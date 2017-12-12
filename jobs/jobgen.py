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
    sh('echo mWORKSPACE: $WORKSPACE')
    sh('echo mRISCV_CI: $RISCV_CI')
    sh('echo PWD: $PWD')
    def joblib = load("${{env.RISCV_CI}}@script/pipelines/{}_build.groovy")
    joblib.true()
    sh('sleep 2s')
}}        
stage('Test') {{
    sh('echo WORKSPACE: $WORKSPACE')
    sh('echo RISCV_CI: $RISCV_CI')
    sh('echo PWD: $PWD')
    def joblib = load("${{env.RISCV_CI}}@script/pipelines/{}_test.groovy")
    joblib.true()
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
def true():
    sh('echo wWORKSPACE: $WORKSPACE')
    sh('echo wRISCV_CI: $RISCV_CI')       
    sh('/bin/true')
return this;
""")
        f.close()
