class Builder {
    static pipeline(dslFactory, pipelineName, ownerAndTarget) {
        println("Building Jenkins job'$pipelineName'")

        dslFactory.job(pipelineName) {
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
        }
    }

    static step(dslFactory, pipelineName, pipelineStep) {
        def jobName = "${pipelineName}-${pipelineStep}"
        def scriptFile = "${pipelineName}_${pipelineStep}.sh"

        println("Building Jenkins job '$jobName'; uses '$scriptFile'")

        dslFactory.job(jobName) {
            steps {
                shell('cd $WORKSPACE/scripts && ' + scriptFile)
            }
        }
    }

    static view(viewFactory, pipelineName, jobNames) {
        println("Building Jenkins view '$pipelineName'")

        viewFactory.listView(pipelineName) {
            description("All jobs for pipeline $pipelineName")
            filterBuildQueue()
            filterExecutors()
            jobs {
                jobNames.each { name("${pipelineName}-${it}") }
                //regex("/$pipelineName-.*/")
            }
//            jobFilters {
//                status {
//                    status(Status.UNSTABLE)
//                }
//            }
            columns {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
    }
}

def jobspec = [['pk', 'riscv/riscv-pk'],
               ['fesvr', 'riscv/riscv-fesvr'],
               ['spike', 'riscv/riscv-isa-sim'],
               ['qemu', 'riscv/riscv-qemu'],
               ['tests', 'riscv/riscv-tests'],
               ['rocketchip', 'freechipsproject/rocket-chip'],
               ['toolchain', 'riscv/riscv-gnu-toolchain']
]

// all pipeline jobs
jobspec.each { Builder.pipeline(this, it[0], it[1]) }

def stepNames = ["build", "test", "deploy"]

// views for each pipeline
jobspec.each { Builder.view(this, it[0], stepNames) }

// all pipeline steps for each pipeline
jobspec.each { j ->
    stepNames.each {
        Builder.step(this, j[0], it)
    }
}
