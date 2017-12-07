// https://github.com/jenkinsci/job-dsl-plugin/wiki


// https://jenkinsci.github.io/job-dsl-plugin/  <-- API viewer

def githubJob(id) {
    job("pk") {
        description 'GitHub Jenkins job'
        scm {
            github(id)
        }
    }
}

githubJob("riscv/pk")
