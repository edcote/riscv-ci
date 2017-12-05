#!/bin/bash

REPOS=$(cat <<EOM
https://github.com/riscv/riscv-tools.git 
https://github.com/riscv/riscv-qemu.git
https://github.com/riscv/riscv-linux.git
https://github.com/riscv/riscv-pk.git
https://github.com/riscv/riscv-gnu-toolchain
https://github.com/riscv/riscv-isa-sim
https://github.com/riscv/riscv-fesvr
EOM
)

for r in $REPOS; do
    git clone --recursive $r
done
