package info.s1products.server.router;

import info.s1products.server.converter.RequestConverter;
import info.s1products.server.device.DeviceMessageSender;
import info.s1products.server.message.Message;
import info.s1products.server.message.Packet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Convert client request packet to message and route it to device senders.
 * <p>
 * This router has internal routing plans for improve routing performance.
 * </p>
 * @author Shuichi Miura
 */
public class RequestPacketRouter extends Router {

// Properties	
	
	/**
	 * Packet converter list
	 */
	private List<RequestConverter> converterList 
		= new ArrayList<RequestConverter>();

	/**
	 * Device sender list
	 */
	private List<DeviceMessageSender> senderList 
		= new ArrayList<DeviceMessageSender>();

	/**
	 * Routing plan for device sender
	 */
	private Map<String, RequestRoutingPlan> routingPlanMap
		= new HashMap<String, RequestRoutingPlan>();
	
// Property accessors	
	
	/**
	 * Set packet converter list
	 * @param converterList Packet converter list
	 */
	public void setConverterList(List<RequestConverter> converterList){
		this.converterList = converterList;
	}
	
	/**
	 * Get packet converter list
	 * @return Packet converter list
	 */
	public List<RequestConverter> getConverterList(){
		return converterList;
	}

	/**
	 * Get device sender list
	 * @return Device sender list
	 */
	public List<DeviceMessageSender> getSenderList() {
		return senderList;
	}
	
	/**
	 * Set DeviceMessageSenderList to router.
	 * <p>
	 * If you set senderList then internal routing plan is cleared.
	 * (Internal routing plan will be generate automatically when receive message.)
	 * </p>
	 * @param senderList Device sender list
	 */
	public void setSenderList(List<DeviceMessageSender> senderList) {
		this.senderList = senderList;
		routingPlanMap.clear();
	}

// Methods	
	
	/**
	 * Reset internal routing plans
	 * (Internal routing plan will be generate automatically when receive message.)
	 */
	public void resetRoutingPlan(){
		routingPlanMap.clear();
	}

	@Override
	List<RoutingResult> onRoute(Object object) {

		Packet packet = (Packet)object;
		
		List<RoutingResult> resultList = new ArrayList<RoutingResult>();
		
		for (RequestConverter converter : converterList) {
			
			if(converter.isTarget(packet)){
				
				List<Message> messageList 
					= converter.convertMessageList(packet);
	
				List<RoutingResult> tempResultList 
					= sendMessageList(messageList);
				
				resultList.addAll(tempResultList);
			}
		}
		
		return resultList;
	}
	
	/**
	 * Send message list to device senders
	 * @param messageList Target message list
	 * @return Routing results
	 */
	private List<RoutingResult> sendMessageList(List<Message> messageList){

		List<RoutingResult> resultList = new ArrayList<RoutingResult>();

		for(Message message : messageList){
			
			// Get sender list from routing plan
			List<DeviceMessageSender> targetSenderList
				= getTargetSenderList(message);
			
			for (DeviceMessageSender sender : targetSenderList) {
				
				RoutingResult result 
					= sender.receiveMessageFromRouter(message);
			
				resultList.add(result);
			}
		}
		
		return resultList;
	}
	
	/**
	 * Get sender list from internal routing plans.
	 * If routing plan does not generate yet then create new plan
	 * @param message Target message
	 * @return Target sender list for message
	 */
	private List<DeviceMessageSender> getTargetSenderList(Message message){
		
		String address = message.getAddress();
		RequestRoutingPlan plan = routingPlanMap.get(address);

		if(plan == null){
			
			plan = generatePlan(message);
			routingPlanMap.put(address, plan);
		}
		
		return plan.getSenderList();
	}

	/**
	 * Generate new plan with message address
	 * @param address Message address
	 * @return New routing plan
	 */
	private RequestRoutingPlan generatePlan(Message message){

		System.out.println("Generating new request routing plan...");
		
		List<DeviceMessageSender> targetSenderList 
			= new ArrayList<DeviceMessageSender>(); 
		
		for (DeviceMessageSender sender : senderList) {
			
			if(sender.isTargetMessage(message)){
				
				targetSenderList.add(sender);
			}
		}

		return new RequestRoutingPlan(targetSenderList);
	}
}
