node {
stage('Build') {
    sh("echo WORKSPACE: ${env.WORKSPACE}")
    sh("echo WORKSPACE: ${env.RISCV_CI}")
    sh('echo WORKSPACE: $WORKSPACE')
    sh('echo RISCV_CI: $RISCV_CI')
    def joblib = load("${env.RISCV_CI}/pipelines/pk_test.groovy")
    joblib.true()
    sh('sleep 2s')
}        
stage('Test') {
    sh('echo WORKSPACE: $WORKSPACE')
    sh('echo RISCV_CI: $RISCV_CI')
    def joblib = load("${env.RISCV_CI}/pipelines/pk_test.groovy")
    joblib.true()
    sh('sleep 2s')
}
}
    