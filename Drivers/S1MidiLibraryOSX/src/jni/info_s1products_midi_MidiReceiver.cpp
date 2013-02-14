//
//  info_s1products_midi_MidiReceiver1.cpp
//  S1MidiLibraryOSX
//
//  Created by Shuichi on 13/02/01.
//  Copyright (c) 2013年 Miura Acoustic. All rights reserved.
//

#include "info_s1products_midi_MidiReceiver.h"
#include "MidiReceiver.h"

static MidiReceiver* midiReceiver = 0;

JNIEXPORT void JNICALL Java_info_s1products_midi_MidiReceiver_jni_1initialize
(JNIEnv *env, jobject obj)
{
    midiReceiver = new MidiReceiver();
}

JNIEXPORT void JNICALL Java_info_s1products_midi_MidiReceiver_jni_1finalize
(JNIEnv *env, jobject obj)
{
    delete midiReceiver;
}

JNIEXPORT void JNICALL Java_info_s1products_midi_MidiReceiver_jni_1setReceiver
(JNIEnv *env, jobject obj, jobject jReceiver)
{
    jobject listener = env->NewGlobalRef(jReceiver);

    JavaVM *vm;
    env->GetJavaVM(&vm);
    
    midiReceiver->setVM(vm);
    midiReceiver->setListener(listener);
}

JNIEXPORT void JNICALL Java_info_s1products_midi_MidiReceiver_jni_1open
(JNIEnv *env, jobject obj, jint deviceIndex)
{
    midiReceiver->start(deviceIndex);
}

JNIEXPORT void JNICALL Java_info_s1products_midi_MidiReceiver_jni_1close
(JNIEnv *env, jobject obj)
{
    midiReceiver->stop();
}
