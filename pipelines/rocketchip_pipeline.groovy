node {
stage('Build') {
    sh('echo WORKSPACE: $WORKSPACE')
    sh('echo RISCV_CI: $RISCV_CI')
    script("${env.WORKSPACE}"/pipelines/rocketchip_build.groovy")
    sh('sleep 2s')
}        
stage('Test') {
    sh('echo WORKSPACE: $WORKSPACE')
    sh('echo RISCV_CI: $RISCV_CI')
    script("${env.WORKSPACE}"/pipelines/rocketchip_test.groovy")
    sh('sleep 2s')
}
}
    