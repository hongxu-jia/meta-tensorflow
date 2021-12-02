DESCRIPTION = "TensorFlow C/C++ Libraries"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c7e17cca1ef4230861fb7868e96c387e"

DEPENDS = "bazel-native protobuf-native util-linux-native protobuf"
SRCREV = "3aa40c3ce9d16eae296f086bc4ac4d62deb2affc"
SRC_URI = "git://github.com/tensorflow/tensorflow.git;branch=r2.6;protocol=https \
           file://0001-support-musl.patch \
           file://0001-use-local-bazel-to-workaround-bazel-paralle-issue.patch \
           file://0001-third_party-eigen_archive-workaround-ice-failure-whi.patch \
           file://0001-drop-bazel_toolchains_repositories-to-support-offlin.patch \
           file://0001-drop-v2-config.patch \
           file://0002-Replace-distutils.sysconfig.get_python_lib-with-sysc.patch \
           file://0001-fix-building-from-source-failed-on-Python-3.10-51776.patch \
          "

S = "${WORKDIR}/git"

DEPENDS += " \
    python3 \
    python3-numpy-native \
    python3-keras-applications-native \
    python3-keras-preprocessing-native \
    python3-pip-native \
    python3-wheel-native \
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
"

inherit tensorflow_ver

do_compile:append() {
    chmod a+w ${BAZEL_DIR}/output_base/execroot/org_tensorflow/bazel-out/*/bin/tensorflow/lite/python/schema_py_srcs_no_include_all
    chmod a+w ${BAZEL_DIR}/output_base/execroot/org_tensorflow/bazel-out/*/bin/tensorflow/lite/python/schema_py_srcs_no_include_all/tflite
}
