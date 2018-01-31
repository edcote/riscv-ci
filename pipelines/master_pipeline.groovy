node {
    sh("echo WORKSPACE: ${env.WORKSPACE}")
    sh("echo RISCV_CI: ${env.RISCV_CI}")
    def pipelines = [:]
    pipelines['toolchain'] = { stage('toolchain') { build job: 'toolchain', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"]] } }
    pipelines['spike'] = { stage('spike') { build job: 'spike', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"]] } }
    pipelines['linux'] = { stage('linux') { build job: 'linux', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"]] } }
    pipelines['openocd'] = { stage('openocd') { build job: 'openocd', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"]] } }
    pipelines['tests'] = { stage('tests') { build job: 'tests', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"]] } }
    pipelines['fesvr'] = { stage('fesvr') { build job: 'fesvr', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"]] } }
    pipelines['rocketchip'] = { stage('rocketchip') { build job: 'rocketchip', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"]] } }
    pipelines['qemu'] = { stage('qemu') { build job: 'qemu', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"]] } }
    pipelines['pk'] = { stage('pk') { build job: 'pk', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"]] } }
    parallel pipelines
}