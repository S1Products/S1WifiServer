package info.s1products.server.device;

import info.s1products.server.event.MessageReceivedEvent;
import info.s1products.server.event.MessageReceivedListener;
import info.s1products.server.event.MessageSendedListener;
import info.s1products.server.message.Message;
import info.s1products.server.router.NotificationPacketRouter;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Driver base class
 * @author Shuichi Miura
 */
public abstract class DeviceMessageReceiver {

	private boolean isActive = false;
	private Properties driverProp;
	private NotificationPacketRouter router;
	
	private List<MessageReceivedListener> receivedListnerList 
		= new ArrayList<MessageReceivedListener>();
	
// Abstract methods

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
	 * Constructor
	 * @param driverProp Driver properties
	 */
	public DeviceMessageReceiver(Properties driverProp){
		this.driverProp = driverProp;
	}
	
// Properties

	public NotificationPacketRouter getRouter() {
		return router;
	}

	public void setRouter(NotificationPacketRouter router) {
		this.router = router;
	}

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

	public void addListener(MessageReceivedListener listener){
		receivedListnerList.add(listener);
	}

	public void removeListener(MessageSendedListener listener){
		receivedListnerList.remove(listener);
	}

	public void removeListener(int index){
		receivedListnerList.remove(index);
	}

	public void removeAllListener(){
		receivedListnerList.clear();
	}

	public List<MessageReceivedListener> getMessageReceivedListenerList(){
		return receivedListnerList;
	}
	
	public boolean openReceiver(){
		
		try{
			isActive = onInitialize(driverProp);
			return isActive;
			
		}catch(Exception ex){
	
			//TODO:error handling
			isActive = false;
			return false;
		}
	}
	
	public boolean closeReceiver(){

		isActive = false;

		try{
			return onClose();
			
		}catch(Exception ex){
			
			//TODO:error handling
			return false;
		}
	}

	public void notifyReceivedEvent(Message message){
		
		if(receivedListnerList == null 
				|| receivedListnerList.size() == 0){
			
			return;
		}
		
		MessageReceivedEvent event 
			= new MessageReceivedEvent(this, message);
		
		for (MessageReceivedListener listener : receivedListnerList) {
			
			listener.messageReceived(event);
		}
	}
}
