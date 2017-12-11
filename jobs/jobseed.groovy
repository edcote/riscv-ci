class Builder {
    static top(dslFactory, jobSpec) {
        println("Building Jenkins top pipeline")

        dslFactory.pipelineJob("top") {
            scm {
                git {
                    remote { github("edcote/riscv-ci") }
                    branches('develop')
                }
            }
            definition {
                def dsl = []
                for (j in jobSpec) {
                    dsl += "job['${j[0]}'] = { build job: '${j[0]}', parameters: [[\$class: 'StringParameterValue', name: 'RISCV_CI', value:"\${env.WORKSPACE}\"]] }"
                }
                cps {
                    script("""\
node {
def jobs = [:]
${dsl.join('\n')}
parallel jobs
}
)""")
                }
            }
        }
    }

    static pipeline(dslFactory, pipelineName, ownerAndTarget, stepNames) {
        println("Building Jenkins job'$pipelineName'")

        dslFactory.pipelineJob(pipelineName) {
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
            parameters {
                stringParam("RISCV_CI", "/you/must/set/me")
            }
            definition {
                // inception, baby!
                def dsl = []
                for (s in stepNames) {
                    dsl += "stage('$s') { sh('echo w:\$WORKSPACE -- r:\$RISCV_CI') }"
                }
                cps {
                    script("node { ${dsl.join("\n")} }")
                }
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

// views for each pipeline
jobSpec.each { Builder.view(this, it[0], stepNames) }

