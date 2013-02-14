//
//  info_s1products_midi_MidiDeviceUtil1.cpp
//  S1MidiLibraryOSX
//
//  Created by Shuichi on 13/02/01.
//  Copyright (c) 2013年 Miura Acoustic. All rights reserved.
//

#include "info_s1products_midi_MidiDeviceUtil.h"
#include "MidiDeviceUtil.h"

static MidiDeviceUtil* midiDeviceUtil = 0;

const char* GetCstr(CFStringRef value)
{
    CFIndex length = CFStringGetLength(value);
    
    CFIndex maxSize
    = CFStringGetMaximumSizeForEncoding(length,
                                        kCFStringEncodingUTF8);
    
    char *buffer = (char *)malloc(maxSize);
    
    if (CFStringGetCString(value, buffer, maxSize,
                           kCFStringEncodingUTF8)) {
        return buffer;
    }
    
    return NULL;
}

JNIEXPORT void JNICALL Java_info_s1products_midi_MidiDeviceUtil_jni_1initialize
(JNIEnv *env, jobject obj)
{
    midiDeviceUtil = new MidiDeviceUtil();
}

/*
 * Class:     info_s1products_midi_MidiDeviceUtil
 * Method:    jni_finalize
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_info_s1products_midi_MidiDeviceUtil_jni_1finalize
(JNIEnv *env, jobject obj)
{
    delete midiDeviceUtil;
}

/*
 * Class:     info_s1products_midi_MidiDeviceUtil
 * Method:    jni_getOutDeviceCount
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_info_s1products_midi_MidiDeviceUtil_jni_1getInDeviceCount
(JNIEnv *env, jobject obj)
{
    return (jint)midiDeviceUtil->GetInDeviceCount();
}

/*
 * Class:     info_s1products_midi_MidiDeviceUtil
 * Method:    jni_getOutDeviceName
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_info_s1products_midi_MidiDeviceUtil_jni_1getInDeviceName
(JNIEnv *env, jobject obj, jint index)
{
    CFStringRef deviceName = midiDeviceUtil->GetInDeviceName(index);
    
    const char* deviceNameC = GetCstr(deviceName);

    return env->NewStringUTF(deviceNameC);
}