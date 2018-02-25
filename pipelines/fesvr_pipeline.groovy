properties([
buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')),
[$class: 'CopyArtifactPermissionProperty', projectNames: '*']
])

node {
def nodelib = load("${env.RISCV_CI}/jobs/nodelib.groovy")

stage('Clone') {
    sh('printenv')
    checkout([ $class: 'GitSCM',
                    branches: [[name: '*/master']],
                    userRemoteConfigs: [[url: 'https://github.com/riscv/riscv-fesvr']],
                    extensions: [ [$class: 'SubmoduleOption', recursiveSubmodules: true, disableSubmodules: false, timeout: 120] ]
             ])
    sh('sleep 2s')
}

stage('Build') {
    nodelib.fesvr_build()
    sh('sleep 2s')
}

stage('Test') {
    nodelib.fesvr_test()
    sh('sleep 2s')
}

stage('Archive') {
    nodelib.fesvr_archive()
    sh('sleep 2s')
}
}
    