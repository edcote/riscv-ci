def riscvURL = "https://github.com/riscv/"
def freechipsURL = "https://github.com/freechips/"

job("pk") {
  scm {
    git(riscvURL+"/riscv-pk.git")
  }
}

