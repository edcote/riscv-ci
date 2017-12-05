#!/bin/bash

REPOS=$(cat <<EOM
https://github.com/riscv/riscv-tools.git 
https://github.com/riscv/riscv-qemu.git
https://github.com/riscv/riscv-linux.git
https://github.com/riscv/riscv-pk.git
https://github.com/riscv/riscv-gnu-toolchain.git
https://github.com/riscv/riscv-isa-sim.git
https://github.com/riscv/riscv-fesvr.git
EOM
)

for r in $REPOS; do
    git clone --recursive $r
=$(cat <<EOM

