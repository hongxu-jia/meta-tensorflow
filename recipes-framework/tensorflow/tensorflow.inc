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
SRCREV = "a481b10260dfdf833a1b16007eead49c1d7febf3"
SRC_URI = "git://github.com/tensorflow/tensorflow.git;branch=r2.21;protocol=https \
    file://0001-Add-support-for-Python-3.14-to-TensorFlow-build-conf.patch \
    file://0001-Update-grpcio-to-1.78.0-for-Python-3.14.patch \
    file://0001-Update-requirements_lock_3_14.txt.patch \
    file://0001-Update-whl-for-python3-3.14.patch \
    file://0001-correct-h5py-version-for-python-3.14.patch \
    file://0001-lite-fix-compile-failure-without-XNNPACK-engine.patch \
    file://0001-use-local-bazel-to-workaround-bazel-paralle-issue.patch \
    file://0003-drop-bazel_toolchains_repositories-to-support-offlin.patch \
    file://0005-hardcode-go-SDKS.patch \
    file://0008-make-hermetic-python3-support-offline-build.patch \
    file://0001-do-not-patch-.so-files-for-linux.patch \
    file://0001-third_party-tf_runtime-fix-compile-failure.patch \
    file://0001-fix-pypi_keras-is-not-defined.patch \
    file://0001-set-x83_64-to-arch-of-whl.patch \
    file://0001-fix-compile-failed-with-clang-21.patch \
    file://0001-fix-clang-22.patch \
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
    xxd-native \
"

inherit python3native bazel

TF_CONFIG ?= " \
    TF_NEED_CUDA=0 \
    TF_NEED_OPENCL_SYCL=0 \
    TF_NEED_OPENCL=0 \
    TF_CUDA_CLANG=0 \
    TF_DOWNLOAD_CLANG=0 \
    TF_NEED_MPI=0 \
    TF_SET_ANDROID_WORKSPACE=0 \
    TF_NEED_CLANG=0 \
    TF_NEED_ROCM=0 \
"

inherit tensorflow_ver

CC:append:arm = " -mfp16-format=ieee"

export TS_DL_DIR

export PIP_CACHE_DIR = "${S}/.cache"

do_compile[network] = "1"

REMOVE_NVIDIA_FILE ?= "1"
remove_nvidia_files() {
    if [ "${REMOVE_NVIDIA_FILE}" != "0" ]; then
        # licenses(["restricted"])  # NVIDIA proprietary license
        rm -f ${S}/third_party/nccl/hermetic/cuda_nccl.BUILD.tpl
        # Many files in this direcgory with the restriced license tag from Nvidia
        rm -rf  ${S}/third_party/gpus/cuda/hermetic/
    fi
}
do_unpack[postfuncs] += "remove_nvidia_files"

DEBUG_OPTIMIZATION:remove:aarch64 = "${@oe.utils.conditional('PACKAGE_ARCH','cortexa53',' -Og','',d)}"
DEBUG_OPTIMIZATION:append:aarch64 = "${@oe.utils.conditional('PACKAGE_ARCH','cortexa53',' -O','',d)}"
