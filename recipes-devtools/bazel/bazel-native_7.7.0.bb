DESCRIPTION = "Bazel build and test tool"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI[sha256sum] = "277946818c77fff70be442864cecc41faac862b6f2d0d37033e2da0b1fee7e0f"

SRC_URI = "https://github.com/bazelbuild/bazel/releases/download/${PV}/bazel-${PV}-dist.zip \
           file://0001-HttpDownloader-save-download-tarball-to-distdir.patch \
           file://0001-fix-unzip-command-not-found.patch \
           file://0001-add-Yocto-native-sysroot-dir-to-the-default-Bazel-to.patch \
           file://0001-customize-native-toolchains-for-Yocto.patch \
"

UPSTREAM_CHECK_URI = "https://github.com/bazelbuild/bazel/releases/"
UPSTREAM_CHECK_REGEX = "releases/tag/v?(?P<pver>\d+(\.\d+)+)$"

inherit python3native

INHIBIT_SYSROOT_STRIP = "1"

CCACHE_DISABLE = "1"

DEPENDS = "coreutils-native \
           zip-native \
           openjdk-21-native \
          "

S = "${UNPACKDIR}"

inherit bazel-base

EXTRA_BAZEL_ARGS = " \
    --tool_java_runtime_version=local_jdk \
    --python_path=python3 \
    --distdir=${TS_DL_DIR} \
    --color=no \
    ${@oe.utils.conditional("BAZEL_JOBS", "", "", "--jobs=${BAZEL_JOBS}", d )} \
    ${@oe.utils.conditional("BAZEL_JOBS", "", "", "--local_resources=cpu=${BAZEL_JOBS}", d )} \
    ${@oe.utils.conditional("BAZEL_MEM", "", "", "--local_resources=memory=${BAZEL_MEM}", d )} \
"

do_compile[network] = "1"
do_compile () {
    BAZEL_DEV_VERSION_OVERRIDE="7.7.0" \
    TMPDIR="${TMPDIR}/bazel" \
    VERBOSE=yes \
    EXTRA_BAZEL_ARGS="${EXTRA_BAZEL_ARGS}" \
    ./compile.sh
}

do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${S}/output/bazel ${D}${bindir}
}

# Explicitly disable uninative
UNINATIVE_LOADER = ""

inherit native
