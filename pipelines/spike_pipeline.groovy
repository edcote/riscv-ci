properties(
[
buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '7')),
disableConcurrentBuilds(), 
pipelineTriggers([upstream('fesvr'), cron('H/15 * * * *')]),
[$class: 'CopyArtifactPermissionProperty', projectNames: '*']
]
)

node {
def nodelib = load("${env.RISCV_CI}/jobs/nodelib.groovy")

stage('Clone') {
    checkout([ $class: 'GitSCM',
                    branches: [[name: '*/master']],
                    userRemoteConfigs: [[url: 'https://github.com/riscv/riscv-isa-sim']],
                    extensions: [ [$class: 'SubmoduleOption', recursiveSubmodules: true, disableSubmodules: false, timeout: 120] ]
             ])
    sh('sleep 0.1s')
}

stage('Dependencies') {
        copyArtifacts filter: '**/*', fingerprintArtifacts: true, projectName: 'fesvr', selector: lastSuccessful()
    sh("mkdir -p riscv-root && cd riscv-root && if [ -f riscv.tgz ]; then tar -zxf riscv.tgz; else echo 'riscv.tgz not found' && true; fi")
    sh('sleep 0.1s')
}

stage('Build') {
    sh('printenv')
    nodelib.spike_build()
    sh('sleep 0.1s')
}

stage('Test') {
    nodelib.spike_test()
    sh('sleep 0.1s')
}

stage('Archive') {
    sh("cd riscv-root && touch spike-archive && rm -f *.tgz && tar -czvf riscv.tgz *")
    archiveArtifacts artifacts: 'riscv-root/*.tgz', excludes: ''
    sh('sleep 0.1s')
}
}
    