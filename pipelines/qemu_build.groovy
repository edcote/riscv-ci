def binTrue() {
    sh('echo WORKSPACE: $WORKSPACE')
    sh('echo RISCV_CI: $RISCV_CI')       
    sh('/bin/true')
    return this;
}

def binFalse() {
    sh('echo WORKSPACE: $WORKSPACE')
    sh('echo RISCV_CI: $RISCV_CI')       
    sh('/bin/false')
    return this;
}
