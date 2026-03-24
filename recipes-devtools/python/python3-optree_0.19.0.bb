SUMMARY = "Optimized PyTree Utilities."
HOMEPAGE = "https://github.com/metaopt/optree"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=936b16b5b0a197fc3bb914f69ebc8e31"

SRC_URI[sha256sum] = "bc1991a948590756409e76be4e29efd4a487a185056d35db6c67619c19ea27a1"

inherit pypi python_setuptools_build_meta

DEPENDS += " \
    python3-pybind11-native \
"

UPSTREAM_CHECK_PYPI_PACKAGE = "optree"

INSANE_SKIP:${PN} += " \
    already-stripped \
"

BBCLASSEXTEND = "native"
