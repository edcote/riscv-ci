# RISC-V Continuous Integration (Experimental)

## Jenkins

### Installation

Use the following to install Jenkins on Ubuntu.

    wget -q -O - https://pkg.jenkins.io/debian-stable/jenkins.io.key | sudo apt-key add -i
    ## echo "deb https://pkg.jenkins.io/debian-stable binary/" >> /etc/apt/sources.list ##
    apt-get update
    apt-get install jenkins
    service jenkins restart
    
Here is a list of plugins. FIXME: need method to automatically install these.

https://plugins.jenkins.io/job-dsl
https://plugins.jenkins.io/git
https://plugins.jenkins.io/github
https://plugins.jenkins.io/authorize-project
+ many others

### [Job DSL](https://wiki.jenkins.io/display/JENKINS/Job+DSL+Plugin)

This plugin is used Jenkins jobs (different than pipelines) using Groovy scripts.  These scripts are revision controlled and unit tested in this repository.  Use `gradle` to run these unit tests.

    cd <path to riscv-ci repo>
    gradle build

A seed job in Jenkins is required to generate the revision-controlled jobs.  More information [here](https://github.com/jenkinsci/job-dsl-plugin/wiki/Tutorial---Using-the-Jenkins-Job-DSL).
