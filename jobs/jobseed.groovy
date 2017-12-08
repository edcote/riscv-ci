class Builder {
    static github(dslFactory, jobName, ownerAndTarget) {
        println("Building github job $jobName")

        dslFactory.job(jobName) {
            scm {
                git {
                    remote { github(ownerAndTarget) }
                    branches('master')
                    extensions {
                        submoduleOptions {
                            recursive(false)
                        }
                    }
                }
            }
            triggers {
                githubPush()
            }
            steps {
                shell("echo Hello, World!")
            }
        }
    }
}

Builder.github(this, 'pk', 'riscv/riscv-pk')
Builder.github(this, 'fesvr', 'riscv/riscv-fesvr')
Builder.github(this, 'spike', 'riscv/riscv-isa-sim')
Builder.github(this, 'qemu', 'riscv/riscv-qemu')
Builder.github(this, 'tests', 'riscv/riscv-tests')
Builder.github(this, 'rocketchip', 'freechipsproject/rocket-chip')
Builder.github(this, 'toolchain', 'riscv/riscv-gnu-toolchain')

