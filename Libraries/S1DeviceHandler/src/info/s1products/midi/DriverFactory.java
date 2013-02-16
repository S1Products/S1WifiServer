package info.s1products.midi;

import info.s1products.util.OSDetector;
import info.s1products.util.OSDetector.OSType;

import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

public class DriverFactory {

	public static Receiver createReceiver(){
		
		OSType type = OSDetector.getExecutingOSType();
		
		String libraryName = "";
		
		switch(type){
			case MacOSX:
				libraryName = "S1MidiLibraryOSX";
				break;
				
			case Windows:
				break;
				
			default:
				break;
		}
		
		return new NativeReceiver(libraryName);
	}

	public static Transmitter createTransmitter(){
		
		OSType type = OSDetector.getExecutingOSType();
		
		String libraryName = "";

		switch(type){
		
			case MacOSX:
				libraryName = "S1MidiLibraryOSX";
				break;
				
			case Windows:
				break;
				
			default:
				break;
		}
		
		return new NativeTransmitter(libraryName);
	}
}
