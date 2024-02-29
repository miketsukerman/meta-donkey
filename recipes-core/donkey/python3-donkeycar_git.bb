DESCRIPTION = ""
SECTION = "devel"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM ?= "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRCREV = "d60dcb5e0627465851873f35e80d3b3863973fc6"
SRC_URI = "git://github.com/autorope/donkeycar.git;protocol=https;branch=main"

S = "${WORKDIR}/git"

inherit setuptools3

do_configure:prepend() {
cat > ${S}/setup.py <<-EOF
from setuptools import setup

setup()
EOF
}
