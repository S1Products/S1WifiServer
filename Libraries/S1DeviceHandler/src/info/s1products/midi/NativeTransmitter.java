package info.s1products.midi;

import java.util.logging.Logger;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Transmitter;

public class NativeTransmitter implements Transmitter {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	public static final int SYSEX_PREFIX = 0xF0;

	private Receiver receiver;

	native private void jni_initialize();
	native private void jni_finalize();
	native private void jni_setTransmitter(NativeTransmitter transmitter);
	native private void jni_open();
	native private void jni_close();

	public NativeTransmitter(String libraryName){
		
		logger.entering(this.getClass().toString(), "Constructor", new Object[]{libraryName});

		System.loadLibrary(libraryName);
		
		logger.exiting(this.getClass().toString(), "Constructor");
	}
	
	public void open(){

		logger.entering(this.getClass().toString(), "open");
		
		jni_initialize();
		jni_setTransmitter(this);
		jni_open();
		
		logger.exiting(this.getClass().toString(), "open");
	}
	
	@Override
	public void setReceiver(Receiver receiver) {

		logger.entering(this.getClass().toString(), "setReceiver");
		
		this.receiver = receiver;
		
		logger.exiting(this.getClass().toString(), "setReceiver");
	}

	@Override
	public Receiver getReceiver() {
		
		logger.entering(this.getClass().toString(), "getReceiver");
		
		return receiver;
	}

	private MidiMessage createMidiMessage(byte[] data){
		
		logger.entering(this.getClass().toString(), "createMidiMessage", new Object[]{ data });
		
		try{
			MidiMessage message = null;

			if(data[0] == SYSEX_PREFIX){
				
				SysexMessage sysEx = new SysexMessage();
				sysEx.setMessage(data, data.length);
				message = sysEx;
				
			}else{
				
				ShortMessage shortMessage = new ShortMessage();
				shortMessage.setMessage(data[0], data[1], data[2]);
				message = shortMessage;
			}

			logger.exiting(this.getClass().toString(), "createMidiMessage");

			return message;

		}catch(InvalidMidiDataException midiEx){
			
			logger.throwing(this.getClass().toString(), "createMidiMessage", midiEx);
			return null;
		}
	}
	
	public void messageReceived(byte[] data){

		logger.entering(this.getClass().toString(), "messageReceived", new Object[]{ data });

		if(data == null || data.length == 0){

			logger.info("Invalid message received.");
			return;
		}

		MidiMessage message = createMidiMessage(data);
		if(message == null){
			return ;
		}

		receiver.send(message, System.currentTimeMillis());
		
		logger.exiting(this.getClass().toString(), "messageReceived");
	}
	
	@Override
	public void close() {

		logger.entering(this.getClass().toString(), "close");

		jni_close();
		jni_finalize();
		
		logger.exiting(this.getClass().toString(), "close");
	}
}
