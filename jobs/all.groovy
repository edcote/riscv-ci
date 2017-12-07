class Builder {
    static github(dslFactory, jobName, ownerAndTarget) {
        dslFactory.job(jobName) {
            scm {
                github(ownerAndTarget)
            }
            steps {
                shell('/bin/true')
            }
        }
    }
}

Builder.github(this, 'pk', 'riscv/riscv-pk')
Builder.github(this, 'fesvr', 'riscv/riscv-fesvr')
Builder.github(this, 'spike', 'riscv/riscv-isa-sim')
