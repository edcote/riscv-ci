// DO NOT EDIT, MANAGED FILE
def pk_build() {
    withEnv(["PATH=${env.WORKSPACE}/riscv-root/bin:${env.PATH}"]) { 
    sh("""
echo 'Build' && 
rm -rf $WORKSPACE/build && mkdir -p $WORKSPACE/build && cd $WORKSPACE/build && 
PATH=$WORKSPACE/riscv-root/bin:$PATH ../configure --prefix=$WORKSPACE/riscv-root --host=riscv64-unknown-elf && 
PATH=$WORKSPACE/riscv-root/bin:$PATH make -j4 && 
make install
""")
    }
}


def pk_test() {
    withEnv(["PATH=${env.WORKSPACE}/riscv-root/bin:${env.PATH}"]) { 
    sh("""
echo 'Test' && 
/bin/true
""")
    }
}


def toolchain_linux_build() {
    withEnv(["PATH=${env.WORKSPACE}/riscv-root/bin:${env.PATH}"]) { 
    sh("""
echo 'Build' && 
rm -rf $WORKSPACE/build && mkdir -p $WORKSPACE/build && cd $WORKSPACE/build && 
PATH=$WORKSPACE/riscv-root/bin:$PATH ../configure --prefix=$WORKSPACE/riscv-root && 
PATH=$WORKSPACE/riscv-root/bin:$PATH make -j4 linux
""")
    }
}


def toolchain_linux_test() {
    withEnv(["PATH=${env.WORKSPACE}/riscv-root/bin:${env.PATH}"]) { 
    sh("""
echo 'Test' && 
/bin/true
""")
    }
}


def qemu_build() {
    withEnv(["PATH=${env.WORKSPACE}/riscv-root/bin:${env.PATH}"]) { 
    sh("""
echo 'Build' && 
rm -rf $WORKSPACE/build && mkdir -p $WORKSPACE/build && cd $WORKSPACE/build
""")
    }
}


def qemu_test() {
    withEnv(["PATH=${env.WORKSPACE}/riscv-root/bin:${env.PATH}"]) { 
    sh("""
echo 'Test' && 
/bin/true
""")
    }
}


def fesvr_build() {
    withEnv(["PATH=${env.WORKSPACE}/riscv-root/bin:${env.PATH}"]) { 
    sh("""
echo 'Build' && 
rm -rf $WORKSPACE/build && mkdir -p $WORKSPACE/build && cd $WORKSPACE/build && 
PATH=$WORKSPACE/riscv-root/bin:$PATH ../configure --prefix=$WORKSPACE/riscv-root && 
PATH=$WORKSPACE/riscv-root/bin:$PATH make -j4 && 
make install
""")
    }
}


def fesvr_test() {
    withEnv(["PATH=${env.WORKSPACE}/riscv-root/bin:${env.PATH}"]) { 
    sh("""
echo 'Test' && 
/bin/true
""")
    }
}


def tests_build() {
    withEnv(["PATH=${env.WORKSPACE}/riscv-root/bin:${env.PATH}"]) { 
    sh("""
echo 'Build' && 
rm -rf $WORKSPACE/build && mkdir -p $WORKSPACE/build && cd $WORKSPACE/build && 
PATH=$WORKSPACE/riscv-root/bin:$PATH ../configure --prefix=$WORKSPACE/riscv-root && 
PATH=$WORKSPACE/riscv-root/bin:$PATH make -j4 && 
make install
""")
    }
}


def tests_test() {
    withEnv(["PATH=${env.WORKSPACE}/riscv-root/bin:${env.PATH}"]) { 
    sh("""
echo 'Test' && 
/bin/true
""")
    }
}


def linux_build() {
    withEnv(["PATH=${env.WORKSPACE}/riscv-root/bin:${env.PATH}"]) { 
    sh("""
echo 'Build' && 
rm -rf $WORKSPACE/build && mkdir -p $WORKSPACE/build && cd $WORKSPACE/build
""")
    }
}


def linux_test() {
    withEnv(["PATH=${env.WORKSPACE}/riscv-root/bin:${env.PATH}"]) { 
    sh("""
echo 'Test' && 
/bin/true
""")
    }
}


def rocketchip_build() {
    withEnv(["PATH=${env.WORKSPACE}/riscv-root/bin:${env.PATH}"]) { 
    sh("""
echo 'Build' && 
rm -rf $WORKSPACE/build && mkdir -p $WORKSPACE/build && cd $WORKSPACE/build
""")
    }
}


def rocketchip_test() {
    withEnv(["PATH=${env.WORKSPACE}/riscv-root/bin:${env.PATH}"]) { 
    sh("""
echo 'Test' && 
/bin/true
""")
    }
}


def spike_build() {
    withEnv(["PATH=${env.WORKSPACE}/riscv-root/bin:${env.PATH}"]) { 
    sh("""
echo 'Build' && 
rm -rf $WORKSPACE/build && mkdir -p $WORKSPACE/build && cd $WORKSPACE/build && 
PATH=$WORKSPACE/riscv-root/bin:$PATH ../configure --prefix=$WORKSPACE/riscv-root --with-fesvr=$WORKSPACE/riscv-root && 
PATH=$WORKSPACE/riscv-root/bin:$PATH make -j4 && 
make install
""")
    }
}


def spike_test() {
    withEnv(["PATH=${env.WORKSPACE}/riscv-root/bin:${env.PATH}"]) { 
    sh("""
echo 'Test' && 
/bin/true
""")
    }
}


def toolchain_newlib_build() {
    withEnv(["PATH=${env.WORKSPACE}/riscv-root/bin:${env.PATH}"]) { 
    sh("""
echo 'Build' && 
rm -rf $WORKSPACE/build && mkdir -p $WORKSPACE/build && cd $WORKSPACE/build && 
PATH=$WORKSPACE/riscv-root/bin:$PATH ../configure --prefix=$WORKSPACE/riscv-root && 
PATH=$WORKSPACE/riscv-root/bin:$PATH make -j4 && 
make install
""")
    }
}


def toolchain_newlib_test() {
    withEnv(["PATH=${env.WORKSPACE}/riscv-root/bin:${env.PATH}"]) { 
    sh("""
echo 'Test' && 
/bin/true
""")
    }
}


def openocd_build() {
    withEnv(["PATH=${env.WORKSPACE}/riscv-root/bin:${env.PATH}"]) { 
    sh("""
echo 'Build' && 
rm -rf $WORKSPACE/build && mkdir -p $WORKSPACE/riscv-root && cd $WORKSPACE/build
""")
    }
}


def openocd_test() {
    withEnv(["PATH=${env.WORKSPACE}/riscv-root/bin:${env.PATH}"]) { 
    sh("""
echo 'Test' && 
/bin/true
""")
    }
}


return this;
