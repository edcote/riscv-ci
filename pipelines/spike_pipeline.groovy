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
                    userRemoteConfigs: [[url: 'https://github.com/riscv/riscv-isa-sim']],
                    extensions: [ [$class: 'SubmoduleOption', recursiveSubmodules: true, disableSubmodules: false, timeout: 120] ]
             ])
    sh('sleep 2s')
}

stage('Build') {
    nodelib.spike_build()
    sh('sleep 2s')
}

stage('Test') {
    nodelib.spike_test()
    sh('sleep 2s')
}

stage('Archive') {
    nodelib.spike_archive()
    sh('sleep 2s')
}
}
    