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
static jmethodID receivedMethod;
static bool receiverIsActive;

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

static void callListener(Byte *data, int length)
{
    int attach;
    JNIEnv *env = getJNIEnv(&attach);

    if (receivedMethod == NULL)
    {
        jclass jMidiListener = env->GetObjectClass(midiListener);

        receivedMethod
            = env->GetMethodID(jMidiListener,
                               "messageReceived",
                               "([B)V");
    }
    
    jbyteArray retByteArray = env->NewByteArray(length);

    env->SetByteArrayRegion(retByteArray, 0, length, (jbyte*)data);
    
    env->CallVoidMethod(midiListener, receivedMethod, retByteArray, NULL);
}
	
static void MIDIInputProc(const MIDIPacketList *pktlist,
                          void *readProcRefCon,
                          void *srcConnRefCon)
{
    int pktCount = pktlist->numPackets;
    
    for (int i = 0; i < pktCount; i++)
    {
        MIDIPacket *packet = (MIDIPacket *)&pktlist->packet[i];
        callListener(packet->data, packet->length);
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
    if (receiverIsActive)
    {
        return;
    }
    
    MidiClientHandler *handler = MidiClientHandler::GetInstance();
    MIDIClientRef client = handler->GetClient();

    //Create MIDI In destination
    OSStatus err
        = MIDIDestinationCreate(client, CFSTR("S1 Direct In"), MIDIInputProc, this, &midiDest);
    
    if (err != noErr)
    {
        printf("MIDI create in port err.");
        return;
    }
    
    receiverIsActive = true;

    printf("CoreMidi receiver started.");
}

void MidiReceiver::stop()
{
    MIDIEndpointDispose(midiDest);
    
    jvm->DetachCurrentThread();
    
    jvm = NULL;
    midiListener  = NULL;
    
    receiverIsActive = false;
    
    printf("CoreMidi receiver stopped.");
}
