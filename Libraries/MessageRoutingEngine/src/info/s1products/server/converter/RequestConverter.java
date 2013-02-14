package info.s1products.server.converter;

import info.s1products.server.message.Message;
import info.s1products.server.message.Packet;

import java.util.List;

/**
 * 
 * @author Shuichi Miura
 */
public interface RequestConverter {

	public boolean isTarget(Packet packet);
	
	public List<Message> convertMessageList(Packet packet);
}
