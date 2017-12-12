node {
stage('Build') {
    sh('echo WORKSPACE: $WORKSPACE')
    sh('echo RISCV_CI: $RISCV_CI')
    script("${env.WORKSPACE}/pipelines/pk_build.groovy")
    sh('sleep 2s')
}        
stage('Test') {
    sh('echo WORKSPACE: $WORKSPACE')
    sh('echo RISCV_CI: $RISCV_CI')
    script("${env.WORKSPACE}/pipelines/pk_test.groovy")
    sh('sleep 2s')
}
}
    