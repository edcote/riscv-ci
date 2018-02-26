properties([
buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')),
[$class: 'CopyArtifactPermissionProperty', projectNames: '*']
])

node {
def nodelib = load("${env.RISCV_CI}/jobs/nodelib.groovy")

stage('Clone') {
    checkout([ $class: 'GitSCM',
                    branches: [[name: '*/master']],
                    userRemoteConfigs: [[url: 'https://github.com/riscv/riscv-tests']],
                    extensions: [ [$class: 'SubmoduleOption', recursiveSubmodules: true, disableSubmodules: false, timeout: 120] ]
             ])
    sh('sleep 0.1s')
}

stage('Dependencies') {
        copyArtifacts filter: '**/*', fingerprintArtifacts: true, projectName: 'spike', selector: lastSuccessful()
    copyArtifacts filter: '**/*', fingerprintArtifacts: true, projectName: 'toolchain_newlib', selector: lastSuccessful()
    copyArtifacts filter: '**/*', fingerprintArtifacts: true, projectName: 'pk', selector: lastSuccessful()
    sh("cd riscv-root && if [ -f riscv.tgz ]; then tar -zxf riscv.tgz; else echo 'riscv.tgz not found' && true; fi")
    sh('sleep 0.1s')
}

stage('Build') {
    sh('printenv')
    nodelib.tests_build()
    sh('sleep 0.1s')
}

stage('Test') {
    nodelib.tests_test()
    sh('sleep 0.1s')
}

stage('Archive') {
    sh("cd riscv-root && rm -f *.tgz && tar -czvf riscv.tgz *")
    archiveArtifacts artifacts: 'riscv-root/*.tgz', excludes: ''
    sh('sleep 0.1s')
}
}
    