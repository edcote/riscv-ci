#!/usr/bin/env python3

import json

jsonfile = "pipespec.json"

pipespec = json.load(open(jsonfile))

# pipeline pipelines
for pipe in pipespec:
    f = open("../pipelines/{}_pipeline.groovy".format(pipe), 'w')
    f.write("""\
node {{
def nodelib = load("${{env.RISCV_CI}}/jobs/nodelib.groovy")

stage('Clone') {{
    sh('printenv')
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

stage('Archive') {{
    nodelib.{pipe}_archive()
    sh('sleep 2s')
}}
}}
    """.format(**{'github': pipespec[pipe]['github'], 'pipe': pipe}))
    f.close

# dummy pipelines
f = open("nodelib.groovy", 'w')
f.write("// DO NOT EDIT, MANAGED FILE\n")
for pipe in pipespec:
    # sh
    for stage in ["build", "test"]:
        f.write("""\
def {}_{}() {{
    sh(\"\"\"
{}
\"\"\")
}}
""".format(pipe, stage, " && \n".join(pipespec[pipe][stage])))
    # archiveArtifaces
    for stage in ["archive"]:
        f.write("""\
def {}_{}() {{
    archiveArtifacts artifacts: 'build/riscv/**/*', excludes: ''
}}

""".format(pipe, stage, " && \n".join(pipespec[pipe][stage])))

    


f.write("return this;\n")
f.close()

