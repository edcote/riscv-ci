node {
def nodelib = load("${env.RISCV_CI}/jobs/nodelib.groovy")

stage('Clone') {
    checkout([ $class: 'GitSCM',
                    branches: [[name: '*/master']],
                    userRemoteConfigs: [[url: 'https://github.com/riscv/riscv-fesvr']],
                    extensions: [ [$class: 'SubmoduleOption', recursiveSubmodules: true, disableSubmodules: false, timeout: 120] ]
             ])
    sh('sleep 2s')
}

stage('Build') {
    sh('printenv')
    nodelib.fesvr_build()
    sh('sleep 2s')
}

stage('Test') {
    sh('printenv')
    nodelib.fesvr_test()
    sh('sleep 2s')
}

stage('Archive') {
    sh('printenv')
    nodelib.fesvr_archive()
    sh('sleep 2s')
}
}
    