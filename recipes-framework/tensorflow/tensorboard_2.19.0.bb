DESCRIPTION = "A suite of web applications for inspecting and understanding \
your TensorFlow runs and graphs."
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://tensorboard-2.19.0.dist-info/LICENSE;md5=6767c3dee46f5a6b27902ea046d2389d"

SRC_URI = " \
    https://files.pythonhosted.org/packages/5d/12/4f70e8e2ba0dbe72ea978429d8530b0333f0ed2140cc571a48802878ef99/tensorboard-2.19.0-py3-none-any.whl \
"


do_unpack[depends] += "python3-pip-native:do_populate_sysroot"

SRC_URI[sha256sum] = "5e71b98663a641a7ce8a6e70b0be8e1a4c0c45d48760b076383ac4755c35b9a0"

RDEPENDS:${PN} += "python3 \
           python3-core \
           python3-numpy \
           python3-protobuf \
           python3-grpcio \
           python3-werkzeug \
           python3-six \
           python3-markdown \
           python3-absl \
           python3-requests \
"

inherit python3native

do_unpack () {
    echo "Installing pip package"
    ${STAGING_BINDIR_NATIVE}/pip3 install --disable-pip-version-check -v \
        -t ${S} --no-cache-dir --no-deps \
         ${DL_DIR}/tensorboard-2.19.0-py3-none-any.whl

    rm -rf ${S}/bin ${S}/tensorboard-2.19.0.dist-info/direct_url.json
}

do_install () {
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}
    cp -rf ${S}/* ${D}${PYTHON_SITEPACKAGES_DIR}/
}

do_configure[noexec] = "1"
do_compile[noexec] = "1"

FILES:${PN} += "${libdir}/*"
