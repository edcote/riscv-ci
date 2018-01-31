// DO NOT EDIT, MANAGED FILE
def openocd_build() {
    sh("""
echo 'Build' && 
/bin/true
""")
}
def openocd_test() {
    sh("""
echo 'Test' && 
/bin/true
""")
}
def openocd_deploy() {
    sh("""
echo 'Deploy' && 
/bin/true
""")
}
def pk_build() {
    sh("""
echo 'Build' && 
rm -rf $WORKSPACE/build && mkdir -p $WORKSPACE/build && cd $WORKSPACE/build && 
../configure --prefix=$RISCV && 
make
""")
}
def pk_test() {
    sh("""
echo 'Test' && 
/bin/true
""")
}
def pk_deploy() {
    sh("""
echo 'Deploy' && 
cd $WORKSPACE/build && make install
""")
}
def qemu_build() {
    sh("""
echo 'Build' && 
/bin/true
""")
}
def qemu_test() {
    sh("""
echo 'Test' && 
/bin/true
""")
}
def qemu_deploy() {
    sh("""
echo 'Deploy' && 
/bin/true
""")
}
def tests_build() {
    sh("""
echo 'Build' && 
/bin/true
""")
}
def tests_test() {
    sh("""
echo 'Test' && 
/bin/true
""")
}
def tests_deploy() {
    sh("""
echo 'Deploy' && 
/bin/true
""")
}
def rocketchip_build() {
    sh("""
echo 'Build' && 
/bin/true
""")
}
def rocketchip_test() {
    sh("""
echo 'Test' && 
/bin/true
""")
}
def rocketchip_deploy() {
    sh("""
echo 'Deploy' && 
/bin/true
""")
}
def linux_build() {
    sh("""
echo 'Build' && 
/bin/true
""")
}
def linux_test() {
    sh("""
echo 'Test' && 
/bin/true
""")
}
def linux_deploy() {
    sh("""
echo 'Deploy' && 
/bin/true
""")
}
def toolchain_build() {
    sh("""
echo 'Build' && 
rm -rf $WORKSPACE/build && mkdir -p $WORKSPACE/build && cd $WORKSPACE/build && 
../configure --prefix=$RISCV && 
make linux
""")
}
def toolchain_test() {
    sh("""
echo 'Test' && 
/bin/true
""")
}
def toolchain_deploy() {
    sh("""
echo 'Deploy' && 
cd $WORKSPACE/build && make install
""")
}
def fesvr_build() {
    sh("""
echo 'Build' && 
rm -rf $WORKSPACE/build && mkdir -p $WORKSPACE/build && cd $WORKSPACE/build && 
../configure --prefix=$RISCV && 
make
""")
}
def fesvr_test() {
    sh("""
echo 'Test' && 
/bin/true
""")
}
def fesvr_deploy() {
    sh("""
echo 'Deploy' && 
cd $WORKSPACE/build && make install
""")
}
def spike_build() {
    sh("""
echo 'Build' && 
rm -rf $WORKSPACE/build && mkdir -p $WORKSPACE/build && cd $WORKSPACE/build && 
../configure --prefix=$RISCV --with-fesvr=$RISCV && 
make
""")
}
def spike_test() {
    sh("""
echo 'Test' && 
/bin/true
""")
}
def spike_deploy() {
    sh("""
echo 'Deploy' && 
cd $WORKSPACE/build && make install
""")
}
return this;
