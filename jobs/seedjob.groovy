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
     * Returns contents of file as string.
     * @param f
     * @return String
     */
    def fromFile(f) {
      new File(f).text
    }

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
                    script(fromFile("${myworkspace}/pipelines/master_pipeline.groovy"))
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
                stringParam("RISCV_CI", "${myworkspace}") // .. master job will set
            }
            definition {
                cps {
                    script(fromFile("${myworkspace}/pipelines/${name}_pipeline.groovy"))
                    sandbox(false)
                }
            }
        }
    }
}
