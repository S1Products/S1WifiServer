package info.s1products.server.router;

import java.util.List;

import info.s1products.server.device.DeviceMessageSender;

public class RequestRoutingPlan {

	private List<DeviceMessageSender> senderList;

	public RequestRoutingPlan(List<DeviceMessageSender> senderList){
		
		this.senderList = senderList;
	}
	
	public List<DeviceMessageSender> getSenderList() {
		return senderList;
	}

	public void setSenderList(List<DeviceMessageSender> senderList) {
		this.senderList = senderList;
	}
}
