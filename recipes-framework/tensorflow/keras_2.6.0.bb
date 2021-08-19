DESCRIPTION = "TensorFlow Keras is an implementation of the Keras API that\
 uses TensorFlow as a backend."
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c573baaa40a28002a2d03d3e7e9bc583"

SRC_URI = "git://github.com/keras-team/keras.git;branch=r2.6 \
           file://0001-customize-for-yocto.patch \
          "

SRCREV = "3a33d53ea4aca312c5ad650b4883d9bac608a32e"
S = "${WORKDIR}/git"

inherit python3native bazel

DEPENDS += " \
    python3-pip-native \
    python3-wheel-native \
    python3-absl-native \
    python3-wrapt-native \
    python3-opt-einsum-native \
    python3-gast-native \
    python3-astunparse-native \
    python3-termcolor-native \
    flatbuffers-native \
    tensorflow-native \
"

do_compile () {
    unset CC
    export TMPDIR="${WORKDIR}"
    export PYTHON_BIN_PATH="${PYTHON}"

    ${BAZEL} build \
        --subcommands --explain=${T}/explain.log \
        --verbose_explanations --verbose_failures \
        --verbose_failures \
        --python_path="${PYTHON}" \
        //keras/tools/pip_package:build_pip_package

    ${S}/bazel-bin/keras/tools/pip_package/build_pip_package \
        ${WORKDIR}/keras_pip
}

do_install () {
    echo "Installing pip package"
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}
    ${STAGING_BINDIR_NATIVE}/pip3 install --disable-pip-version-check -v --no-deps \
        -t ${D}/${PYTHON_SITEPACKAGES_DIR} --no-cache-dir ${WORKDIR}/keras_pip/*.whl

}

FILES_${PN} += "${libdir}/*"

BBCLASSEXTEND = "native"
