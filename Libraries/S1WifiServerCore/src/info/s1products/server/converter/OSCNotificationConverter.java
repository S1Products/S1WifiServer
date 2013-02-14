package info.s1products.server.converter;

import info.s1products.server.message.Message;
import info.s1products.server.message.Packet;

/**
 * 
 * @author Shuichi Miura
 */
public class OSCNotificationConverter implements NotificationConverter {

	@Override
	public boolean isTarget(Message message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Packet convertPacket(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

}
