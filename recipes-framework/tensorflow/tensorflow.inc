DESCRIPTION = "TensorFlow C/C++ Libraries"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4158a261ca7f2525513e31ba9c50ae98"

DEPENDS = " \
    bazel-native \
    protobuf-native \
    util-linux-native \
    patchelf-native \
    protobuf \
"
SRCREV = "e36baa302922ea3c7131b302c2996bd2051ee5c4"
SRC_URI = "git://github.com/tensorflow/tensorflow.git;branch=r2.19;protocol=https \
    file://0001-lite-fix-compile-failure-without-XNNPACK-engine.patch \
    file://0001-support-gcc-15.patch \
    file://0001-use-local-bazel-to-workaround-bazel-paralle-issue.patch \
    file://0002-third_party-eigen_archive-workaround-ice-failure-whi.patch \
    file://0003-drop-bazel_toolchains_repositories-to-support-offlin.patch \
    file://0004-drop-v2-config.patch \
    file://0005-hardcode-go-SDKS.patch \
    file://0006-fix-build-failure.patch \
    file://0007-support-hermetic-Python.patch \
    file://0008-make-hermetic-python3-support-offline-build.patch \
    file://0009-Fix-build-with-gcc-12.patch \
    file://0010-fix-patchelf-not-found.patch \
    file://0011-fix-llvm-project-timeout-occasionally.patch \
    file://0001-do-not-patch-.so-files-for-linux.patch \
    file://0001-third_party-ducc-fix-ambiguous-failure.patch \
    file://0001-third_party-tf_runtime-fix-compile-failure.patch \
"

DEPENDS += " \
    python3 \
    python3-requests-native \
    python3-numpy-native \
    python3-keras-applications-native \
    python3-keras-preprocessing-native \
    python3-pip-native \
    python3-wheel-native \
    python3-packaging-native \
"

inherit python3native bazel

TF_CONFIG ?= " \
    TF_NEED_CUDA=0 \
    TF_NEED_OPENCL_SYCL=0 \
    TF_NEED_OPENCL=0 \
    TF_CUDA_CLANG=0 \
    TF_DOWNLOAD_CLANG=0 \
    TF_ENABLE_XLA=0 \
    TF_NEED_MPI=0 \
    TF_SET_ANDROID_WORKSPACE=0 \
    TF_NEED_CLANG=0 \
    TF_NEED_ROCM=0 \
"

inherit tensorflow_ver

CC:append:arm = " -mfp16-format=ieee"

CFLAGS += "-Wno-stringop-overflow -Wno-dangling-pointer -Wno-maybe-uninitialized"

export TS_DL_DIR

do_compile[network] = "1"

# Missing write permission prevents the WORKDIR to be deleted, e.g. when recipe becames unreachable
do_compile:append() {
    chmod a+w ${BAZEL_DIR}/output_base/execroot/org_tensorflow/bazel-out/*/bin/tensorflow/lite/python/conversion_metadata_schema_py_srcs_no_include_all || true
    chmod a+w ${BAZEL_DIR}/output_base/execroot/org_tensorflow/bazel-out/*/bin/tensorflow/lite/python/schema_py_srcs_no_include_all || true
    chmod a+w ${BAZEL_DIR}/output_base/execroot/org_tensorflow/bazel-out/*/bin/tensorflow/tools/pip_package/wheel_house || true
}
