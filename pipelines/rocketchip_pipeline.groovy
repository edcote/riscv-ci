node {
stage('Build') {
    sh('echo WORKSPACE: $WORKSPACE')
    sh('echo RISCV_CI: $RISCV_CI')
    def joblib = load("${env.WORKSPACE}/pipelines/rocketchip_test.groovy")
    joblib.true()
    sh('sleep 2s')
}        
stage('Test') {
    sh('echo WORKSPACE: $WORKSPACE')
    sh('echo RISCV_CI: $RISCV_CI')
    def joblib = load("${env.WORKSPACE}/pipelines/rocketchip_test.groovy")
    joblib.true()
    sh('sleep 2s')
}
}
    