node {
    sh("echo WORKSPACE: ${env.WORKSPACE}")
    sh("echo RISCV_CI: ${env.RISCV_CI}")
    def jobs = [:]
    jobs['rocketchip'] = { stage('rocketchip') { build job: 'rocketchip', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"]] } }
    jobs['qemu'] = { stage('qemu') { build job: 'qemu', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"]] } }
    jobs['toolchain'] = { stage('toolchain') { build job: 'toolchain', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"]] } }
    jobs['spike'] = { stage('spike') { build job: 'spike', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"]] } }
    jobs['pk'] = { stage('pk') { build job: 'pk', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"]] } }
    jobs['fesvr'] = { stage('fesvr') { build job: 'fesvr', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"]] } }
    parallel jobs
}