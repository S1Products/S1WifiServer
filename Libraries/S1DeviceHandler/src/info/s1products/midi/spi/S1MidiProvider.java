package info.s1products.midi.spi;

import info.s1products.midi.InDevice;
import info.s1products.midi.OutDevice;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.spi.MidiDeviceProvider;

public class S1MidiProvider extends MidiDeviceProvider {

	private OutDevice outDevice = new OutDevice();
	private InDevice  inDevice  = new InDevice();
	
	@Override
	public Info[] getDeviceInfo() {
		
		Info[] s1Info = new Info[2];

		s1Info[0] = outDevice.getDeviceInfo();
		s1Info[1] = inDevice.getDeviceInfo();
		
		return s1Info;
	}

	@Override
	public MidiDevice getDevice(Info info) {

		Info outInfo = outDevice.getDeviceInfo();
		
		if(outInfo.equals(info)){
			
			return outDevice;
		}
		
		Info inInfo = inDevice.getDeviceInfo();
		
		if(inInfo.equals(info)){
			
			return inDevice;
		}
		
		return null;
	}
}
