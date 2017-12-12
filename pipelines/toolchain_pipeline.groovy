node {
stage('Build') {
    sh('echo WORKSPACE: $WORKSPACE')
    sh('echo RISCV_CI: $RISCV_CI')
    sh('echo PWD: $PWD')
    def joblib = load("${env.RISCV_CI}@script/pipelines/toolchain_test.groovy")
    joblib.true()
    sh('sleep 2s')
}        
stage('Test') {
    sh('echo WORKSPACE: $WORKSPACE')
    sh('echo RISCV_CI: $RISCV_CI')
    sh('echo PWD: $PWD')
    def joblib = load("${env.RISCV_CI}@script/pipelines/toolchain_test.groovy")
    joblib.true()
    sh('sleep 2s')
}
}
    