node {
stage('Build') {
    sh('echo mWORKSPACE: $WORKSPACE')
    sh('echo mRISCV_CI: $RISCV_CI')
    sh('echo PWD: $PWD')
    def joblib = load("${env.RISCV_CI}@script/pipelines/pk_build.groovy")
    joblib.true()
    sh('sleep 2s')
}        
stage('Test') {
    sh('echo WORKSPACE: $WORKSPACE')
    sh('echo RISCV_CI: $RISCV_CI')
    sh('echo PWD: $PWD')
    def joblib = load("${env.RISCV_CI}@script/pipelines/pk_test.groovy")
    joblib.true()
    sh('sleep 2s')
}
}
    