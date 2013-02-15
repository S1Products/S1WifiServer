//
//  MidiReceiver.cpp
//  S1MidiLibraryOSX
//
//  Created by Shuichi on 13/02/01.
//  Copyright (c) 2013年 Miura Acoustic. All rights reserved.
//

#include "MidiReceiver.h"

static JavaVM *jvm;
static jobject midiListener;
static jclass  jMidiListener;
static jmethodID jMidiListenerReceiveMethod;

JNIEnv* getJNIEnv(int *attach)
{
	*attach = 0;
    
	JNIEnv *env;
	jint ret = jvm->GetEnv((void**)&env, JNI_VERSION_1_6);
	
    if (ret == JNI_OK)
    {
		return env;
	}
    
	ret = jvm->AttachCurrentThread((void**)&env, NULL);

	if (ret == JNI_OK)
    {
		*attach = 1;
		return env;
	}
    
	return NULL;
}

static void callListener(Byte *data)
{
    int attach;
    JNIEnv *env = getJNIEnv(&attach);
    
    if (jMidiListener == NULL)
    {
        jMidiListener = env->GetObjectClass(midiListener);
    }
    
    if (jMidiListenerReceiveMethod == NULL)
    {
        jMidiListenerReceiveMethod
            = env->GetMethodID(jMidiListener,
                               "messageReceived",
                               "([B)V");
    }
    
    jbyteArray retByteArray = env->NewByteArray(sizeof(data));

    jboolean isCopy;
    jbyte *param = env->GetByteArrayElements(retByteArray, &isCopy);

    env->CallVoidMethod(midiListener,
                        jMidiListenerReceiveMethod,
                        param);
    
    env->ReleaseByteArrayElements(retByteArray, param, 0);
}
	
static void MIDIInputProc(const MIDIPacketList *pktlist,
                          void *readProcRefCon,
                          void *srcConnRefCon)
{
    int pktCount = pktlist->numPackets;
    
    for (int i = 0; i < pktCount; i++)
    {
        MIDIPacket *packet = (MIDIPacket *)&pktlist->packet[i];
        callListener(packet->data);
    }
}

void MidiReceiver::setVM(JavaVM *vm){
    jvm = vm;
}

void MidiReceiver::setListener(jobject listener)
{
    midiListener = listener;
}

void MidiReceiver::start()
{
    MidiClientHandler *handler = MidiClientHandler::GetInstance();
    MIDIClientRef client = handler->GetClient();

    //Create MIDI In destination
    OSStatus err
        = MIDIDestinationCreate(client, CFSTR("S1WifiServer_In"), MIDIInputProc, this, &midiDest);
    
    if (err != noErr)
    {
        printf("MIDI create in port err.");
        return;
    }
    
    printf("CoreMidi receiver started.");
}

void MidiReceiver::stop()
{
    MIDIEndpointDispose(midiDest);
    
    printf("CoreMidi receiver stopped.");
}
