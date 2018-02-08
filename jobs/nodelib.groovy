// DO NOT EDIT, MANAGED FILE
def rocketchip_build() {
    sh("""
export MAKEFLAGS=-j8
echo 'Build' && 
/bin/true
""")
}
def rocketchip_test() {
    sh("""
export MAKEFLAGS=-j8
echo 'Test' && 
/bin/true
""")
}
def rocketchip_deploy() {
    sh("""
export MAKEFLAGS=-j8
echo 'Deploy' && 
/bin/true
""")
}
def qemu_build() {
    sh("""
export MAKEFLAGS=-j8
echo 'Build' && 
/bin/true
""")
}
def qemu_test() {
    sh("""
export MAKEFLAGS=-j8
echo 'Test' && 
/bin/true
""")
}
def qemu_deploy() {
    sh("""
export MAKEFLAGS=-j8
echo 'Deploy' && 
/bin/true
""")
}
def fesvr_build() {
    sh("""
export MAKEFLAGS=-j8
echo 'Build' && 
mkdir -p $WORKSPACE/build && cd $WORKSPACE/build && 
../configure --prefix=$RISCV && 
make
""")
}
def fesvr_test() {
    sh("""
export MAKEFLAGS=-j8
echo 'Test' && 
/bin/true
""")
}
def fesvr_deploy() {
    sh("""
export MAKEFLAGS=-j8
echo 'Deploy' && 
cd $WORKSPACE/build && make install
""")
}
def toolchain_newlib_build() {
    sh("""
export MAKEFLAGS=-j8
echo 'Build' && 
mkdir -p $WORKSPACE/build && cd $WORKSPACE/build && 
../configure --prefix=$RISCV && 
make
""")
}
def toolchain_newlib_test() {
    sh("""
export MAKEFLAGS=-j8
echo 'Test' && 
/bin/true
""")
}
def toolchain_newlib_deploy() {
    sh("""
export MAKEFLAGS=-j8
echo 'Deploy' && 
cd $WORKSPACE/build && make install
""")
}
def tests_build() {
    sh("""
export MAKEFLAGS=-j8
echo 'Build' && 
/bin/true
""")
}
def tests_test() {
    sh("""
export MAKEFLAGS=-j8
echo 'Test' && 
/bin/true
""")
}
def tests_deploy() {
    sh("""
export MAKEFLAGS=-j8
echo 'Deploy' && 
/bin/true
""")
}
def linux_build() {
    sh("""
export MAKEFLAGS=-j8
echo 'Build' && 
/bin/true
""")
}
def linux_test() {
    sh("""
export MAKEFLAGS=-j8
echo 'Test' && 
/bin/true
""")
}
def linux_deploy() {
    sh("""
export MAKEFLAGS=-j8
echo 'Deploy' && 
/bin/true
""")
}
def spike_build() {
    sh("""
export MAKEFLAGS=-j8
echo 'Build' && 
mkdir -p $WORKSPACE/build && cd $WORKSPACE/build && 
../configure --prefix=$RISCV --with-fesvr=$RISCV && 
make
""")
}
def spike_test() {
    sh("""
export MAKEFLAGS=-j8
echo 'Test' && 
/bin/true
""")
}
def spike_deploy() {
    sh("""
export MAKEFLAGS=-j8
echo 'Deploy' && 
cd $WORKSPACE/build && make install
""")
}
def toolchain_linux_build() {
    sh("""
export MAKEFLAGS=-j8
echo 'Build' && 
mkdir -p $WORKSPACE/build && cd $WORKSPACE/build && 
../configure --prefix=$RISCV && 
make linux
""")
}
def toolchain_linux_test() {
    sh("""
export MAKEFLAGS=-j8
echo 'Test' && 
/bin/true
""")
}
def toolchain_linux_deploy() {
    sh("""
export MAKEFLAGS=-j8
echo 'Deploy' && 
cd $WORKSPACE/build && make install
""")
}
def pk_build() {
    sh("""
export MAKEFLAGS=-j8
echo 'Build' && 
mkdir -p $WORKSPACE/build && cd $WORKSPACE/build && 
../configure --prefix=$RISCV && 
make
""")
}
def pk_test() {
    sh("""
export MAKEFLAGS=-j8
echo 'Test' && 
/bin/true
""")
}
def pk_deploy() {
    sh("""
export MAKEFLAGS=-j8
echo 'Deploy' && 
cd $WORKSPACE/build && make install
""")
}
def openocd_build() {
    sh("""
export MAKEFLAGS=-j8
echo 'Build' && 
/bin/true
""")
}
def openocd_test() {
    sh("""
export MAKEFLAGS=-j8
echo 'Test' && 
/bin/true
""")
}
def openocd_deploy() {
    sh("""
export MAKEFLAGS=-j8
echo 'Deploy' && 
/bin/true
""")
}
return this;
