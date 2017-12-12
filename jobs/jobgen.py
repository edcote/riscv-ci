#!/usr/env python3

import json

jsonfile = "/home/edc/gitrepos/riscv-ci/jobs/jobspec.json"

jobspec = json.load(open(jsonfile))

# master job
jobs = []
for job in jobspec:
    jobs.append(
        "    jobs['{}'] = {{ build job: '{}', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:\"${{env.RISCV_CI}}\"]] }}".format(
            job, job))

f = open('../pipelines/master_pipeline.groovy', 'w')
f.write("""\
node {{
    sh("echo WORKSPACE: ${{env.WORKSPACE}}")
    sh("echo RISCV_CI: ${{env.RISCV_CI}}")
    def jobs = [:]
{}
    parallel jobs
}}""".format('\n'.join(jobs)))
f.close()

# pipeline jobs
for job in jobspec:
    f = open('../pipelines/{}_pipeline.groovy'.format(job), 'w')
    f.write("""\
// assuming that stage('Compile') is done b 
node {{

sh('echo WORKSPACE: $WORKSPACE')

sh('echo RISCV_CI: $RISCV_CI')       

stage('Compile') {{
    checkout([ $class: 'GitSCM',
                    branches: [[name: '*/master']],
                    userRemoteConfigs: [[url: 'https://github.com/{}']],
                    extensions: [ [$class: 'SubmoduleOption', recursiveSubmodules: false, disableSubmodules:false] ]
                  ])
    sh('sleep 2s')
}}
stage('Build') {{
    def joblib = load("${{env.RISCV_CI}}/pipelines/{}_build.groovy")
    joblib.binTrue()
    sh('sleep 2s')
}}        
stage('Test') {{
    def joblib = load("${{env.RISCV_CI}}/pipelines/{}_test.groovy")
    sh('echo PWD: $PWD')
    joblib.binTrue()
    sh('sleep 2s')
}}
}}
    """.format(jobspec[job]['github'], job, job))
    f.close

# dummy jobs
for job in jobspec:
    for stage in ["build", "test"]:
        f = open('../pipelines/{}_{}.groovy'.format(job, stage), 'w')
        f.write("""\
def binTrue() {
    sh('/bin/true')
}

def binFalse() {
    sh('/bin/false')
}
return this;
""")
        f.close()
