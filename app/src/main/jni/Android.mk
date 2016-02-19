LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := custompcap

LOCAL_SRC_FILES :=\
    mypacket.cpp



LOCAL_C_INCLUDES :=\
	$(NDK_ROOT)/platforms/android-8/arch-arm/usr/include\
	$(LOCAL_PATH)/libpcap

LOCAL_CFLAGS := -DLIBPCAP_VERSION=0x097

LOCAL_STATIC_LIBRARIES := libpcap

include $(BUILD_SHARED_LIBRARY)

include $(LOCAL_PATH)/libpcap/Android.mk
