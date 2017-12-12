def binTrue() {
    sh('echo WORKSPACE: $WORKSPACE')
    sh('echo RISCV_CI: $RISCV_CI')       
    sh('/bin/true')
}

def binFalse() {
    sh('echo WORKSPACE: $WORKSPACE')
    sh('echo RISCV_CI: $RISCV_CI')       
    sh('/bin/false')
}
return this;
