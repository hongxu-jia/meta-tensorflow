include tensorflow.inc

SRC_URI += " \
    file://0012-disable-avxvnni-and-avx512fp16-for-x86.patch \
    file://0019-fix-build-failure-for-2.19.patch \
    file://0020-tensorflow-compiler-mlir-lite-fix-tensorflow_lite_qu.patch \
    file://0001-Add-hermetic-PYTHON-3.13-requirements-lock-file-in-T.patch \
    file://0001-support-python-3.33.patch \
"

inherit native

do_configure:append () {

    cat >> "${S}/bazelrc" <<-EOF
build --conlyopt=-Wno-stringop-overflow
build --conlyopt=-Wno-dangling-pointer
EOF

    SED_COMMAND="s#%%WORKDIR%%#${WORKDIR}#g"
    sed -i "${SED_COMMAND}" ${S}/configure.py

    ${TF_CONFIG} \
    ./configure
}

do_compile () {
    unset CC
    ${BAZEL} build \
        ${TF_ARGS_EXTRA} \
        -c opt \
        --subcommands --explain=${T}/explain.log \
        --verbose_explanations --verbose_failures \
        --repo_env=TF_PYTHON_VERSION=3.13 \
        --verbose_failures \
        //tensorflow/tools/pip_package:wheel
}

do_install() {
    echo "Installing pip package"
    install -d ${D}/${PYTHON_SITEPACKAGES_DIR}
    ${STAGING_BINDIR_NATIVE}/pip3 install --disable-pip-version-check -v --no-deps \
        -t ${D}/${PYTHON_SITEPACKAGES_DIR} --no-cache-dir \
         ${S}/bazel-bin/tensorflow/tools/pip_package/wheel_house/tensorflow-${PV}*cp313*.whl

    install -d ${D}${sbindir}
    (
        cd ${D}${PYTHON_SITEPACKAGES_DIR}/bin;
        for app in `ls`; do
            mv $app ${D}${sbindir}
        done

    )

}
