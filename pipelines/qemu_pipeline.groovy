// assuming that stage('Compile') is done b 
node {

sh('echo WORKSPACE: $WORKSPACE')

sh('echo RISCV_CI: $RISCV_CI')       

stage('Compile') {
    checkout([ $class: 'GitSCM',
                    branches: [[name: '*/master']],
                    userRemoteConfigs: [[url: 'https://github.com/riscv/riscv-qemu']],
                    extensions: [ [$class: 'SubmoduleOption', recursiveSubmodules: true, disableSubmodules: false] ]
                  ])
    sh('sleep 2s')
}
stage('Build') {
    def joblib = load("${env.RISCV_CI}/pipelines/qemu_build.groovy")
    joblib.binTrue()
    sh('sleep 2s')
}        
stage('Test') {
    def joblib = load("${env.RISCV_CI}/pipelines/qemu_test.groovy")
    sh('echo PWD: $PWD')
    joblib.binTrue()
    sh('sleep 2s')
}
}
    