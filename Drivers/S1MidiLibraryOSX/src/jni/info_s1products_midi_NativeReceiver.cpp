//
//  info_s1products_MidiSender.cpp
//  S1MidiLibraryOSX
//
//  Created by Shuichi on 13/01/30.
//  Copyright (c) 2013年 Miura Acoustic. All rights reserved.
//

#include "info_s1products_midi_NativeReceiver.h"
#include "MidiSender.h"

static MidiSender* midiSender = 0;

JNIEXPORT void JNICALL Java_info_s1products_midi_NativeReceiver_jni_1initialize
(JNIEnv *, jobject)
{
    midiSender = new MidiSender();
}

JNIEXPORT void JNICALL Java_info_s1products_midi_NativeReceiver_jni_1finalize
(JNIEnv *, jobject)
{
    delete midiSender;
}

JNIEXPORT void JNICALL Java_info_s1products_midi_NativeReceiver_jni_1open
(JNIEnv *, jobject)
{
    midiSender->open();
}

JNIEXPORT void JNICALL Java_info_s1products_midi_NativeReceiver_jni_1close
(JNIEnv *, jobject)
{
    midiSender->close();
}

JNIEXPORT void JNICALL Java_info_s1products_midi_NativeReceiver_jni_1sendMessage
(JNIEnv *env, jobject obj, jbyteArray midiData)
{
    jboolean isCopy;
    
    jbyte* midiDataArray = env->GetByteArrayElements(midiData, &isCopy);
    
    int size = env->GetArrayLength(midiData);
    
    unsigned char message[size];
    
    for (int i = 0; i < size; i++)
    {
        message[i] = midiDataArray[i];
    }
    
    midiSender->sendMessage(message, size);
}

