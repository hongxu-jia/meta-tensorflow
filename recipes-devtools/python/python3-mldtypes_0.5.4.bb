SUMMARY = "The ml_dtypes is a stand-alone implementation of several NumPy dtype extensions used in machine learning libraries"
HOMEPAGE = "https://github.com/jax-ml/ml_dtypes"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI[sha256sum] = "8ab06a50fb9bf9666dd0fe5dfb4676fa2b0ac0f31ecff72a6c3af8e22c063453"

inherit pypi python_setuptools_build_meta

PYPI_PACKAGE = "ml_dtypes"

UPSTREAM_CHECK_PYPI_PACKAGE = "${PYPI_PACKAGE}"
UPSTREAM_CHECK_URI = "https://pypi.org/simple/${UPSTREAM_CHECK_PYPI_PACKAGE}/"

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
