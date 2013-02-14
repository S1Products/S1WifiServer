//
//  MidiSender.h
//  S1MidiLibraryOSX
//
//  Created by Shuichi Miura on 2013/01/29.
//  Copyright (c) 2013年 Miura Acoustic. All rights reserved.
//

#ifndef __S1MidiLibraryOSX__MidiSender__
#define __S1MidiLibraryOSX__MidiSender__

#include <iostream>
#include <CoreFoundation/CoreFoundation.h>
#include <CoreMIDI/CoreMIDI.h>
#include "MidiClientHandler.h"

class MidiSender
{
private:
    MIDIClientRef midi_client;
    MIDIEndpointRef midi_source;
    
public:
    bool open();
    void close();
    void sendMessage(unsigned char* message, int length);
};

#endif /* defined(__S1MidiLibraryOSX__MidiSender__) */
