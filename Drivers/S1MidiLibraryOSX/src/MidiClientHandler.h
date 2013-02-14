//
//  MidiHandler.h
//  S1MidiLibraryOSX
//
//  Created by Shuichi on 13/02/01.
//  Copyright (c) 2013年 Miura Acoustic. All rights reserved.
//

#ifndef __S1MidiLibraryOSX__MidiClientHandler__
#define __S1MidiLibraryOSX__MidiClientHandler__

#include <iostream>
#include <CoreFoundation/CoreFoundation.h>
#include <CoreMIDI/CoreMIDI.h>

class MidiClientHandler
{
private:

    MIDIClientRef midi_client;

    MidiClientHandler(){}
    MidiClientHandler(const MidiClientHandler& rhs);
    MidiClientHandler& operator=(const MidiClientHandler& rhs);
    
public:
    
    static MidiClientHandler* GetInstance()
    {
    	static MidiClientHandler instance;
    	return &instance;
    }
    
    MIDIClientRef GetClient();
    void CloseClient();
};

#endif /* defined(__S1MidiLibraryOSX__MidiHandler__) */
