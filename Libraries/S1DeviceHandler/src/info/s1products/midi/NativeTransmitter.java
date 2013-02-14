package info.s1products.midi;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Transmitter;

public class NativeTransmitter implements Transmitter {

	public static final int SYSEX_PREFIX = 0xF0;

	private Receiver receiver;

	native private void jni_initialize();
	native private void jni_finalize();
	native private void jni_setTransmitter(NativeTransmitter transmitter);
	native private void jni_open(int deviceIndex);
	native private void jni_close();

	public NativeTransmitter(String libraryName){
/*
		System.loadLibrary(libraryName);
		jni_initialize();
		jni_setTransmitter(this);
*/		
	}
	
	public void open(int deviceIndex){
		jni_open(deviceIndex);
	}
	
	@Override
	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	@Override
	public Receiver getReceiver() {
		return receiver;
	}

	private MidiMessage createMidiMessage(byte[] data){
		
		try{
			if(data[0] == SYSEX_PREFIX){
				
				SysexMessage sysEx = new SysexMessage();
				sysEx.setMessage(data, data.length);
				return sysEx;
				
			}else{
				
				ShortMessage message = new ShortMessage();
				message.setMessage(data[0], data[1], data[2]);
				return message;
			}
			
		}catch(InvalidMidiDataException midiEx){
			return null;
		}
	}
	
	public synchronized void messageReceived(byte[] data){

		if(data.length == 0){
			return;
		}
		
		MidiMessage message = createMidiMessage(data);
		if(message == null){
			return ;
		}

		receiver.send(message, System.currentTimeMillis());
	}
	
	@Override
	public void close() {
		jni_close();
	}
}
