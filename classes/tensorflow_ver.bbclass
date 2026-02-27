# Build tensorflow 2.x by default
TF_ARGS_EXTRA ??= "--config=v2"

# Uncomment the following line to build tensorflow 1.x
#TF_ARGS_EXTRA = "--config=v1"

python __anonymous() {
    if not bb.utils.contains("DISTRO_FEATURES", "tensorflow", True, False, d):
        msg = "\nThe official TensorFlow is tested and supported under Python 3.13\n"
        msg += "Please add the following to local.conf\n"
        msg += "    DISTRO_FEATURES:append = ' tensorflow'\n"
        msg += "    DISTRO_FEATURES_NATIVE:append = ' tensorflow'\n"
        msg += "    DISTRO_FEATURES_NATIVESDK:append = ' tensorflow'\n"
        raise bb.parse.SkipPackage(msg)

    if d.getVar("PYTHON_BASEVERSION") != "3.13":
        msg = "\nThe official TensorFlow is tested and supported under Python 3.13\n"
        msg += "Please add the following to local.conf\n"
        msg += "    PYTHON_BASEVERSION:class-target = '3.13'\n"
        msg += "    PYTHON_BASEVERSION:class-native = '3.13'\n"
        msg += "    PYTHON_BASEVERSION:class-nativesdk = '3.13'\n"
        msg += "It will apply python3 modules for 3.13"
        raise bb.parse.SkipPackage(msg)
}

