SUMMARY = "The oauthlib integration for Google Auth"
HOMEPAGE = "https://github.com/googleapis/google-auth-library-python-oauthlib"
DESCRIPTION = "This library provides oauthlib integration with google-auth."
SECTION = "devel/python"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

inherit pypi

SRC_URI[md5sum] = "0ef7695adb47224714926a7e8331e61d"
SRC_URI[sha256sum] = "4ab58e6c3dc6ccf112f921fcced40e5426fba266768986ea502228488276eaba"

BBCLASSEXTEND = "native"

RDEPENDS:${PN} += " \
    python3-requests-oauthlib \
    python3-oauthlib \
"
inherit setuptools3
