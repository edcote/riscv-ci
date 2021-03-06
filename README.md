# RISC-V Continuous Integration Experiment

### Jenkins Install

Use the following to install Jenkins on Ubuntu.

    wget -q -O - https://pkg.jenkins.io/debian-stable/jenkins.io.key | sudo apt-key add -i
    ## echo "deb https://pkg.jenkins.io/debian-stable binary/" >> /etc/apt/sources.list ##
    apt-get update
    apt-get install jenkins
    service jenkins restart
    
Here are the required plugins (*FIXME*: find method for automatic install):

* [job-dsl](https://plugins.jenkins.io/job-dsl) (Groovy DSL to describe Jenkins jobs)
* [git](https://plugins.jenkins.io/git)
* [github](https://plugins.jenkins.io/github)
* [authorize-project](https://plugins.jenkins.io/authorize-project) (To allow a specific user to run a job)

### Job DSL Plugin

The plugin is used create Jenkins jobs (different than pipelines) using Groovy scripts. These scripts are revision controlled and unit tested in this repository. Use command `gradle` to run the unit tests (*FIXME*: currently broken).

    $ sudo apt install gradle  # if needed
    $ cd <path to repo>
    $ gradle build

### Job Pipelines

Job pipeline scripts are automatically generated by `build/pipegen.py` which consumes pipeline definitions in `build/pipespec.json`. Pipelines are stored under `pipelines/` and reference `build/nodelib.groovy`. (*FIXME*: this two file solution was done for some reason or another, it probably needs a refactor).

More info on Jenkins job pipelines [here](https://jenkins.io/solutions/pipeline/).

### Additional Information

A seed job in Jenkins is required to generate the revision-controlled jobs.  More information on seed jobs [here](https://github.com/jenkinsci/job-dsl-plugin/wiki/Tutorial---Using-the-Jenkins-Job-DSL). The seed job for this project is in `jobs/seedjob.groovy`.

See [JENKINS-43509](https://issues.jenkins-ci.org/browse/JENKINS-43509) for info about authentication.
