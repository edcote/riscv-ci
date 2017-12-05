#!/bin/bash

REPOS=$(cat <<EOM
https://github.com/riscv/riscv-tools.git         -o riscv-tools
https://github.com/riscv/riscv-qemu.git          -o riscv-qemu
https://github.com/riscv/riscv-linux.git         -o riscv-linux
https://github.com/riscv/riscv-pk.git            -o riscv-pk
https://github.com/riscv/riscv-gnu-toolchain.git -o riscv-gnu-toolchain
https://github.com/riscv/riscv-isa-sim.git       -o riscv-isa_sim
https://github.com/riscv/riscv-fesvr.git         -o riscv-fesvr
EOM
)

for r in $REPOS; do
    cd $RISCV_CI && git clone --recursive $r
done

