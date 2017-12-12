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
    /**
     * Builds a Jenkins master job using pipeline DSL.
     * @param dslFactory
     * @return N/A
     */
    def masterJob(dslFactory) {
        // jdsl
        dslFactory.pipelineJob("master") {
            scm {
                github('edcote/riscv-ci', 'develop') // don't think this does anything
            }
            definition {
                cpsScm {
                    scm {
                        github('edcote/riscv-ci', 'develop')
                        scriptPath("@script/pipelines/master_pipeline.groovy")
                    }
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
                stringParam("RISCV_CI", "/you/must/set/me")
            }
            definition {
                cpsScm {
                    scm {
                        github('edcote/riscv-ci', 'develop')
                        scriptPath("pipelines/${name}_pipeline.groovy")
                    }
                }
            }
        }
    }
}
