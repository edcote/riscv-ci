import com.here.gradle.plugins.jobdsl.util.GroovySeedJobBuilder

// See: https://github.com/heremaps/gradle-jenkins-jobdsl-plugin#writing-job-dsl-scripts


def x = new GroovySeedJobBuilder()

def riscvURL = "https://github.com/riscv/"
def freechipsURL = "https://github.com/freechips/"

job("pk") {
    scm {
        git(riscvURL + "/riscv-pk.git")
    }
}

