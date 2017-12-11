class Builder {
    static top(dslFactory, jobSpec) {
        println("Building Jenkins top pipeline")

        // returns pipeline script that creates a Jenkins job
        def mkjob = { String j ->
            "jobs['${j}'] = { build job: '${j}', parameters: [[\$class: 'StringParameterValue', name: 'RISCV_CI', value:\"\${env.WORKSPACE}\"]] }"
        }

        // returns pipeline script that creates a parallel Jenkins node
        def mkparnode = { n ->
            """\
node {
def jobs = [:]
${n.join('\n')}
parallel jobs
}"""
        }

        dslFactory.pipelineJob("top") {
            scm {
                git {
                    remote { github("edcote/riscv-ci") }
                    branches('develop')
                }
            }
            definition {
                def jobs = []
                for (j in jobSpec) {
                    jobs += mkjob(j[0])
                }
                cps {
                    script(mkparnode(jobs))
                }
            }
        }
    }

    static pipeline(dslFactory, pipelineName, ownerAndTarget, stepNames) {
        println("Building Jenkins job'$pipelineName'")

        def mkstage = { n ->
            """\
    stage('$n') {
        sh('echo WORKSPACE: \$WORKSPACE')
        sh('echo RISCV_CI: \$RISCV_CI')
        sh('sleep 15s')
    }
        """
        }

        def mkpipnode = { x ->
            """\
node {
${x.join("\n")}
}"""
        }

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
                    dsl += mkstage(s)
                }
                cps {
                    script(mkpipnode(dsl))
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
               ['toolchain', 'riscv/riscv-gnu-toolchain']]


def stepNames = ["build", "test", "deploy"]

// top pipeline
Builder.top(this, jobSpec)

// pipeline job for each 'jobspec'
jobSpec.each {
    Builder.pipeline(this, it[0], it[1], stepNames)
}

// views for each pipeline
jobSpec.each {
    Builder.view(this, it[0], stepNames)
}
