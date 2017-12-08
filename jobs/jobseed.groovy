class Builder {
    static top(dslFactory, jobSpec) {
        println("Building Jenkins top pipeline")

        dslFactory.pipelineJob("top") {
            parameters {
                stringParam("RISCV_CI", "\${WORKSPACE}")
            }
            scm {
                git {
                    remote { github("edcote/riscv-ci") }
                    branches('develop')
                }
            }
            definition {
                def dsl = []
                for (j in jobSpec) {
                    dsl += "stage('${j[0]}') { build job: '${j[0]}', parameters: [string(name: 'RISCV_CI', value: '\${params.RISCV_CI}')] }"
                }
                cps {
                    script("agent none\n" + dsl.join('\n'))
                }
            }
        }
    }

    static pipeline(dslFactory, pipelineName, ownerAndTarget, stepNames) {
        println("Building Jenkins job'$pipelineName'")

        dslFactory.pipelineJob(pipelineName) {
            parameters {
                stringParam("RISCV_CI")
            }
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
            definition {
                // inception, baby!
                def dsl = []
                for (s in stepNames) {
                    dsl += "stage('$s') { build job: '$pipelineName-$s', parameters: [string(name: 'RISCV_CI', value: '\${params.RISCV_CI}')] }"
                }
                cps {
                    script("agent none\n" + dsl.join("\n"))
                }
            }
        }
    }

    static step(dslFactory, pipelineName, pipelineStep) {
        def jobName = "${pipelineName}-${pipelineStep}"
        def scriptFile = "${pipelineName}_${pipelineStep}.sh"

        println("Building Jenkins job '$jobName'; uses '$scriptFile'")

        dslFactory.job(jobName) {
            parameters {
                stringParam("RISCV_CI")
            }
            steps {
                shell('printf "$WORKSPACE\\n$RISCV_CI\\n"')
                shell('cd $RISCV_CI/scripts && ' + scriptFile)
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
            }
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

def jobSpec = [['pk', 'riscv/riscv-pk'],
               ['fesvr', 'riscv/riscv-fesvr'],
               ['spike', 'riscv/riscv-isa-sim'],
               ['qemu', 'riscv/riscv-qemu'],
               ['rocketchip', 'freechipsproject/rocket-chip'],
               ['toolchain', 'riscv/riscv-gnu-toolchain']
]


def stepNames = ["build", "test", "deploy"]

// top pipeline
Builder.top(this, jobSpec)

// pipeline job for each 'jobspec'
jobSpec.each {
    Builder.pipeline(this, it[0], it[1], stepNames)
}

// for each pipeline, all pipeline steps
jobSpec.each { j ->
    stepNames.each {
        Builder.step(this, j[0], it)
    }
}

// views for each pipeline
jobSpec.each { Builder.view(this, it[0], stepNames) }

