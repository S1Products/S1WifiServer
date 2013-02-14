package info.s1products.server.converter;

import info.s1products.server.message.Argument;
import info.s1products.server.message.DataType.DataTypeEnum;
import info.s1products.server.message.Message;
import info.s1products.server.message.Packet;
import info.s1products.server.router.RoutingConstants;
import info.s1products.server.util.ByteUtil;

import java.util.ArrayList;
import java.util.List;

public class SimpleMidiRequestConverter implements RequestConverter {

	//TODO: delete message prefix
	public static final int MESSAGE_PREFIX = 0x1;
	public static final int BUNDLE_PREFIX  = 0x2;
	
	@Override
	public List<Message> convertMessageList(Packet packet) {

		if(isBundle(packet)){
			return convertBundle(packet);
		}
		
		List<Message> messageList = new ArrayList<Message>();
		messageList.add(convertMessage(packet.getContents()));
		
		return messageList;
	}

	@Override
	public boolean isTarget(Packet packet) {
		
		if(packet.getContents().length == 0){
			return false;
		}
		
		byte firstByte = packet.getContents()[0];
		if(firstByte == BUNDLE_PREFIX){
			return true;

		//TODO:Check valid midi message
		}else if(firstByte == MESSAGE_PREFIX){
			return true;
		}
		
		return true;
	}

	private boolean isBundle(Packet packet) {
		
		byte[] data = packet.getContents();
		
		if(data.length == 0){
			return false;
		}

		if(data[0] == BUNDLE_PREFIX){
			return true;
		}
		
		return false;
	}

	private List<Argument> createArgumentList(byte[] data){
		
		List<Argument> argList = new ArrayList<Argument>();

		while(true){

			if(data.length == 0){
				break;
			}
			
			int length = data[0];
			data = ByteUtil.copy(data, 1);

			Argument arg = createArgument(data, length);
			argList.add(arg);
			
			data = ByteUtil.copy(data, length);
		}
		
		return argList;
	}
	
	private Argument createArgument(byte[] data, int length){
		
		//TODO:Implement MIDI Specification
		
		Argument arg = new Argument(DataTypeEnum.MidiMessage);
		
		byte[] midiData = new byte[length + 1];
		midiData[0] = (byte)(data[0] & 0x0F);
		midiData[1] = (byte)(data[0] & 0xF0);
		midiData[2] = data[1];
		midiData[3] = data[2];
		
		arg.setData(midiData);
		return arg;
	}
	
	/**
	 * Format: [Bundle] {[Length] [Status] [MIDIData]} {[Length] [Status] [MIDIData]} ...
	 * Example: 01 03 90 01 02 05 F7 01 02 03 F0
	 */
	private List<Message> convertBundle(Packet packet) {

		// Skip first bundle prefix
		byte[] data = ByteUtil.copy(packet.getContents(), 1);
		List<Argument> argList = createArgumentList(data);
		
		Message message = new Message();
		message.setAddress(RoutingConstants.ROUTER_ADDRESS);
		message.setIsValidMessage(true);
		message.setArgumentList(argList);
		
		List<Message> messageList = new ArrayList<Message>();
		messageList.add(message);
		
		return messageList;
	}

	private Message convertMessage(byte[] content) {
		
		List<Argument> argList = new ArrayList<Argument>();
	
		Argument arg = createArgument(content, 3);
		argList.add(arg);
		
		Message message = new Message();
		message.setAddress(RoutingConstants.ROUTER_ADDRESS);
		message.setIsValidMessage(true);
		message.setArgumentList(argList);

		return message;
	}
}
