//
//  main.cpp
//  S1MidiLibraryChecker
//
//  Created by Shuichi on 13/02/02.
//  Copyright (c) 2013年 Miura Acoustic. All rights reserved.
//

#include <iostream>
#include "MidiDeviceUtil.h"

const char* GetCstr(CFStringRef value)
{
    CFIndex length = CFStringGetLength(value);
    
    CFIndex maxSize
    = CFStringGetMaximumSizeForEncoding(length,
                                        kCFStringEncodingUTF8);
    
    char *buffer = (char *)malloc(maxSize);
    
    if (CFStringGetCString(value, buffer, maxSize,
                           kCFStringEncodingUTF8)) {
        return buffer;
    }
    
    return NULL;
}

int main(int argc, const char * argv[])
{
    printf("Start");
    
    MidiDeviceUtil *util = new MidiDeviceUtil();
    unsigned long deviceCount = util->GetDeviceCount();
    
    for (int i = 0; i < deviceCount; i++)
    {
        CFStringRef deviceName = util->GetMidiDeviceName(i);
        const char* deviceNameC = GetCstr(deviceName);
        
        printf("DeviceName:%s", deviceNameC);
        
        unsigned long entityCount = util->GetMidiEntityCount(i);
        
        for (int j = 0; j < entityCount; j++)
        {
            unsigned long sourceCount = util->GetMidiSourcesCount(i, j);
            
            for (int k = 0; k < sourceCount; k++)
            {
                CFStringRef endpointName = util->GetMidiEndpointName(i, j, k);
                const char* endpointNameC = GetCstr(endpointName);
                
                printf("EndpointName:%s", endpointNameC);
            }
        }
    }

    return 0;
}

