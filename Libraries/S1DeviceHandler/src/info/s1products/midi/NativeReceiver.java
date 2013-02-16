package info.s1products.midi;

import java.util.logging.Logger;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

public class NativeReceiver implements Receiver {

	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	native private void jni_initialize();
	native private void jni_finalize();
	native private void jni_open();
	native private void jni_close();
	native private void jni_sendMessage(byte[] data);

	public NativeReceiver(String libraryName){

		logger.entering(this.getClass().toString(), "Constructor", new Object[]{libraryName});
		
		System.loadLibrary(libraryName);
		
		logger.exiting(this.getClass().toString(), "Constructor");
	}
	
	public void open(){
		
		logger.entering(this.getClass().toString(), "open");

		jni_initialize();
		jni_open();
		
		logger.exiting(this.getClass().toString(), "open");
	}
	
	@Override
	public void send(MidiMessage message, long timeStamp) {

		logger.entering(this.getClass().toString(), "send", new Object[]{message, timeStamp});

		jni_sendMessage(message.getMessage());
		
		logger.exiting(this.getClass().toString(), "send");
	}

	@Override
	public void close() {

		logger.entering(this.getClass().toString(), "close");
		
		jni_close();
		jni_finalize();
		
		logger.exiting(this.getClass().toString(), "close");
	}
}
