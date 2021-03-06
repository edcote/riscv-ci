node {
    def pipelines = [:]
    pipelines['pk'] = { stage('pk') { build job: 'pk', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"], [$class: 'StringParameterValue', name: 'RISCV', value:"${env.RISCV_CI}/riscv"]] } }
    pipelines['qemu'] = { stage('qemu') { build job: 'qemu', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"], [$class: 'StringParameterValue', name: 'RISCV', value:"${env.RISCV_CI}/riscv"]] } }
    pipelines['tests'] = { stage('tests') { build job: 'tests', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"], [$class: 'StringParameterValue', name: 'RISCV', value:"${env.RISCV_CI}/riscv"]] } }
    pipelines['rocketchip'] = { stage('rocketchip') { build job: 'rocketchip', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"], [$class: 'StringParameterValue', name: 'RISCV', value:"${env.RISCV_CI}/riscv"]] } }
    pipelines['spike'] = { stage('spike') { build job: 'spike', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"], [$class: 'StringParameterValue', name: 'RISCV', value:"${env.RISCV_CI}/riscv"]] } }
    pipelines['toolchain'] = { stage('toolchain') { build job: 'toolchain', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"], [$class: 'StringParameterValue', name: 'RISCV', value:"${env.RISCV_CI}/riscv"]] } }
    pipelines['fesvr'] = { stage('fesvr') { build job: 'fesvr', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"], [$class: 'StringParameterValue', name: 'RISCV', value:"${env.RISCV_CI}/riscv"]] } }
    pipelines['linux'] = { stage('linux') { build job: 'linux', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"], [$class: 'StringParameterValue', name: 'RISCV', value:"${env.RISCV_CI}/riscv"]] } }
    pipelines['openocd'] = { stage('openocd') { build job: 'openocd', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"], [$class: 'StringParameterValue', name: 'RISCV', value:"${env.RISCV_CI}/riscv"]] } }
    parallel pipelines
}