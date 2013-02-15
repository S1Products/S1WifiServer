package info.s1products.server.device;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

public class MidiDeviceUtil {

	private static final int UNLIMITED = -1;
	
	public static List<String> getMidiOutDriverList(){
		
		Info[] infoArray = MidiSystem.getMidiDeviceInfo();
		
		List<String> deviceList = new ArrayList<String>();
		
		for (Info info : infoArray) {
			
			try{
				MidiDevice device = MidiSystem.getMidiDevice(info);
				
				int recMax = device.getMaxReceivers();
				int trsMax = device.getMaxTransmitters();
				
				if(trsMax == 0 && (recMax == UNLIMITED || recMax > 0)){
					
					deviceList.add(info.getName());
				}
				
			}catch(MidiUnavailableException ex){
				
				System.out.println("Skip device :" + info.getName());
			}
		}
		
		return deviceList;
	}

	public static List<String> getMidiInDriverList(){
		
		Info[] infoArray = MidiSystem.getMidiDeviceInfo();
		
		List<String> deviceList = new ArrayList<String>();
		
		for (Info info : infoArray) {
			
			try{
				MidiDevice device = MidiSystem.getMidiDevice(info);
				
				int trsMax = device.getMaxTransmitters();
				
				if(trsMax == UNLIMITED || trsMax > 0){
					
					deviceList.add(info.getName());
				}
				
			}catch(MidiUnavailableException ex){
				
				System.out.println("Skip device :" + info.getName());
			}
		}
		
		return deviceList;
	}
	
	public static MidiDevice getMidiOutDevice(String deviceName)
			throws MidiUnavailableException{

		MidiDevice.Info[] infoArray = MidiSystem.getMidiDeviceInfo();
		
		for (MidiDevice.Info info : infoArray) {
			
			MidiDevice device = MidiSystem.getMidiDevice(info);

			int recMax = device.getMaxReceivers();

			if(recMax == 0){
				continue;
			}
			
			if(info.getName().equals(deviceName)){
				
				return MidiSystem.getMidiDevice(info);
			}
		}
		
		return null;
	}

	public static MidiDevice getMidiInDevice(String deviceName)
			throws MidiUnavailableException{

		MidiDevice.Info[] infoArray = MidiSystem.getMidiDeviceInfo();
		
		for (MidiDevice.Info info : infoArray) {
			
			MidiDevice device = MidiSystem.getMidiDevice(info);

			int trsMax = device.getMaxTransmitters();

			if(trsMax == 0){
				continue;
			}
			
			if(info.getName().equals(deviceName)){
				
				return MidiSystem.getMidiDevice(info);
			}
		}
		
		return null;
	}
}
