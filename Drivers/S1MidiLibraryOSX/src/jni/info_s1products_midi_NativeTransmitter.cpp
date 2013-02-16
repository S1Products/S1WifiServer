//
//  info_s1products_midi_MidiReceiver1.cpp
//  S1MidiLibraryOSX
//
//  Created by Shuichi on 13/02/01.
//  Copyright (c) 2013年 Miura Acoustic. All rights reserved.
//

#include "info_s1products_midi_NativeTransmitter.h"
#include "MidiReceiver.h"

static MidiReceiver* midiReceiver = 0;
static jobject listener;

JNIEXPORT void JNICALL Java_info_s1products_midi_NativeTransmitter_jni_1initialize
(JNIEnv *, jobject)
{
    midiReceiver = new MidiReceiver();
}

JNIEXPORT void JNICALL Java_info_s1products_midi_NativeTransmitter_jni_1finalize
(JNIEnv *env, jobject)
{
    env->DeleteGlobalRef(listener);
    
    delete midiReceiver;
    midiReceiver = NULL;
}

JNIEXPORT void JNICALL Java_info_s1products_midi_NativeTransmitter_jni_1setTransmitter
(JNIEnv *env, jobject obj, jobject jTransmitter)
{
    listener = env->NewGlobalRef(jTransmitter);

    JavaVM *vm;
    env->GetJavaVM(&vm);
    
    midiReceiver->setVM(vm);
    midiReceiver->setListener(listener);
}

JNIEXPORT void JNICALL Java_info_s1products_midi_NativeTransmitter_jni_1open
(JNIEnv *, jobject)
{
    midiReceiver->start();
}

JNIEXPORT void JNICALL Java_info_s1products_midi_NativeTransmitter_jni_1close
(JNIEnv *, jobject)
{
    midiReceiver->stop();
}
