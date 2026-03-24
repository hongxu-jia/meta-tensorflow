SUMMARY = "A tensor contraction order optimizer"
HOMEPAGE = "https://github.com/dgasmith/opt_einsum"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5ab423c88cf3e69553decf93419f53ac"

SRC_URI[sha256sum] = "96ca72f1b886d148241348783498194c577fa30a8faac108586b14f1ba4473ac"

inherit pypi python_hatchling

PYPI_PACKAGE = "opt_einsum"

DEPENDS += " \
    python3-hatch-vcs-native \
    python3-hatch-fancy-pypi-readme-native \
"

UPSTREAM_CHECK_PYPI_PACKAGE = "${PYPI_PACKAGE}"

BBCLASSEXTEND = "native"
