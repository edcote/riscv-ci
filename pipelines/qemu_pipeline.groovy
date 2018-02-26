properties([
buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')),
[$class: 'CopyArtifactPermissionProperty', projectNames: '*']
])

node {
def nodelib = load("${env.RISCV_CI}/jobs/nodelib.groovy")

stage('Clone') {
    checkout([ $class: 'GitSCM',
                    branches: [[name: '*/master']],
                    userRemoteConfigs: [[url: 'https://github.com/riscv/riscv-qemu']],
                    extensions: [ [$class: 'SubmoduleOption', recursiveSubmodules: true, disableSubmodules: false, timeout: 120] ]
             ])
    sh('sleep 0.1s')
}

stage('Dependencies') {
    
    sh('find . -name bin -type d -exec chmod +x {}/* \\;')
    sh('sleep 0.1s')
}

stage('Build') {
    sh('printenv')
    nodelib.qemu_build()
    sh('sleep 0.1s')
}

stage('Test') {
    nodelib.qemu_test()
    sh('sleep 0.1s')
}

stage('Archive') {
    archiveArtifacts artifacts: 'riscv-root/**/*', excludes: ''
    sh('sleep 0.1s')
}
}
    