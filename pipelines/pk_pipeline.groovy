properties(
[
buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '7')),
disableConcurrentBuilds(), 
pipelineTriggers([upstream('toolchain_newlib'), cron('H/15 * * * *')]),
[$class: 'CopyArtifactPermissionProperty', projectNames: '*']
]
)

node {
def nodelib = load("${env.RISCV_CI}/jobs/nodelib.groovy")

stage('Clone') {
    checkout([ $class: 'GitSCM',
                    branches: [[name: '*/master']],
                    userRemoteConfigs: [[url: 'https://github.com/riscv/riscv-pk']],
                    extensions: [ [$class: 'SubmoduleOption', recursiveSubmodules: true, disableSubmodules: false, timeout: 120] ]
             ])
    sh('sleep 0.1s')
}

stage('Dependencies') {
        copyArtifacts filter: '**/*', fingerprintArtifacts: true, projectName: 'toolchain_newlib', selector: lastSuccessful()
    sh("mkdir -p riscv-root && cd riscv-root && if [ -f riscv.tgz ]; then tar -zxf riscv.tgz; else echo 'riscv.tgz not found' && true; fi")
    sh('sleep 0.1s')
}

stage('Build') {
    sh('printenv')
    nodelib.pk_build()
    sh('sleep 0.1s')
}

stage('Test') {
    nodelib.pk_test()
    sh('sleep 0.1s')
}

stage('Archive') {
    sh("cd riscv-root && touch pk-archive && rm -f *.tgz && tar -czvf riscv.tgz *")
    archiveArtifacts artifacts: 'riscv-root/*.tgz', excludes: ''
    sh('sleep 0.1s')
}
}
    