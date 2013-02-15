package info.s1products.midi;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

public class OutDevice implements MidiDevice {

	public static final String DEVICE_NAME = "S1 Direct Out";
	public static final String VENDOR_NAME = "S1Products.info";
	public static final String DESCRIPTION = "Out device for S1 WIFI Server";
	public static final String VERSION     = "1.0.0";

	private MidiOutDeviceInfo deviceInfo;
	private List<Receiver> receiverList = new ArrayList<Receiver>();
	
	private boolean isOpen;
	
	public OutDevice() {
		
		deviceInfo = new MidiOutDeviceInfo(DEVICE_NAME, 
										   VENDOR_NAME, 
										   DESCRIPTION, 
										   VERSION);
		
		receiverList.add(DriverFactory.createReceiver());
	}
	
	@Override
	public Info getDeviceInfo() {
		return deviceInfo;
	}

	@Override
	public void open() throws MidiUnavailableException {
		
		NativeReceiver receiver = (NativeReceiver)receiverList.get(0);
		receiver.open();
		isOpen = true;
	}

	@Override
	public void close() {

		NativeReceiver receiver = (NativeReceiver)receiverList.get(0);
		receiver.close();
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
		return 1;
	}

	@Override
	public int getMaxTransmitters() {
		return 0;
	}

	@Override
	public Receiver getReceiver() throws MidiUnavailableException {
		return receiverList.get(0);
	}

	@Override
	public List<Receiver> getReceivers() {
		return receiverList;
	}

	@Override
	public Transmitter getTransmitter() throws MidiUnavailableException {
		return null;
	}

	@Override
	public List<Transmitter> getTransmitters() {
		return null;
	}
	
	public class MidiOutDeviceInfo extends Info{

		protected MidiOutDeviceInfo(String name, String vendor, 
				String description, String version) {
			
			super(name, vendor, description, version);
		}
	}
}
