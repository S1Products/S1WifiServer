//
//  MidiReceiver.h
//  S1MidiLibraryOSX
//
//  Created by Shuichi on 13/02/01.
//  Copyright (c) 2013年 Miura Acoustic. All rights reserved.
//

#ifndef __S1MidiLibraryOSX__MidiReceiver__
#define __S1MidiLibraryOSX__MidiReceiver__

#include <iostream>
#include <jni.h>
#include <CoreFoundation/CoreFoundation.h>
#include <CoreMIDI/CoreMIDI.h>
#include "MidiClientHandler.h"

class MidiReceiver
{
private:
    MIDIPortRef midiInPort;
    MIDIEndpointRef midiSource;

    MIDIPortRef createInputPort(MIDIClientRef client);
    MIDIEndpointRef createSource(MIDIPortRef inPort, int deviceIndex);
    
public:
    void start(int deviceIndex);
    void stop();
    void setVM(JavaVM *vm);
    void setListener(jobject listener);
    void sendMessage(unsigned char* message, int length);
};


#endif /* defined(__S1MidiLibraryOSX__MidiReceiver__) */
