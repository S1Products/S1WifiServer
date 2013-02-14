package info.s1products.server.converter;

import info.s1products.server.message.Message;
import info.s1products.server.message.Packet;

public interface NotificationConverter {

	public boolean isTarget(Message message);
	public Packet convertPacket(Message message);

}
