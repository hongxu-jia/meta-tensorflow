DESCRIPTION = "A suite of web applications for inspecting and understanding \
your TensorFlow runs and graphs."
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://tensorboard-2.9.0.dist-info/LICENSE;md5=6767c3dee46f5a6b27902ea046d2389d"

SRC_URI = " \
    https://files.pythonhosted.org/packages/69/80/a3abccc4ea941c36741751206e40e619afe28652cf76f74cfa4c3e4248ba/tensorboard-2.9.0-py3-none-any.whl \
"

do_unpack[depends] += "python3-pip-native:do_populate_sysroot"

SRC_URI[md5sum] = "8521e8cb6f368497a7c39567f7efa4ae"
SRC_URI[sha256sum] = "bd78211076dca5efa27260afacfaa96cd05c7db12a6c09cc76a1d6b2987ca621"

RDEPENDS:${PN} += "python3 \
           python3-core \
           python3-numpy \
           python3-protobuf \
           python3-grpcio \
           python3-werkzeug \
           python3-six \
           python3-markdown \
           python3-absl \
           python3-google-auth \
           python3-google-auth-oauthlib \
           python3-requests \
"

inherit python3native

do_unpack () {
    echo "Installing pip package"
    ${STAGING_BINDIR_NATIVE}/pip3 install --disable-pip-version-check -v \
        -t ${S} --no-cache-dir --no-deps \
         ${DL_DIR}/tensorboard-2.9.0-py3-none-any.whl
}

do_install () {
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}
    cp -rf ${S}/* ${D}${PYTHON_SITEPACKAGES_DIR}/
    rm ${D}/${PYTHON_SITEPACKAGES_DIR}/bin ${D}/${PYTHON_SITEPACKAGES_DIR}/tensorboard-2.9.0.dist-info  -rf
    rm ${D}/${PYTHON_SITEPACKAGES_DIR}/bin -rf
}

do_configure[noexec] = "1"
do_compile[noexec] = "1"

FILES:${PN} += "${libdir}/*"
