import groovy.json.JsonSlurper

import hudson.FilePath
import hudson.*

def jsonSlurper = new JsonSlurper()

// for server ..
hudson.FilePath workspace = hudson.model.Executor.currentExecutor().getCurrentWorkspace()
Map jobSpec = jsonSlurper.parse(("${workspace}/jobs/jobspec.json" as File))
// for testing ..
//Map jobSpec = jsonSlurper.parse(("jobs/jobspec.json" as File))

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
        dslFactory.pipelineJob("Master") {
            scm {
                github('edcote/riscv-ci', 'develop')
            }
            definition {
                cpsScm {
                    scm {
                        github('edcote/riscv-ci', 'develop')
                    }
                    scriptPath('\$\{WORKSPACE\}/pipelines/master_pipeline.groovy')
                    //scriptPath("pipelines/master_pipeline.groovy")
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
                github(job['github'], 'develop') {
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
                        github(job['github'], 'develop') {
                            extensions {
                                submoduleOptions {
                                    recursive(false)
                                }
                            }
                        }
                    }
                    scriptPath("\$\{RISCV_CI\}/pipelines/${name}_pipeline.groovy")
                    //scriptPath("pipelines/${name}_pipeline.groovy")
                }
            }
        }
    }

/*
static view(viewFactory, pipelineName, jobNames) {
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

}


