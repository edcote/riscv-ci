#!/bin/bash

REPOS=$(cat <<EOM
riscv-tools
riscv-qemu
riscv-linux
riscv-pk
riscv-gnu-toolchain
riscv-isa-sim
riscv-fesvr
EOM
)

for r in $REPOS; do
    cd $RISCV_CI/$r && git pull origin master && git submodule update --init --recursive $r
done
