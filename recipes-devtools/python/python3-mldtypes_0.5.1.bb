SUMMARY = "The ml_dtypes is a stand-alone implementation of several NumPy dtype extensions used in machine learning libraries"
HOMEPAGE = "https://github.com/jax-ml/ml_dtypes"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI[sha256sum] = "ac5b58559bb84a95848ed6984eb8013249f90b6bab62aa5acbad876e256002c9"

inherit pypi python_setuptools_build_meta

SRCNAME ?= "ml_dtypes"
PYPI_SRC_URI = "https://files.pythonhosted.org/packages/32/49/6e67c334872d2c114df3020e579f3718c333198f8312290e09ec0216703a/${SRCNAME}-${PV}.tar.gz"
SRC_URI += "file://0001-fix-setuptools-missing.patch"
S = "${WORKDIR}/${SRCNAME}-${PV}"

DEPENDS = " \
    python3-pybind11-native \
    python3-numpy-native \
"

RDEPENDS:${PN} = " \
    python3-pybind11 \
    python3-numpy \
"

CCACHE_DISABLE = "1"

BBCLASSEXTEND = "native"
