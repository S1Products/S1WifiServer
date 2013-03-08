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

import info.s1products.server.event.MessageSentEvent;
import info.s1products.server.event.MessageSentListener;
import info.s1products.server.message.Message;
import info.s1products.server.router.RoutingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This class provided send message to device core functions.
 * To implement own message sender, extend this abstract class.
 * <p>
 * [ Feature list ]
 * <ul>
 * 	<li>Receive message from request router</li>
 *  <li>Send message to device</li>
 *  <li>Notify message sent event</li>
 * </ul>
 * </p> 
 * @author Shuichi Miura
 */
public abstract class DeviceMessageSender {

	private boolean isActive = false;
	private Properties driverProp;
	
	private List<MessageSentListener> sentListnerList 
		= new ArrayList<MessageSentListener>();
	
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
	protected abstract boolean onInitialize(Properties prop);
	
	/**
	 * Closing device driver
	 */
	protected abstract boolean onClose();
	
	/**
	 * Receive message from router
	 * @param message Any message
	 * @return Processing result on driver
	 */
	protected abstract RoutingResult onReceiveMessage(Message message);

	/**
	 * Constructor
	 * @param driverProp Driver properties
	 */
	public DeviceMessageSender(Properties driverProp){
		this.driverProp = driverProp;
	}
	
// Properties

	/**
	 * Determine whether this sender state is active or stopped
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
	 * Add message sent listener.
	 * This listener will be called when sender send message to device.  
	 * @param listener Message received listener 
	 */
	public void addListener(MessageSentListener listener){
		sentListnerList.add(listener);
	}

	/**
	 * Remove message sent listener.
	 * @param listener Target listener
	 */
	public void removeListener(MessageSentListener listener){
		sentListnerList.remove(listener);
	}

	/**
	 * Remove message received listener.
	 * @param index Target listener index
	 */
	public void removeListener(int index){
		sentListnerList.remove(index);
	}

	/**
	 * Remove all message sent listeners.
	 */
	public void removeAllListener(){
		sentListnerList.clear();
	}

	/**
	 * Get all message sent listeners.
	 * @return Message sent listeners
	 */
	public List<MessageSentListener> getMessageSentListenerList(){
		return sentListnerList;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean openSender(){
		
		try{
			isActive = onInitialize(driverProp);
			return isActive;
			
		}catch(Exception ex){
	
			//TODO:error handling
			isActive = false;
			return false;
		}
	}
	
	public boolean closeSender(){

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
			notifyMessageSentEvent(message);
			
			return result;
			
		}catch(Exception ex){
			
			//TODO: error handling
			return null;
		}
	}
	
	private void notifyMessageSentEvent(Message message){
		
		if(sentListnerList == null 
				|| sentListnerList.size() == 0){
			
			return;
		}
		
		MessageSentEvent event 
			= new MessageSentEvent(this, message);
		
		for (MessageSentListener listener : sentListnerList) {
			
			listener.messageSent(event);
		}
	}
}
