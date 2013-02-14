//
//  MidiDeviceUtil.h
//  S1MidiLibraryOSX
//
//  Created by Shuichi on 13/02/01.
//  Copyright (c) 2013年 Miura Acoustic. All rights reserved.
//

#ifndef __S1MidiLibraryOSX__MidiDeviceUtil__
#define __S1MidiLibraryOSX__MidiDeviceUtil__

#include <iostream>
#include <CoreFoundation/CoreFoundation.h>
#include <CoreMIDI/CoreMIDI.h>

class MidiDeviceUtil
{
private:
    
public:

    unsigned long GetDeviceCount();

    MIDIDeviceRef GetMidiDevice(int index);

    CFStringRef GetMidiDeviceName(int index);
    
    unsigned long GetMidiEntityCount(int index);
    
    MIDIEntityRef GetMidiEntity(int deviceIndex, int entityIndex);
    
    unsigned long GetMidiSourcesCount(int deviceIndex, int entityIndex);
    
    MIDIEndpointRef GetMidiEndpoint(int deviceIndex, int entityIndex, int sourceIndex);
    
    CFStringRef GetMidiEndpointName(int deviceIndex, int entityIndex, int sourceIndex);
    
    SInt32 IsEndpointOffline(int deviceIndex, int entityIndex, int sourceIndex);

    unsigned long GetOutDeviceCount();
    
    CFStringRef GetOutDeviceName(int index);

    CFStringRef GetOutDeviceManufacturer(int index);

    unsigned long GetInDeviceCount();
    
    CFStringRef GetInDeviceName(int index);

    CFStringRef GetInDeviceManufacturer(int index);
};

#endif /* defined(__S1MidiLibraryOSX__MidiDeviceUtil__) */
