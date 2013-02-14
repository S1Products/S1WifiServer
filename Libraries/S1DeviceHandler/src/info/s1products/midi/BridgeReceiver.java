package info.s1products.midi;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

public class BridgeReceiver implements Receiver {

	private BridgeReceiverListener receiveListener;

	@Override
	public void send(MidiMessage message, long timeStamp) {
		receiveListener.onReceived(message.getMessage());
	}

	@Override
	public void close() {
		receiveListener = null;
	}
}
