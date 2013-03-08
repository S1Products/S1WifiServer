/*******************************************************************************
 * Copyright (c) 2013 Shuichi Miura.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Shuichi Miura - initial API and implementation
 ******************************************************************************/
package info.s1products.server.device;

import info.s1products.server.event.MessageReceivedEvent;
import info.s1products.server.event.MessageReceivedListener;
import info.s1products.server.event.MessageSentListener;
import info.s1products.server.message.Message;
import info.s1products.server.router.NotificationPacketRouter;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This class provided receive device message core functions.
 * To implement own message receiver, extend this abstract class.
 * <p>
 * [ Feature list ]
 * <ul>
 *  <li>Hold device driver properties</li>
 *  <li>Add message queue to NotificationRouter queue</li>
 *  <li>Notify message received event</li>
 * </ul>
 * </p> 
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
	protected abstract boolean onInitialize(Properties prop);
	
	/**
	 * Closing device driver
	 */
	protected abstract boolean onClose();
	
	/**
	 * Constructor
	 * @param driverProp Driver properties
	 */
	public DeviceMessageReceiver(Properties driverProp){
		this.driverProp = driverProp;
	}
	
// Properties

	/**
	 * Get notification router
	 * @return Notification router
	 */
	public NotificationPacketRouter getRouter() {
		return router;
	}

	/**
	 * Set notification router
	 * @param router notification router
	 */
	public void setRouter(NotificationPacketRouter router) {
		this.router = router;
	}

	/**
	 * Determine whether this receiver state is active or stopped
	 * @return True: active, False: stopped
	 */
	public boolean isActive(){
		return this.isActive;
	}
	
	/**
	 * Get device driver properties object 
	 * @return Device driver properties
	 */
	public Properties getDriverProperties(){
		return this.driverProp;
	}

	/**
	 * Set device driver properties object.
	 * Need restart receiver to reflect properties. 
	 * @param driverProp Device driver properties
	 */
	public void setDriverProperties(Properties driverProp){
		this.driverProp = driverProp;
	}
	
// Methods	

	/**
	 * Add message received listener.
	 * This listener will be called when receiver receive message from device.  
	 * @param listener Message received listener 
	 */
	public void addListener(MessageReceivedListener listener){
		receivedListnerList.add(listener);
	}

	/**
	 * Remove message received listener.
	 * @param listener Target listener
	 */
	public void removeListener(MessageSentListener listener){
		receivedListnerList.remove(listener);
	}

	/**
	 * Remove message received listener.
	 * @param index Target index
	 */
	public void removeListener(int index){
		receivedListnerList.remove(index);
	}

	/**
	 * Remove all message received listeners.
	 */
	public void removeAllListener(){
		receivedListnerList.clear();
	}

	/**
	 * Get all message received listeners.
	 * @return Message received listeners
	 */
	public List<MessageReceivedListener> getMessageReceivedListenerList(){
		return receivedListnerList;
	}
	
	/**
	 * Open device message receiver
	 * @return True: Opened, False: Failed to open
	 */
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
	
	/**
	 * Close device message receiver
	 * @return True: Closed, False: Failed to close
	 */
	public boolean closeReceiver(){

		isActive = false;

		try{
			return onClose();
			
		}catch(Exception ex){
			
			//TODO:error handling
			return false;
		}
	}

	/**
	 * Notify device message received event
	 * @param message Device message
	 */
	protected void notifyReceivedEvent(Message message){
		
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
