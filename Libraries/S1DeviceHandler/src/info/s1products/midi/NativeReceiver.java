package info.s1products.midi;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

public class NativeReceiver implements Receiver {

	native private void jni_initialize();
	native private void jni_finalize();
	native private void jni_open();
	native private void jni_close();
	native private void jni_sendMessage(byte[] data);

	public NativeReceiver(String libraryName){
		System.loadLibrary(libraryName);
		jni_initialize();
	}
	
	public void finalize(){
		jni_finalize();
	}
	
	public void open(){
		jni_open();
	}
	
	@Override
	public void send(MidiMessage message, long timeStamp) {
		jni_sendMessage(message.getMessage());
	}

	@Override
	public void close() {
		jni_close();
	}
}
