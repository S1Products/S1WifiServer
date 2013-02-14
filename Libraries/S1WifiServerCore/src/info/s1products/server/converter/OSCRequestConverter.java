package info.s1products.server.converter;

import static info.s1products.server.message.DataType.BIG_ENDIAN64;
import static info.s1products.server.message.DataType.BLOB;
import static info.s1products.server.message.DataType.CHARACTER;
import static info.s1products.server.message.DataType.COLOR;
import static info.s1products.server.message.DataType.DOUBLE64;
import static info.s1products.server.message.DataType.FALSE;
import static info.s1products.server.message.DataType.FLOAT32;
import static info.s1products.server.message.DataType.INFINITE;
import static info.s1products.server.message.DataType.INT32;
import static info.s1products.server.message.DataType.LONG64;
import static info.s1products.server.message.DataType.MIDI_MESSAGE;
import static info.s1products.server.message.DataType.NIL;
import static info.s1products.server.message.DataType.STRING;
import static info.s1products.server.message.DataType.SYMBOL;
import static info.s1products.server.message.DataType.TIME_TAG;
import static info.s1products.server.message.DataType.TRUE;
import info.s1products.server.message.Argument;
import info.s1products.server.message.DataType.DataTypeEnum;
import info.s1products.server.message.Message;
import info.s1products.server.message.Packet;
import info.s1products.server.util.ByteUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Shuichi Miura
 */
public class OSCRequestConverter implements RequestConverter {

	public static final char BUNDLE_KEY = 0x23;
	public static final int  BUNDLE_HEADER_SIZE = 16;
	
	public static final char NULL_CHAR = 0x00;
	public static final char COMMA = 0x2c;

	@Override
	public List<Message> convertMessageList(Packet packet) {
		
		if(isBundle(packet)){
			return convertBundle(packet);
			
		}else{
			
			List<Message> messageList = new ArrayList<Message>();
			messageList.add(convertMessage(packet.getContents()));

			return messageList;
		}
	}

	@Override
	public boolean isTarget(Packet packet) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean isBundle(Packet packet){
		
		if(packet.getContents() == null){
			return false;
		}

		// Compare first byte is '#'
		if(packet.getContents()[0] == (byte)BUNDLE_KEY){
			return true;
		}

		return false;
	}
	
	private List<Message> convertBundle(Packet packet){
		
		byte[] contents = packet.getContents();
		
		List<Message> messageList = new ArrayList<Message>();

		if (contents.length > BUNDLE_HEADER_SIZE) {
			
			Long timeTag = (new Long(ByteUtil.toLong(ByteUtil.copy(contents, 8, 8)))).longValue();
			
			int currentPosition = BUNDLE_HEADER_SIZE;
			
			int messageLength = ByteUtil.toInt(ByteUtil.copy(contents, currentPosition, 4));
		
			while (messageLength != 0 && (messageLength % 4 == 0)&& currentPosition < contents.length) {

				currentPosition += 4;
				
				byte[] messageData 
					= ByteUtil.copy(contents, currentPosition, messageLength);
				
				Message message = convertMessage(messageData);
				message.setTimetag(timeTag);
				messageList.add(message);
				
				currentPosition += messageLength;
				
				if(currentPosition >= contents.length){
					
					break;
				}
				
				messageLength = ByteUtil.toInt(ByteUtil.copy(contents, currentPosition,4));
			}
		}
		
		for (Message message : messageList) {
			
			if(message.isValidMessage() == false){
				
				messageList.remove(message);
			}
		}
		
		return messageList;
	}

	public Message convertMessage(byte[] content){
		
		Message message = new Message();
		
		int contentLength = content.length;
		int currentIndex = 0;
		
		currentIndex = parseAddressPattern(message, content, contentLength);
	    
	    if (currentIndex != -1) {
	    	
	    	List<Argument> argumentList 
	    		= parseArguments(content, contentLength, currentIndex);
	    	
	    	message.setArgumentList(argumentList);
	    	message.setIsValidMessage(true);
	    	
	    	return message;

	    } else {
	    	
		    return null;
	    }
	}
	
	private int parseAddressPattern(Message message, byte[] content, int length) {
		
		for (int i = 0; i < length; i++) {
			
			if (content[i] == NULL_CHAR) {
				
				message.setAddress(new String(content, 0, i));
				return i + ByteUtil.align(i);
			}
		}
		
		return -1;
	}

	private List<Argument> parseArguments(byte[] content, int length, int index) {

		List<Argument> argumentList = new ArrayList<Argument>();

		if(content.length < index){
			return argumentList;
		}
		
		byte[] typeTag = null;
		
		int tagLength = 0;
		
		if (content[index] == COMMA) {
			
			index++;
			for (int i = index; i < length; i++) {
				
				if (content[i] == NULL_CHAR) {
					
					typeTag = ByteUtil.copy(content, index, i - index);
					index = i + ByteUtil.align(i);
					tagLength++;
					break;
				}
			}
		}

		if(tagLength == 0 || typeTag == null){
			
			return argumentList;
		}
		
    	byte[] argData = ByteUtil.copy(content, index);
		
		int tagIndex = 0;
		int dataIndex = 0;
		
		while (tagIndex < typeTag.length) {

			char type = (char)typeTag[tagIndex];

			Object argValue = null;
			
			switch (typeTag[tagIndex]) {
				case CHARACTER:
					argValue = new Character((char)ByteUtil.toInt(ByteUtil.copy(argData, dataIndex, 4)));
					dataIndex += 4;
					break;
					
				case INT32:
					argValue = new Integer(ByteUtil.toInt(ByteUtil.copy(argData, dataIndex, 4)));
					dataIndex += 4;
					break;
					
				case FLOAT32:
					argValue = new Float(ByteUtil.toFloat(ByteUtil.copy(argData, dataIndex, 4)));
					dataIndex += 4;
					break;
					
				case TIME_TAG:
				case LONG64:
				case BIG_ENDIAN64:
					argValue = new Long(ByteUtil.toLong(ByteUtil.copy(argData, dataIndex, 8)));
					dataIndex += 8;
					break;
					
				case DOUBLE64:
					argValue = new Double(ByteUtil.toDouble(ByteUtil.copy(argData, dataIndex, 8)));
					dataIndex += 8;
					break;
					
				case SYMBOL:
				case STRING:
					int newIndex = dataIndex;
					StringBuffer stringBuffer = new StringBuffer();

					stringLoop:
		            do {
		            	if (argData[newIndex] == 0x00) {
		            		break stringLoop;
		            		
		            	} else {
		            		stringBuffer.append((char) argData[newIndex]);
		            	}

		            	newIndex++;
		            	
		            } while (newIndex < argData.length);

					argValue = (stringBuffer.toString());
					dataIndex = newIndex + ByteUtil.align(newIndex);
					break;
  
				case BLOB:
					int myLen = ByteUtil.toInt(ByteUtil.copy(argData, dataIndex, 4));
					dataIndex += 4;
					argValue = ByteUtil.copy(argData, dataIndex, myLen);
					dataIndex += myLen + (ByteUtil.align(myLen) % 4);
					break;
					
				case MIDI_MESSAGE:
					argValue = ByteUtil.copy(argData, dataIndex, 4);
					dataIndex += 4;
					break;
					
				case TRUE:
					argValue = new Boolean(true);
					break;

				case FALSE:
					argValue = new Boolean(false);
					break;

				case NIL:
					argValue = null;
					break;
					
				case INFINITE:
					argValue = null;
					break;
					
				case COLOR:
					byte[] colorData = ByteUtil.copy(argData, dataIndex, 4);
					argValue = ByteUtil.toColor(colorData);
					dataIndex += 4;
					break;
					
				default:
					argValue = ByteUtil.copy(argData, dataIndex, argData.length - dataIndex);
					break;
			}
			
			// Create one argument
			Argument argument 
				= new Argument(DataTypeEnum.parse(type), type, argValue);
			
			argumentList.add(argument);
			
			tagIndex++;
		}
		
		return argumentList;
	}
/*	
// Convert Packet
	
	public Packet convertPacket(Message message){

		byte[] contents = getMessageBytes(message);
		return new Packet(contents);
	}
	
	private byte[] getMessageBytes(Message message){
		
		// Address
		byte[] addrBytes = message.getAddress().getBytes();
		byte[] data = ByteUtil.appendNullToRight(addrBytes, 4);
		
		// DataType
		byte[] dataTypeBytes 
			= getDataTypeBytes(message.getArgumentList());
		
		dataTypeBytes = ByteUtil.appendNullToRight(dataTypeBytes, 4);
		data = ByteUtil.append(data, dataTypeBytes);
		
		// Arguments
		for (Argument arg : message.getArgumentList()) {

			byte[] argByte = getBytes(arg);
			
			if(argByte != null){
				
				data = ByteUtil.append(data, argByte);
			}
		}
		
		return data;
	}
	
	private byte[] getDataTypeBytes(List<Argument> argList){
		
		byte[] data = new byte[argList.size() + 1];
		
		data[0] = ','; 
				
		for(int i = 1; i < data.length ; i++){
			
			data[i] = (byte)argList.get(i - 1).getRawDataType();
		}
		
		return data;
	}
	
	private byte[] getBytes(Argument arg){

		byte[] data = null;
		
		switch (arg.getDataType()) {
		
			case Int32:
				int i = (Integer)arg.getData();
				data = ByteUtil.toBytes(i);
				break;
	
			case Float32:
				float f = (Float)arg.getData();
				data = ByteUtil.toBytes(f);
				break;

			case Long64:
			case BigEndian64:
				long l = (Long)arg.getData();
				data = ByteUtil.toBytes(l);
				break;
				
			case String:
			case Symbol:
				String s = (String)arg.getData();
				data = ByteUtil.appendNullToRight(s.getBytes(), 4);
				break;

			case Blob:
				byte[] blob = (byte[])arg.getData();
				byte[] size = ByteUtil.toBytes(blob.length);
				
				data = ByteUtil.append(size, blob);
				break;
				
			case Timetag:
				long t = (Long)arg.getData();
				data = ByteUtil.toBytes(t);
				break;

			case Double64:
				double d = (Double)arg.getData();
				data = ByteUtil.toBytes(d);
				break;

			case Character:
				char c = (Character)arg.getData();
				data = new byte[4];
				data[0] = (byte)c;
				break;
				
			case Color:
				Color color = (Color)arg.getData();
				data = new byte[4];
				data[0] = (byte)color.getRed();
				data[1] = (byte)color.getGreen();
				data[2] = (byte)color.getBlue();
				data[3] = (byte)color.getAlpha();
				break;

			case MidiMessage:
				byte[] m = (byte[])arg.getData();
				data = new byte[4];
				data[0] = (byte)m[0];
				
				if(m.length >= 2){
					data[1] = (byte)m[1];
				}
				
				if(m.length >= 3){
					data[2] = (byte)m[2];
				}
				
				if(m.length >= 4){
					data[3] = (byte)m[3];
				}

				break;

			case True:
			case False:
			case Nil:
			case Infinite:
				data = null;
				break;
				
			case Undefined:
				data = (byte[])arg.getData();
				break;
				
			default:
				break;
		}
		
		return data;
	}
	
	public Packet convertPacket(Bundle bundle){
		
		return null;
	}

	public Packet convertPacket(List<Message> messageList){
		
		return null;
	}

// Convert message
	
	public Message convertMessage(Packet packet){

		byte[] content = packet.getContents();
		return convertMessage(content);
	}
*/	
}
