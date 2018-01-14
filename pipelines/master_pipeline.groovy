node {
    sh("echo WORKSPACE: ${env.WORKSPACE}")
    sh("echo RISCV_CI: ${env.RISCV_CI}")
    def jobs = [:]
    jobs['qemu'] = { stage('qemu') { build job: 'qemu', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"]] } }
    jobs['pk'] = { stage('pk') { build job: 'pk', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"]] } }
    jobs['toolchain'] = { stage('toolchain') { build job: 'toolchain', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"]] } }
    jobs['openocd'] = { stage('openocd') { build job: 'openocd', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"]] } }
    jobs['spike'] = { stage('spike') { build job: 'spike', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"]] } }
    jobs['llvm'] = { stage('llvm') { build job: 'llvm', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"]] } }
    jobs['linux'] = { stage('linux') { build job: 'linux', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"]] } }
    jobs['fesvr'] = { stage('fesvr') { build job: 'fesvr', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"]] } }
    jobs['rocketchip'] = { stage('rocketchip') { build job: 'rocketchip', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"]] } }
    jobs['tests'] = { stage('tests') { build job: 'tests', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:"${env.RISCV_CI}"]] } }
    parallel jobs
}