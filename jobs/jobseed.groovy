import groovy.json.JsonSlurper

//-----------------------------------------------------------------------------

def jsonSlurper = new JsonSlurper()

Map jobSpec = jsonSlurper.parse(("jobs/jobspec.json" as File))

build = new Builder()

build.bossJob(this, jobSpec)

for (j in jobSpec) {
    build.workerJob(this, j.key, j.value)
}

//jobSpec.each {
//    build.view(this)
//}

//-----------------------------------------------------------------------------


class Builder {
    /**
     * Builds a Jenkins master job using pipeline DSL.
     * @param dslFactory
     * @return N/A
     */
    def bossJob(dslFactory, jobSpec) {
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
                    jobs += mkjob(j)
                }
                cps {
                    script(mkparnode(jobs))
                }
            }
        }
    }

    /**
     * Builds a Jenkins pipeline job using pipeline DSL.
     * @param dslFactory
     * @return N/A
     */
    def workerJob(dslFactory, name, job) {
        dslFactory.pipelineJob(name) {
            scm {
                git {
                    remote { github(job['github']) }
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
                for (stage in ["build", "test", "deploy"]) {
                    dsl += mkstage(stage)
                }
                cps {
                    script(mkpipenode(dsl))
                }
            }
        }
    }

    /*
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
    */
    private def mkjob = { j ->
        "jobs['$j'] = { build job: '$j', parameters: [[\$class: 'StringParameterValue', name: 'RISCV_CI', value:\"\${env.WORKSPACE}\"]] }"
    }

    private def mkparnode = { n ->
        """\
node {
def jobs = [:]
${n.join('\n')}
parallel jobs
}"""
    }

    def mkstage = { s ->
        """\
    stage('$s') {
        sh('echo WORKSPACE: \$WORKSPACE')
        sh('echo RISCV_CI: \$RISCV_CI')
        sh('sleep 15s')
    }
        """
    }

    def mkpipenode = { x ->
        """\
node {
${x.join("\n")}
}"""
    }
}
