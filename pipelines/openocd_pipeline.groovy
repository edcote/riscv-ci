node {
def nodelib = load("${env.RISCV_CI}/jobs/nodelib.groovy")

stage('Clone') {
    checkout([ $class: 'GitSCM',
                    branches: [[name: '*/master']],
                    userRemoteConfigs: [[url: 'https://github.com/riscv/riscv-openocd']],
                    extensions: [ [$class: 'SubmoduleOption', recursiveSubmodules: true, disableSubmodules: false, timeout: 120] ]
             ])
    sh('sleep 2s')
}

stage('Build') {
    sh('printenv')
    nodelib.openocd_build()
    sh('sleep 2s')
}

stage('Test') {
    sh('printenv')
    nodelib.openocd_test()
    sh('sleep 2s')
}

stage('Deploy') {
    sh('printenv')
    nodelib.openocd_deploy()
    sh('sleep 2s')
}
}
    