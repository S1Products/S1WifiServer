package info.s1products.server.device;

import info.s1products.server.event.MessageSendedEvent;
import info.s1products.server.event.MessageSendedListener;
import info.s1products.server.message.Message;
import info.s1products.server.router.RoutingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Driver base class
 * @author Shuichi Miura
 */
public abstract class DeviceMessageSender {

	private boolean isActive = false;
	private Properties driverProp;
	
	private List<MessageSendedListener> sendedListnerList 
		= new ArrayList<MessageSendedListener>();
	
// Abstract methods

	/**
	 * Check specified message is target for this driver
	 * @param address Address
	 * @return True is target for me. False is not target for me. 
	 */
	public abstract boolean isTargetMessage(Message message);
	
	/**
	 * Initialize device driver
	 * @param prop Settings for driver
	 * @return True is success. False is fail. 
	 */
	public abstract boolean onInitialize(Properties prop);
	
	/**
	 * Closing device driver
	 */
	public abstract boolean onClose();
	
	/**
	 * Receive message from router
	 * @param message Any message
	 * @return Processing result on driver
	 */
	public abstract RoutingResult onReceiveMessage(Message message);

	/**
	 * Constructor
	 * @param driverProp Driver properties
	 */
	public DeviceMessageSender(Properties driverProp){
		this.driverProp = driverProp;
	}
	
// Properties

	public boolean isActive(){
		return this.isActive;
	}
	
	protected void setIsActive(boolean isActive){
		this.isActive = isActive;
	}
	
	public Properties getDriverProperties(){
		return this.driverProp;
	}

	public void setDriverProperties(Properties driverProp){
		this.driverProp = driverProp;
	}
	
// Methods	

	public void addListener(MessageSendedListener listener){
		sendedListnerList.add(listener);
	}

	public void removeListener(MessageSendedListener listener){
		sendedListnerList.remove(listener);
	}

	public void removeListener(int index){
		sendedListnerList.remove(index);
	}

	public void removeAllListener(){
		sendedListnerList.clear();
	}

	public List<MessageSendedListener> getMessageReceivedListenerList(){
		return sendedListnerList;
	}
	
	public boolean open(){
		
		try{
			isActive = onInitialize(driverProp);
			return isActive;
			
		}catch(Exception ex){
	
			//TODO:error handling
			isActive = false;
			return false;
		}
	}
	
	public boolean close(){

		isActive = false;

		try{
			return onClose();
			
		}catch(Exception ex){
			
			//TODO:error handling
			return false;
		}
	}

	public RoutingResult receiveMessageFromRouter(Message message){
		
		try{
			RoutingResult result = onReceiveMessage(message);
			notifySendedEvent(message);
			
			return result;
			
		}catch(Exception ex){
			
			//TODO: error handling
			return null;
		}
	}
	
	private void notifySendedEvent(Message message){
		
		if(sendedListnerList == null 
				|| sendedListnerList.size() == 0){
			
			return;
		}
		
		MessageSendedEvent event 
			= new MessageSendedEvent(this, message);
		
		for (MessageSendedListener listener : sendedListnerList) {
			
			listener.messageSended(event);
		}
	}
}
