node {
def nodelib = load("${env.RISCV_CI}/jobs/nodelib.groovy")

stage('Clone') {
    checkout([ $class: 'GitSCM',
                    branches: [[name: '*/master']],
                    userRemoteConfigs: [[url: 'https://github.com/riscv/riscv-tests']],
                    extensions: [ [$class: 'SubmoduleOption', recursiveSubmodules: true, disableSubmodules: false, timeout: 120] ]
             ])
    sh('sleep 2s')
}

stage('Build') {
    sh('printenv')
    nodelib.tests_build()
    sh('sleep 2s')
}

stage('Test') {
    sh('printenv')
    nodelib.tests_test()
    sh('sleep 2s')
}

stage('Archive') {
    sh('printenv')
    nodelib.tests_archive()
    sh('sleep 2s')
}
}
    