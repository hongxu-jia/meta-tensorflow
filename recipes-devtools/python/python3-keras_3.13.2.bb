SUMMARY = "Keras 3: Deep Learning for Humans"
HOMEPAGE = "https://github.com/keras-team/keras"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${UNPACKDIR}/LICENSE;md5=2b42edef8fa55315f34f2370b4715ca9"

SRC_URI[sha256sum] = "62f0123488ac87c929c988617e14f293f7bc993811837d08bb37eff77adc85a9"

inherit pypi python_setuptools_build_meta

SRCNAME ?= "keras"

SRC_URI += " \
    file://LICENSE \
"
S = "${UNPACKDIR}/${SRCNAME}-${PV}"

UPSTREAM_CHECK_PYPI_PACKAGE = "keras"
UPSTREAM_CHECK_URI = "https://pypi.org/simple/${UPSTREAM_CHECK_PYPI_PACKAGE}/"

DEPENDS += " \
    python3-absl-native \
    python3-numpy-native \
    python3-rich-native \
    python3-h5py \
    python3-mldtypes-native \
    python3-packaging \
"

DEPENDS = " \
    python3-pybind11-native \
"

RDEPENDS:${PN} = " \
    python3-pybind11 \
    python3-numpy \
"

CCACHE_DISABLE = "1"

BBCLASSEXTEND = "native"
