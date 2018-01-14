node {

sh('echo WORKSPACE: $WORKSPACE')

sh('echo RISCV_CI: $RISCV_CI')

stage('Clone') {
    checkout([ $class: 'GitSCM',
                    branches: [[name: '*/master']],
                    userRemoteConfigs: [[url: 'https://github.com/riscv/riscv-qemu']],
                    extensions: [ [$class: 'SubmoduleOption', recursiveSubmodules: true, disableSubmodules: false, timeout: 120] ]
             ])
    sh('sleep 2s')
}
stage('Build') {
    def joblib = load("${env.RISCV_CI}/pipelines/qemu_build.groovy")
    joblib.build()
    sh('sleep 2s')
}
stage('Test') {
    def joblib = load("${env.RISCV_CI}/pipelines/qemu_test.groovy")
    joblib.test()
    sh('sleep 2s')
}
}
    