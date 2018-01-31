#!/usr/bin/env python3

import json

jsonfile = "pipespec.json"

pipespec = json.load(open(jsonfile))

# master job
pipelines = []
for pipe in pipespec:
    pipelines.append("    pipelines['{pipe}'] = {{ stage('{pipe}') {{ build job: '{pipe}', parameters: [[$class: 'StringParameterValue', name: 'RISCV_CI', value:\"${{env.RISCV_CI}}\"]] }} }}".format(**{'pipe': pipe}))

f = open('../pipelines/master_pipeline.groovy', 'w')
f.write("""\
node {{
    sh("echo WORKSPACE: ${{env.WORKSPACE}}")
    sh("echo RISCV_CI: ${{env.RISCV_CI}}")
    def pipelines = [:]
{}
    parallel pipelines
}}""".format('\n'.join(pipelines)))
f.close()

# pipeline pipelines
for pipe in pipespec:
    f = open("../pipelines/{}_pipeline.groovy".format(pipe), 'w')
    f.write("""\
node {{
sh('echo WORKSPACE: $WORKSPACE')

sh('echo RISCV_CI: $RISCV_CI')

def nodelib = load("${{env.RISCV_CI}}/jobs/nodelib.groovy")

stage('Clone') {{
    checkout([ $class: 'GitSCM',
                    branches: [[name: '*/master']],
                    userRemoteConfigs: [[url: 'https://github.com/{github}']],
                    extensions: [ [$class: 'SubmoduleOption', recursiveSubmodules: true, disableSubmodules: false, timeout: 120] ]
             ])
    sh('sleep 2s')
}}

stage('Build') {{
    nodelib.{pipe}_build()
    sh('sleep 2s')
}}

stage('Test') {{
    nodelib.{pipe}_test()
    sh('sleep 2s')
}}

stage('Deploy') {{
    nodelib.{pipe}_test()
    sh('sleep 2s')
}}
}}
    """.format(**{'github': pipespec[pipe]['github'], 'pipe': pipe}))
    f.close

# dummy pipelines
f = open("nodelib.groovy", 'w')
f.write("// DO NOT EDIT, MANAGED FILE\n")
for pipe in pipespec:
    for stage in ["build", "test", "deploy"]:
        f.write("""\
def {}_{}() {{
    sh(\"\"\"
/bin/true
\"\"\")
}}
""".format(pipe, stage))
f.write("return this;\n")
f.close()
