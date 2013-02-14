//
//  MidiSender.cpp
//  S1MidiLibraryOSX
//
//  Created by Shuichi Miura on 2013/01/29.
//  Copyright (c) 2013年 Miura Acoustic. All rights reserved.
//

#include "MidiSender.h"

bool MidiSender::open()
{
    MidiClientHandler *handler = MidiClientHandler::GetInstance();
    MIDIClientRef client = handler->GetClient();
    
    OSStatus status = MIDISourceCreate(client, CFSTR("S1Bridge_Out"), &midi_source);

	if(status != 0)
	{
		printf("Failed to create MIDI source.\n");
		return false;
	}
	
    return true;
}

void MidiSender::close()
{
    MIDIEndpointDispose(midi_source);
}

void MidiSender::sendMessage(unsigned char *message, int length)
{
    Byte buffer[64];
	MIDIPacketList *pktlist = (MIDIPacketList *)buffer;
	MIDIPacket *curPacket = MIDIPacketListInit(pktlist);
	
	curPacket = MIDIPacketListAdd(pktlist, sizeof(buffer), curPacket, 0, length, message);
	
	OSStatus status;
	status = MIDIReceived(midi_source, pktlist);
	
	if(status != 0)
	{
		printf("Send MIDI error.\n");
	}
}

