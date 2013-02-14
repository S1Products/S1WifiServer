//
//  MidiHandler.cpp
//  S1MidiLibraryOSX
//
//  Created by Shuichi on 13/02/01.
//  Copyright (c) 2013年 Miura Acoustic. All rights reserved.
//

#include "MidiClientHandler.h"

MIDIClientRef MidiClientHandler::GetClient()
{
    if (midi_client == 0)
    {
        OSStatus status
            = MIDIClientCreate(CFSTR("S1MIDIBridge"), NULL, NULL, &midi_client);
        
        if(status != 0)
        {
            printf("Failed to create MIDI client.\n");
            return false;
        }
    }

    return midi_client;
}

void MidiClientHandler::CloseClient()
{
    MIDIClientDispose(midi_client);
    midi_client = 0;
}
