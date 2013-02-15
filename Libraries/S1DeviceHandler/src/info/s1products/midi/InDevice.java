package info.s1products.midi;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

public class InDevice implements MidiDevice {

	public static final String DEVICE_NAME = "S1 Direct In";
	public static final String VENDOR_NAME = "S1Products.info";
	public static final String DESCRIPTION = "In device for S1 WIFI Server";
	public static final String VERSION     = "1.0.0";

	private boolean isOpen;
	private MidiInDeviceInfo deviceInfo;

	private List<Transmitter> transmitterList = new ArrayList<Transmitter>();
	
	public InDevice() {
		
		deviceInfo = new MidiInDeviceInfo(DEVICE_NAME, 
									   	  VENDOR_NAME, 
										  DESCRIPTION, 
										  VERSION);
		
		transmitterList.add(DriverFactory.createTransmitter());
	}
	
	@Override
	public Info getDeviceInfo() {
		return deviceInfo;
	}

	@Override
	public void open() throws MidiUnavailableException {
		
		NativeTransmitter transmitter 
			= (NativeTransmitter)transmitterList.get(0);

		transmitter.open();
		
		isOpen = true;
	}

	@Override
	public void close() {

		NativeTransmitter transmitter 
			= (NativeTransmitter)transmitterList.get(0);

		transmitter.close();
		
		isOpen = false;
	}

	@Override
	public boolean isOpen() {
		return isOpen;
	}

	@Override
	public long getMicrosecondPosition() {
		return 0;
	}

	@Override
	public int getMaxReceivers() {
		return 0;
	}

	@Override
	public int getMaxTransmitters() {
		return 1;
	}

	@Override
	public Receiver getReceiver() throws MidiUnavailableException {
		return null;
	}

	@Override
	public List<Receiver> getReceivers() {
		return null;
	}

	@Override
	public Transmitter getTransmitter() throws MidiUnavailableException {
		return transmitterList.get(0);
	}

	@Override
	public List<Transmitter> getTransmitters() {
		return transmitterList;
	}
	
	public class MidiInDeviceInfo extends Info{

		protected MidiInDeviceInfo(String name, String vendor, 
				String description, String version) {
			
			super(name, vendor, description, version);
		}
	}
}
