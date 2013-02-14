//
//  MidiDeviceUtilTest.m
//  S1MidiLibraryOSX
//
//  Created by Shuichi on 13/02/02.
//  Copyright (c) 2013年 Miura Acoustic. All rights reserved.
//

#import "MidiDeviceUtilTest.h"

@implementation MidiDeviceUtilTest

-(void)testMidiDeviceUtilTest
{
    MidiDeviceUtil *util = new MidiDeviceUtil();
    unsigned long deviceCount = util->GetDeviceCount();
    
    for (int i = 0; i < deviceCount; i++)
    {
        CFStringRef deviceName = util->GetMidiDeviceName(i);
        
        const char* deviceNameC
        = CFStringGetCStringPtr(deviceName, kCFStringEncodingASCII);
        
        printf("DeviceName:%s", deviceNameC);
        
        unsigned long entityCount = util->GetMidiEntityCount(i);
        
        for (int j = 0; j < entityCount; j++)
        {
            unsigned long sourceCount = util->GetMidiSourcesCount(i, j);
            
            for (int k = 0; k < sourceCount; k++)
            {
                CFStringRef endpointName = util->GetMidiEndpointName(i, j, k);
                
                const char* endpointNameC
                = CFStringGetCStringPtr(endpointName, kCFStringEncodingASCII);
                
                printf("EndpointName:%s", endpointNameC);
            }
        }
    }

}

@end
