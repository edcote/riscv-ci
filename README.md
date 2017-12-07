# RISC-V Continuous Integration (Experimental)

## Jenkins

### Installation

Information about automated information: https://blog.thesparktree.com/you-dont-know-jenkins-part-1

### Your API Token

From: https://wiki.jenkins.io/display/JENKINS/Authenticating+scripted+clients

The API token is available in your personal configuration page. Click your name on the top right corner on every page, then click "Configure" to see your API token.

# OLD MATERIAL

### [Job DSL](https://wiki.jenkins.io/display/JENKINS/Job+DSL+Plugin)

Used to create Jenkins jobs (different than pipelines) using scripts.

https://github.com/jenkinsci/job-dsl-plugin/wiki/Tutorial---Using-the-Jenkins-Job-DSL

Be sure to set JAVA_HOME before running gradle: `export JAVA_HOME=$(readlink -f /usr/bin/java | sed "s:jre/bin/java::")`

    cd riscv-ci
    gradle test

Primary reference:  [Linux/RISC-V installation manual](https://github.com/riscv/riscv-tools#linuxman). Note the distinction between linux and newlib toolchain.

### Setup environment variables (old)

    module load riscv-tools/local
   
    export RISCV_SW=$CAD_ROOT/riscv-sw
    export TOP=$RISCV_SW/riscv-tools
    export RISCV=$RISCV_SW/riscv
    export ROOT_DISK=$RISCV_SW/riscv-linux/root
    export PK_ROOT=$RISCV_SW/riscv-pk
    mkdir -p $RISCV
    export PATH=$RISCV/bin:$PATH
    export MAKEFLAGS="$MAKEFLAGS -j8"

### Clone the required repositories

    cd $RISCV_SW
    git clone --recursive https://github.com/riscv/riscv-tools.git 
    git clone --recursive https://github.com/riscv/riscv-qemu.git
    git clone --recursive https://github.com/riscv/riscv-linux.git
    git clone --recursive https://github.com/riscv/riscv-pk.git
    git clone --recursive https://github.com/riscv/riscv-gnu-toolchain
    git clone --recursive https://github.com/riscv/riscv-isa-sim
    git clone --recursive https://github.com/riscv/riscv-fesvr

### Update repositories

    #git pull && git submodule update --init --recursive

### Build toolchain (newlib)

This is largely for sanity testing.

    cd $RISCV_SW//riscv-tools && ./build-spike-only.sh
    cd $RISCV_SW/riscv-tools && ./build-rv32-ima.sh

### Build toolchain (linux)

riscv64-unknown-linux-gnu-gcc (glibc)

Don't use build*.sh scripts for this.  Instead, manually build the toolchain's Linux target.

    cd $RISCV_SW
    rm -rf $RISCV_SW/riscv-gnu-toolchain/build
    mkdir -p $RISCV_SW/riscv-gnu-toolchain/build
    cd $RISCV_SW/riscv-gnu-toolchain/build
    ../configure --prefix=$RISCV
    make linux
    make install

*FIXME* Do we replace -unknown- in compiler string?

# Kernel build

    cd $RISCV_SW/riscv-linux
    make ARCH=riscv defconfig 
    make ARCH=riscv CROSS_COMPILE=riscv64-unknown-linux-gnu-

There will be a second compile pass later

### BusyBox

    cd $RISCV_SW
    curl -L http://busybox.net/downloads/busybox-1.26.2.tar.bz2 > busybox-1.26.2.tar.bz2
    tar xvjf busybox-1.26.2.tar.bz2

    cd busybox-1.26.2
    make CROSS_COMPILE=riscv64-unknown-linux-gnu- allnoconfig
    # ^^^ turn OFF all config options

    echo "CONFIG_STATIC=y
    CONFIG_CROSS_COMPILER_PREFIX=riscv64-unknown-linux-gnu-
    CONFIG_FEATURE_INSTALLER=y
    CONFIG_INIT=y
    CONFIG_ASH=y
    CONFIG_ASH_JOB_CONTROL=n
    CONFIG_MOUNT=y
    CONFIG_FEATURE_USE_INITTAB=y" >> .config

    make oldconfig
    make CROSS_COMPILE=riscv64-unknown-linux-gnu-

### Root disk image

    export ROOT_DISK=$RISCV_SW/root-disk
    mkdir -p $ROOT_DISK && cd $ROOT_DISK
    mkdir -p bin etc dev lib proc sbin sys tmp usr usr/bin usr/lib usr/sbin

    cp $RISCV_SW/busybox-1.26.2/busybox $ROOT_DISK/bin

    echo "::sysinit:/bin/busybox mount -t proc proc /proc
    ::sysinit:/bin/busybox mount -t tmpfs tmpfs /tmp
    ::sysinit:/bin/busybox mount -o remount,rw /dev/htifblk0 /
    ::sysinit:/bin/busybox --install -s
    /dev/console::sysinit:-/bin/ash" > $ROOT_DISK/etc/inittab

    # ln [OPTION] TARGET LINK_NAME // create link to target with link name
    cd $ROOT_DISK/sbin && ln -s ../bin/busybox init
    cd $ROOT_DISK && ln -s sbin/init init

    ln -s bin/busybox sbin/init
    ln -s sbin/init init

    # make character device for console
    cd $ROOT_DISK && sudo mknod dev/console c 5 1

    # create ramfs
    find $ROOT_DISK | cpio --quiet -o -H newc > $RISCV_SW/riscv-linux/rootfs.cpio

Finally, need to configure Linux to embed the created cpio archive.

    cd $RISCV_SW/riscv-linux
    echo "CONFIG_BLK_DEV_INITRD=y
    CONFIG_INITRAMFS_SOURCE="rootfs.cpio"" >> .config
    make ARCH=riscv oldconfig
    make ARCH=riscv vmlinux CROSS_COMPILE=riscv64-unknown-linux-gnu-

# Frontend Server

    rm -rf $RISCV_SW/riscv-fesvr/build
    mkdir -p $RISCV_SW/riscv-fesvr/build
    cd $RISCV_SW/riscv-fesvr/build
    ../configure --prefix=$RISCV
    make
    make install

# Proxy Kernel

    rm -rf $PK_ROOT/build && mkdir -p $PK_ROOT/build && cd $PK_ROOT/build
    ../configure --prefix=$RISCV --host=riscv64-unknown-linux-gnu --with-payload=$RISCV_SW/riscv-linux/vmlinux
    make && make install

# Spike ISS

    rm -rf $RISCV_SW/riscv-isa-sim/build
    mkdir -p $RISCV_SW/riscv-isa-sim/build
    cd $RISCV_SW/riscv-isa-sim/build
    ../configure --prefix=$RISCV --with-fesvr=$RISCV
    make
    make install

Test the kernel using the ISS.

    cd $PK_ROOT/build && spike bbl vmlinux

# QEMU

    cd $RISCV_SW/riscv-qemu
    git submodule update --init dtc
    git submodule update --init pixman
    # Method 1a (Full-System Simulation using the Spike board):
    ./configure --target-list=riscv64-softmmu,riscv32-softmmu --prefix=$RISCV
    make
    make install

    wget https://people.eecs.berkeley.edu/~skarandikar/host/qemu/1.9.1/bblvmlinuxinitramfs_dynamic
    qemu-system-riscv64 -nographic -kernel bblvmlinuxinitramfs_dynamic
