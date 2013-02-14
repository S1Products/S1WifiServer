//
//  MidiDeviceUtil.cpp
//  S1MidiLibraryOSX
//
//  Created by Shuichi on 13/02/01.
//  Copyright (c) 2013年 Miura Acoustic. All rights reserved.
//

#include "MidiDeviceUtil.h"

unsigned long MidiDeviceUtil::GetDeviceCount()
{
    return MIDIGetNumberOfDevices();
}

MIDIDeviceRef MidiDeviceUtil::GetMidiDevice(int index)
{
    return MIDIGetDevice(index);
}

CFStringRef MidiDeviceUtil::GetMidiDeviceName(int index)
{
    MIDIDeviceRef device = GetMidiDevice(index);

    CFStringRef deviceName = NULL;
    
    OSStatus err
        = MIDIObjectGetStringProperty(device,
                                      kMIDIPropertyName,
                                      &deviceName);
    //TODO:Error
    return deviceName;
}

unsigned long MidiDeviceUtil::GetMidiEntityCount(int index)
{
    MIDIDeviceRef device = GetMidiDevice(index);
    return MIDIDeviceGetNumberOfEntities(device);
}

MIDIEntityRef MidiDeviceUtil::GetMidiEntity(int deviceIndex, int entityIndex)
{
    MIDIDeviceRef device = GetMidiDevice(deviceIndex);
    return MIDIDeviceGetEntity(device, entityIndex);
}

unsigned long MidiDeviceUtil::GetMidiSourcesCount(int deviceIndex, int entityIndex)
{
    MIDIEntityRef entity = GetMidiEntity(deviceIndex, entityIndex);
    return MIDIEntityGetNumberOfSources(entity);
}

MIDIEndpointRef MidiDeviceUtil::GetMidiEndpoint(int deviceIndex, int entityIndex, int sourceIndex)
{
    MIDIEntityRef entity = GetMidiEntity(deviceIndex, entityIndex);
    return MIDIEntityGetSource(entity, sourceIndex);
}

CFStringRef MidiDeviceUtil::GetMidiEndpointName(int deviceIndex, int entityIndex, int sourceIndex)
{
    MIDIEndpointRef endpoint = GetMidiEndpoint(deviceIndex, entityIndex, sourceIndex);
    
    CFStringRef endpointName = NULL;
    
    OSStatus err
        = MIDIObjectGetStringProperty(endpoint, kMIDIPropertyName, &endpointName);
    
    if (err == 0)
    {
        //TODO:Error
    }
    
    return endpointName;
}

SInt32 MidiDeviceUtil::IsEndpointOffline(int deviceIndex, int entityIndex, int sourceIndex)
{
    MIDIEndpointRef endpoint = GetMidiEndpoint(deviceIndex, entityIndex, sourceIndex);
    
    SInt32 isOffline;
    
    OSStatus err
        = MIDIObjectGetIntegerProperty(endpoint, kMIDIPropertyOffline, &isOffline);
    
    if (err == 0)
    {
        //TODO:Error
    }
    
    return isOffline;
}

unsigned long MidiDeviceUtil::GetOutDeviceCount()
{
    return MIDIGetNumberOfDestinations();
}

CFStringRef MidiDeviceUtil::GetOutDeviceName(int index)
{
    MIDIEndpointRef endpoint = MIDIGetDestination(index);

    CFStringRef deviceName = NULL;
    
    MIDIObjectGetStringProperty(endpoint,
                                kMIDIPropertyName,
                                &deviceName);

    return deviceName;
}

CFStringRef MidiDeviceUtil::GetOutDeviceManufacturer(int index)
{
    MIDIEndpointRef endpoint = MIDIGetDestination(index);
    
    CFStringRef manufacturer = NULL;
    
    MIDIObjectGetStringProperty(endpoint,
                                kMIDIPropertyManufacturer,
                                &manufacturer);
    
    return manufacturer;
}

unsigned long MidiDeviceUtil::GetInDeviceCount()
{
    return MIDIGetNumberOfSources();
}

CFStringRef MidiDeviceUtil::GetInDeviceName(int index)
{
    MIDIEndpointRef endpoint = MIDIGetSource(index);
    
    CFStringRef deviceName = NULL;

    MIDIObjectGetStringProperty(endpoint,
                                kMIDIPropertyName,
                                &deviceName);

    return deviceName;
}

CFStringRef MidiDeviceUtil::GetInDeviceManufacturer(int index)
{
    MIDIEndpointRef endpoint = MIDIGetSource(index);
    
    CFStringRef manufacturer = NULL;
    
    MIDIObjectGetStringProperty(endpoint,
                                kMIDIPropertyManufacturer,
                                &manufacturer);
    
    return manufacturer;
}
