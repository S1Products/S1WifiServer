package info.s1products.midi.spi;

import java.util.logging.Logger;

import info.s1products.midi.InDevice;
import info.s1products.midi.OutDevice;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.midi.spi.MidiDeviceProvider;

public class S1MidiProvider extends MidiDeviceProvider {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	private static OutDevice outDevice = new OutDevice();
	private static InDevice  inDevice  = new InDevice();
	
	@Override
	public Info[] getDeviceInfo() {
		
		logger.entering(this.getClass().getName(), "getDeviceInfo");
		
		Info[] s1Info = new Info[2];

		s1Info[0] = outDevice.getDeviceInfo();
		s1Info[1] = inDevice.getDeviceInfo();

		logger.exiting(this.getClass().getName(), "getDeviceInfo");

		return s1Info;
	}

	@Override
	public MidiDevice getDevice(Info info) {

		logger.entering(this.getClass().getName(), "getDevice", new Object[]{ info });

		Info outInfo = outDevice.getDeviceInfo();
		
		MidiDevice device = null;
		
		if(outInfo.equals(info)){
			
			device = outDevice;
		}
		
		Info inInfo = inDevice.getDeviceInfo();
		
		if(inInfo.equals(info)){
			
			device = inDevice;
		}
		
		logger.exiting(this.getClass().getName(), "getDevice");

		return device;
	}
}
