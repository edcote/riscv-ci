node {
sh('echo WORKSPACE: $WORKSPACE')

sh('echo RISCV_CI: $RISCV_CI')

def nodelib = load("${env.RISCV_CI}/jobs/nodelib.groovy")

stage('Clone') {
    checkout([ $class: 'GitSCM',
                    branches: [[name: '*/master']],
                    userRemoteConfigs: [[url: 'https://github.com/riscv/riscv-gnu-toolchain']],
                    extensions: [ [$class: 'SubmoduleOption', recursiveSubmodules: true, disableSubmodules: false, timeout: 120] ]
             ])
    sh('sleep 2s')
}

stage('Build') {
    nodelib.toolchain_build()
    sh('sleep 2s')
}

stage('Test') {
    nodelib.toolchain_test()
    sh('sleep 2s')
}

stage('Deploy') {
    nodelib.toolchain_test()
    sh('sleep 2s')
}
}
    