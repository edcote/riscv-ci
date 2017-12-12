node {
    sh('echo WORKSPACE: $WORKSPACE')
    sh('echo RISCV_CI: $RISCV_CI')
    def jobs = [:]
    jobs['toolchain'] = { build job: 'toolchain', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.WORKSPACE}"]] }
    jobs['spike'] = { build job: 'spike', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.WORKSPACE}"]] }
    jobs['rocketchip'] = { build job: 'rocketchip', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.WORKSPACE}"]] }
    jobs['fesvr'] = { build job: 'fesvr', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.WORKSPACE}"]] }
    jobs['qemu'] = { build job: 'qemu', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.WORKSPACE}"]] }
    jobs['pk'] = { build job: 'pk', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.WORKSPACE}"]] }
    parallel jobs
}