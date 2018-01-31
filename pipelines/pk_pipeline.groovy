node {
def nodelib = load("${env.RISCV_CI}/jobs/nodelib.groovy")

stage('Clone') {
    checkout([ $class: 'GitSCM',
                    branches: [[name: '*/master']],
                    userRemoteConfigs: [[url: 'https://github.com/riscv/riscv-pk']],
                    extensions: [ [$class: 'SubmoduleOption', recursiveSubmodules: true, disableSubmodules: false, timeout: 120] ]
             ])
    sh('sleep 2s')
}

stage('Build') {
    sh('printenv')
    nodelib.pk_build()
    sh('sleep 2s')
}

stage('Test') {
    sh('printenv')
    nodelib.pk_test()
    sh('sleep 2s')
}

stage('Deploy') {
    sh('printenv')
    nodelib.pk_deploy()
    sh('sleep 2s')
}
}
    