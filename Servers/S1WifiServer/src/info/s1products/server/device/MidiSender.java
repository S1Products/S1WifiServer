package info.s1products.server.device;

import info.s1products.server.message.Argument;
import info.s1products.server.message.DataType.DataTypeEnum;
import info.s1products.server.message.Message;
import info.s1products.server.router.RoutingResult;

import java.util.Date;
import java.util.Properties;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

/**
 * 
 * @author Shuichi Miura
 */
public class MidiSender extends DeviceMessageSender {

	public static final String PROP_DEVICE_ID = "DeviceID";
	
	private MidiDevice device;
	private Receiver receiver;

	public MidiSender(Properties driverProp) {
		super(driverProp);
	}

	@Override
	public boolean isTargetMessage(Message message){

		//TODO:Implement sysex
		for (Argument argument : message.getArgumentList()) {
			
			if(argument.getDataType() == DataTypeEnum.MidiMessage){
				
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean onInitialize(Properties prop) {
		
		try{
			device = MidiDeviceUtil.getMidiOutDevice(prop.getProperty(PROP_DEVICE_ID));
			
			if(device == null){
				
				//TODO:error handling
				return false;
			}
			
			device.open();
			receiver = device.getReceiver();
			
		}catch(MidiUnavailableException ex){
			
			//TODO:error handling
			ex.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public boolean onClose() {

		receiver.close();
		device.close();
		
		return true;
	}

	@Override
	public RoutingResult onReceiveMessage(Message message) {
		
		RoutingResult result = new RoutingResult();
		
		try{
			sendMessage(message);
			
			result.setSuccess(true);
			
		}catch(InvalidMidiDataException ex){
			
			//TODO:error handling
			result.setSuccess(false);
		}
		
		return result;
	}

	private void sendMessage(Message message)
		throws InvalidMidiDataException{
		
		for (Argument argument : message.getArgumentList()) {

			if(argument.getDataType() != DataTypeEnum.MidiMessage){
				continue;
			}
			
			//TODO:For sysex message
			sendShortMessage(argument);
		}
	}
	
	private void sendShortMessage(Argument argument)
		throws InvalidMidiDataException{
		
		ShortMessage midiMessage = createShortMessage(argument);
		receiver.send(midiMessage, new Date().getTime());
	}
	
	private ShortMessage createShortMessage(Argument argument)
		throws InvalidMidiDataException{
		
		ShortMessage midiMessage = new ShortMessage();
		
		byte[] midiData = (byte[])argument.getData();
		
		int channel = midiData[0];
		int command = midiData[1] & 0xF0;
		
		if(midiData.length == 4){
			
			midiMessage.setMessage(command, channel, midiData[2], midiData[3]);
			
		}else if(midiData.length == 3){
			
			midiMessage.setMessage(command, channel, midiData[2], 0);
			
		}else if(midiData.length == 2){
			
			midiMessage.setMessage(command, channel, 0, 0);
		}
		
		return midiMessage;
	}
}
