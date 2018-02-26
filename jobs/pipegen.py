#!/usr/bin/env python3

import json
import sys

jsonfile = "pipespec.json"

pipespec = json.load(open(jsonfile))

def copyA(x):
    return "    copyArtifacts filter: '**/*', fingerprintArtifacts: true, projectName: '{}', selector: lastSuccessful()".format(x)

# pipeline pipelines
for pipe in pipespec:
    f = open("../pipelines/{}_pipeline.groovy".format(pipe), 'w')

    copyArtifacts = "\n".join(list(map(copyA, pipespec[pipe]['dependencies'])))

    f.write("""\
properties([
buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')),
[$class: 'CopyArtifactPermissionProperty', projectNames: '*']
])

node {{
def nodelib = load("${{env.RISCV_CI}}/jobs/nodelib.groovy")

stage('Clone') {{
    checkout([ $class: 'GitSCM',
                    branches: [[name: '*/master']],
                    userRemoteConfigs: [[url: 'https://github.com/{github}']],
                    extensions: [ [$class: 'SubmoduleOption', recursiveSubmodules: true, disableSubmodules: false, timeout: 120] ]
             ])
    sh('sleep 0.1s')
}}

stage('Dependencies') {{
    {copyArtifacts}
    sh("cd riscv-root && if [ -f riscv.tgz ]; then tar -zxf riscv.tgz; else echo 'riscv.tgz not found' && true; fi")
    sh('sleep 0.1s')
}}

stage('Build') {{
    sh('printenv')
    nodelib.{pipe}_build()
    sh('sleep 0.1s')
}}

stage('Test') {{
    nodelib.{pipe}_test()
    sh('sleep 0.1s')
}}

stage('Archive') {{
    sh("cd riscv-root && tar -czvf riscv.tgz *")
    archiveArtifacts artifacts: 'riscv-root/*.tgz', excludes: ''
    sh('sleep 0.1s')
}}
}}
    """.format(**{'github': pipespec[pipe]['github'], 'pipe': pipe, 'copyArtifacts': copyArtifacts}))
    f.close

# dummy pipelines
f = open("nodelib.groovy", 'w')
f.write("// DO NOT EDIT, MANAGED FILE\n")
for pipe in pipespec:
    # sh
    for stage in ["build", "test"]:
        f.write("""\
def {}_{}() {{
    withEnv(["PATH=${{env.WORKSPACE}}/riscv-root/bin:${{env.PATH}}"]) {{ 
    sh(\"\"\"
{}
\"\"\")
    }}
}}


""".format(pipe, stage, " && \n".join(pipespec[pipe][stage])))

f.write("return this;\n")
f.close()

