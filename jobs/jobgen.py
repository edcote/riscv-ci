#!/usr/env python3

import json

jsonfile = "/home/edc/gitrepos/riscv-ci/jobs/jobspec.json"

jobspec = json.load(open(jsonfile))

# master job
jobs = []
for job in jobspec:
    jobs.append(
        "    jobs['{}'] = {{ build job: '{}', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:\"${{env.WORKSPACE}}\"]] }}".format(
            job, job))

f = open('../pipelines/master_pipeline.groovy', 'w')
f.write("""\
node {{
    sh("echo WORKSPACE: ${{env.WORKSPACE}}")
    sh('echo WORKSPACE: $WORKSPACE')
    def jobs = [:]
{}
    parallel jobs
}}""".format('\n'.join(jobs)))
f.close()

# pipeline jobs
for job in jobspec:
    f = open('../pipelines/{}_pipeline.groovy'.format(job), 'w')
    f.write("""\
node {{
stage('Build') {{
    def joblib = load("${{env.RISCV_CI}}/pipelines/{}_build.groovy")
    sh('echo WORKSPACE: $WORKSPACE')
    sh('echo RISCV_CI: $RISCV_CI')
    joblib.binTrue()
    sh('sleep 2s')
}}        
stage('Test') {{
    def joblib = load("${{env.RISCV_CI}}/pipelines/{}_test.groovy")
    sh('echo WORKSPACE: $WORKSPACE')
    sh('echo RISCV_CI: $RISCV_CI')
    sh('echo PWD: $PWD')
    joblib.binTrue()
    sh('sleep 2s')
}}
}}
    """.format(job, job))
    f.close

# dummy jobs
for job in jobspec:
    for stage in ["build", "test"]:
        f = open('../pipelines/{}_{}.groovy'.format(job, stage), 'w')
        f.write("""\
def binTrue() {
    sh('echo WORKSPACE: $WORKSPACE')
    sh('echo RISCV_CI: $RISCV_CI')       
    sh('/bin/true')
}

def binFalse() {
    sh('echo WORKSPACE: $WORKSPACE')
    sh('echo RISCV_CI: $RISCV_CI')       
    sh('/bin/false')
}
return this;
""")
        f.close()
