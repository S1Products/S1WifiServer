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
	if (ret == JNI_OK) {
		return env;
	}
    
	ret = jvm->AttachCurrentThread((void**)&env, NULL);
	if (ret == JNI_OK) {
		*attach = 1; //自分でアタッチしたという印
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

MIDIPortRef MidiReceiver::createInputPort(MIDIClientRef client)
{
    OSStatus err;
    MIDIPortRef inPort;
    
    //Create MIDI In port
    err = MIDIInputPortCreate(client, CFSTR("S1Bridge_In"),
                              MIDIInputProc, this, &inPort);
    
    if (err != noErr)
    {
        printf("MIDI create in port err.");
        return nil;
    }
    
    return inPort;
}

MIDIEndpointRef MidiReceiver::createSource(MIDIPortRef inPort, int deviceIndex)
{
    OSStatus err;
    
    MIDIEndpointRef source = MIDIGetSource(deviceIndex);
    err = MIDIPortConnectSource(inPort, source, NULL);
    
    return source;
}

void MidiReceiver::start(int deviceIndex)
{
    MidiClientHandler *handler = MidiClientHandler::GetInstance();
    MIDIClientRef client = handler->GetClient();

    midiInPort = createInputPort(client);
    midiSource = createSource(midiInPort, deviceIndex);
    
    printf("CoreMidi receiver started.");
}

void MidiReceiver::stop()
{
    MIDIPortDispose(midiInPort);
    MIDIEndpointDispose(midiSource);
    
    printf("CoreMidi receiver stopped.");
}
