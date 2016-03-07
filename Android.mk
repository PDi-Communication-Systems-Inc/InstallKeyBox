LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_SRC_FILES := $(call all-java-files-under, app/src/main)

LOCAL_STATIC_JAVA_LIBRARIES := android-support-v7-appcompat

#LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := android-support-v4:libs/android-support-v4.jar \
#                                        android-support-v7-appcompat:libs/android-support-v7-appcompat.jar

LOCAL_AAPT_FLAGS := \
        --auto-add-overlay \
        --extra-packages android-support-v7-appcompat

LOCAL_MANIFEST_FILE := app/src/main/AndroidManifest.xml
LOCAL_RESOURCE_DIR := $(LOCAL_PATH)/app/src/main/res/ \
                      $(TOPDIR)frameworks/support/v7/appcompat/res

LOCAL_PACKAGE_NAME := installkeybox
LOCAL_MODULE_TAGS := optional
LOCAL_CERTIFICATE := platform

include $(BUILD_PACKAGE)

#LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := android-support-v7-appcompat:libs/android-support-v7-appcompat.jar

#include $(BUILD_MULTI_PREBUILT)
