import groovy.json.JsonSlurper
import hudson.*

def jsonSlurper = new JsonSlurper()

String myworkspace = hudson.model.Executor.currentExecutor().getCurrentWorkspace().toString()

Map jobSpec = jsonSlurper.parse(("${myworkspace}/jobs/pipespec.json" as File))

def build = new Builder()

//build.masterJob(this)

for (j in jobSpec) {
    build.workerJob(this, j.key, j.value)
}

/**
 * Wrapper class for build definition jobs.
 */
class Builder {
    String myworkspace = hudson.model.Executor.currentExecutor().getCurrentWorkspace().toString()

    /**
     * Returns contents of file as string.
     * @param f
     * @return String
     */
    def fromFile(f) {
        new File(f).text
    }

    /**
     * Creates a Jenkins pipeline job using job-dsl-plugin.
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

            definition {
                cps {
                    script(fromFile("${myworkspace}/pipelines/${name}_pipeline.groovy"))
                    sandbox(false)
                }
            }
        }
    }
}
