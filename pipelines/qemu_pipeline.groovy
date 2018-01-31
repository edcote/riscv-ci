node {
sh('echo WORKSPACE: $WORKSPACE')

sh('echo RISCV_CI: $RISCV_CI')

def nodelib = load("${env.RISCV_CI}/jobs/nodelib.groovy")

stage('Clone') {
    checkout([ $class: 'GitSCM',
                    branches: [[name: '*/master']],
                    userRemoteConfigs: [[url: 'https://github.com/riscv/riscv-qemu']],
                    extensions: [ [$class: 'SubmoduleOption', recursiveSubmodules: true, disableSubmodules: false, timeout: 120] ]
             ])
    sh('sleep 2s')
}

stage('Build') {
    nodelib.qemu_build()
    sh('sleep 2s')
}

stage('Test') {
    nodelib.qemu_test()
    sh('sleep 2s')
}

stage('Deploy') {
    nodelib.qemu_deploy()
    sh('sleep 2s')
}
}
    