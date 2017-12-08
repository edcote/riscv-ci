class Builder {
    static github(dslFactory, name, ownerAndTarget, type) {
        def jobName = "$name-$type"
        def scriptFile = "${name}_${type}.sh"

        println("Building job '$jobName'; uses '$scriptFile'")

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
            steps {
                shell('cd $WORKSPACE/scripts && ' + scriptFile)
            }
        }
    }
}

["build", "test", "deploy"].each {
    Builder.github(this, 'pk', 'riscv/riscv-pk', it)
    Builder.github(this, 'fesvr', 'riscv/riscv-fesvr', it)
    Builder.github(this, 'spike', 'riscv/riscv-isa-sim', it)
    Builder.github(this, 'qemu', 'riscv/riscv-qemu', it)
    Builder.github(this, 'tests', 'riscv/riscv-tests', it)
    Builder.github(this, 'rocketchip', 'freechipsproject/rocket-chip', it)
    Builder.github(this, 'toolchain', 'riscv/riscv-gnu-toolchain', it)
}
