DESCRIPTION = "A suite of web applications for inspecting and understanding \
your TensorFlow runs and graphs."
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://tensorboard-2.10.0.dist-info/LICENSE;md5=6767c3dee46f5a6b27902ea046d2389d"

SRC_URI = " \
    https://files.pythonhosted.org/packages/6b/42/e271c40c84c250b52fa460fda970899407c837a2049c53969f37e404b1f6/tensorboard-2.10.0-py3-none-any.whl \
"

do_unpack[depends] += "python3-pip-native:do_populate_sysroot"

SRC_URI[md5sum] = "e8973431406c2226ff0732ab3d4672d9"
SRC_URI[sha256sum] = "76c91a5e8959cd2208cc32cb17a0cb002badabb66a06ac2af02a7810f49a59e3"

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
         ${DL_DIR}/tensorboard-2.10.0-py3-none-any.whl
}

do_install () {
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}
    cp -rf ${S}/* ${D}${PYTHON_SITEPACKAGES_DIR}/
    rm ${D}/${PYTHON_SITEPACKAGES_DIR}/bin ${D}/${PYTHON_SITEPACKAGES_DIR}/tensorboard-2.10.0.dist-info  -rf
    rm ${D}/${PYTHON_SITEPACKAGES_DIR}/bin -rf
}

do_configure[noexec] = "1"
do_compile[noexec] = "1"

FILES:${PN} += "${libdir}/*"
