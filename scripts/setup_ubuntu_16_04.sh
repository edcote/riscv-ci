#!/bin/bash

INSTALL="apt-get install"

PKGS=$(cat <<EOM
autoconf
automake
autotools-dev
curl
device-tree-compiler
libmpc-dev
libmpfr-dev
libgmp-dev
gawk
build-essential
bison
flex
texinfo
gperf
libtool
patchutils
zlib1g-dev
bc
libusb-1.0-0-dev
libncurses5-dev
gcc
libc6-dev
pkg-config
bridge-utils
uml-utilities
zlib1g-dev
libglib2.0-dev
autoconf
automake
libtool
libsdl1.2-dev
environment-module
EOM
)
for p in $PKGS; do
    $INSTALL $p
done

