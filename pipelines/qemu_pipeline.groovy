node {
stage('Build') {
    def joblib = load("${env.RISCV_CI}@script/pipelines/qemu_build.groovy")
    sh('echo mWORKSPACE: $WORKSPACE')
    sh('echo mRISCV_CI: $RISCV_CI')
    sh('echo PWD: $PWD')
    joblib.binTrue()
    sh('sleep 2s')
}        
stage('Test') {
    def joblib = load("${env.RISCV_CI}@script/pipelines/qemu_test.groovy")
    sh('echo WORKSPACE: $WORKSPACE')
    sh('echo RISCV_CI: $RISCV_CI')
    sh('echo PWD: $PWD')
    joblib.binTrue()
    sh('sleep 2s')
}
}
    