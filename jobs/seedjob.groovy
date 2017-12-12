import groovy.json.JsonSlurper

import hudson.FilePath
import hudson.*

def jsonSlurper = new JsonSlurper()

String myworkspace = hudson.model.Executor.currentExecutor().getCurrentWorkspace().toString()
//String myworkspace = "./"

Map jobSpec = jsonSlurper.parse(("${myworkspace}/jobs/jobspec.json" as File))

def build = new Builder()

build.masterJob(this)

for (j in jobSpec) {
    build.workerJob(this, j.key, j.value)
}

/**
 * Wrapper class for build definition jobs.
 */
class Builder {
    // FIXME: DNR
    String myworkspace = hudson.model.Executor.currentExecutor().getCurrentWorkspace().toString()
    //String myworkspace = "./"

    /**
     * Builds a Jenkins master job using pipeline DSL.
     * @param dslFactory
     * @return N/A
     */
    def masterJob(dslFactory) {
        // jdsl
        dslFactory.pipelineJob("master") {
            scm {
                github('edcote/riscv-ci', 'develop')
            }
            parameters {
                stringParam("RISCV_CI", "${myworkspace}") // .. seedjob will set
            }
            definition {
                cps {
                    script("evaluate(new File(\"\${env.RISCV_CI}/pipelines/master_pipeline.groovy\"))")
                    sandbox(false)
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
        // jdsl
        dslFactory.pipelineJob(name) {
            scm {
                git("http://github.com/" + job['github']) {
                    extensions {
                        submoduleOptions {
                            recursive(false)
                        }
                    }
                }
            }
            parameters {
                stringParam("RISCV_CI", "/you/must/set/me") // .. master job will set
            }
            definition {
                cps {
                    script("evaluate(new File(\"\${env.RISCV_CI}/pipelines/${name}_pipeline.groovy\"))")
                }
            }
        }
    }
}
