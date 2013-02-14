package info.s1products.server.router;

import info.s1products.server.converter.NotificationConverter;
import info.s1products.server.message.Message;
import info.s1products.server.notify.PacketNotifier;

public class NotificationPlan {

	private String targetAddress;
	
	private NotificationConverter converter;
	private PacketNotifier notifier;

	public NotificationPlan(String targetAddress, 
			NotificationConverter converter, PacketNotifier notifier){

		this.targetAddress = targetAddress;
		this.converter = converter;
		this.notifier = notifier;
	}
	
	public String getTargetAddress() {
		return targetAddress;
	}
	
	public NotificationConverter getConverter() {
		return converter;
	}
	
	public PacketNotifier getNotifier() {
		return notifier;
	}
	
	public boolean isTargetMessage(Message message){
		
		if(message.getAddress().equals(targetAddress)){
			
			return true;
		}

		return false;
	}
}
