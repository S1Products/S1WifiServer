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
package info.s1products.server;

import info.s1products.server.converter.RequestConverter;
import info.s1products.server.device.DeviceMessageReceiver;
import info.s1products.server.device.DeviceMessageSender;
import info.s1products.server.notify.PacketNotifier;
import info.s1products.server.request.RequestHandler;
import info.s1products.server.router.NotificationPacketRouter;
import info.s1products.server.router.NotificationPlan;
import info.s1products.server.router.RequestPacketRouter;
import info.s1products.server.router.Router;

import java.util.List;

/**
 * This class provided message routing core functions.
 * To implement own message routing server, extend this abstract class.
 * <p>
 * [ Feature list ]
 * <ul>
 * 	<li>Create message routers</li>
 *  <li>Start message routers</li>
 *  <li>Stop message routers</li>
 * </ul>
 * </p> 
 * @author Shuichi Miura
 * @see Router
 * @see RequestPacketRouter
 * @see NotificationPacketRouter
 */
public abstract class ServerBase {

// Abstract methods -->	
	
	// Start server methods -->
	
	/**
	 * This method called on startServer method.
	 * If call startServer method 
	 * then this method will be called before start routers.
	 */
	protected abstract void prepareServer();

	/**
	 * This method called on startServer method and start request router.
	 * If you want to initialize some objects, 
	 * implement them on this method.
	 * @param router Start target request router
	 */
	protected abstract void onStartRequestRouter(RequestPacketRouter router);

	/**
	 * This method called on startServer method and start notification router.
	 * If you want to initialize some objects, 
	 * implement them on this method.
	 * @param router Start target request router
	 */
	protected abstract void onStartNotificationRouter(NotificationPacketRouter router);

	/**
	 * Create request converter list objects on this method.
	 * This method called on startServer method and start request router.
	 * @return Converter list
	 */
	protected abstract List<RequestConverter>      createRequestConverterList();

	/**
	 * Create device message sender list objects on this method.
	 * This method called on startServer method and start request router.
	 * @return Device message sender list
	 */
	protected abstract List<DeviceMessageSender>   createDeviceMessageSenderList();
	
	/**
	 * Create request handler list objects on this method.
	 * This method called on startServer method and start request router.
	 * @return Reuqest handler list
	 */
	protected abstract List<RequestHandler>  		createRequestHandlerList();

	/**
	 * Create notification plan list objects on this method.
	 * This method called on startServer method and start notification router.
	 * @return Notification plan list
	 */
	protected abstract List<NotificationPlan>      createNotificationPlanList();

	/**
	 * Create device message receiver list objects on this method.
	 * This method called on startServer method and start notification router.
	 * @return Device message receiver list
	 */
	protected abstract List<DeviceMessageReceiver> createDeviceMessageReceiverList();

	// <-- Start server methods

	// Stop server methods -->
	
	/**
	 * This method called on stopServer method.
	 * If call stopServer method 
	 * then this method will be called after stop routers. 
	 */
	protected abstract void cleanupServer();
	
	//<-- Stop server methods

//<-- Abstract methods	
	
	/**
	 * Flags of server is working
	 */
	private boolean isWorking;

	private RequestPacketRouter 	 requestRouter;
	private NotificationPacketRouter notificationRouter;

	private List<RequestHandler>  handlerList;
	private List<DeviceMessageReceiver> receiverList;

	/**
	 * Determine whether this server state is working or stopped
	 * @return True: working, False: stopped
	 */
	public boolean isWorking() {
		return isWorking;
	}

// Start server processes -->	
	
	/**
	 * Start request and notification routers.
	 * @return True: Working, False: Stopped 
	 */
	public boolean startServer(){

		prepareServer();
		
		startRequestRouter();
		startNotificationRouter();		
		
		isWorking = true;
		
		return true;
	}
	
	private void startRequestRouter(){
		
		List<RequestConverter> requestConverterList 
			= createRequestConverterList();
	
		requestRouter = new RequestPacketRouter();
		onStartRequestRouter(requestRouter);
		
		requestRouter.setConverterList(requestConverterList);
	
		List<DeviceMessageSender> senderList 
			= createDeviceMessageSenderList();
		
		for (DeviceMessageSender sender : senderList) {
			
			sender.openSender();
		}
		
		requestRouter.setSenderList(senderList);
		
		handlerList = createRequestHandlerList();
	
		for (RequestHandler handler : handlerList) {
			
			handler.setRouter(requestRouter);
			handler.start();
		}
		
		requestRouter.start();
	}
	
	private void startNotificationRouter(){

		List<NotificationPlan> planList 
			= createNotificationPlanList();
	
		notificationRouter = new NotificationPacketRouter();
		onStartNotificationRouter(notificationRouter);
		
		notificationRouter.setPlanList(planList);
	
		receiverList 
			= createDeviceMessageReceiverList();
		
		for (DeviceMessageReceiver receiver : receiverList) {

			receiver.setRouter(notificationRouter);
			receiver.openReceiver();
		}
		
		for (NotificationPlan plan : planList) {
			
			PacketNotifier notifier = plan.getNotifier();
			
			if(notifier.isActive() == false){
				
				notifier.startNotifier();
			}
		}
		
		notificationRouter.start();
	}

//<-- Start server processes	

// Stop server processes -->
	
	/**
	 * Stop request and notification routers.
	 */
	public void stopServer(){

		stopRequestRouter();

		stopNotifierRouter();

		cleanupServer();
		
		isWorking = false;
	}
	
	private void stopRequestRouter(){
		
		requestRouter.stopRouter();
		
		List<DeviceMessageSender> senderList 
			= requestRouter.getSenderList();
	
		for (DeviceMessageSender sender : senderList) {
			
			sender.closeSender();
		}
		
		for (RequestHandler handler : handlerList) {
			
			handler.stopHandler();
		}
	}
	
	private void stopNotifierRouter(){
		
		notificationRouter.stopRouter();
		
		for (DeviceMessageReceiver receiver : receiverList) {
			
			receiver.closeReceiver();
		}
		
		List<NotificationPlan> planList 
			= notificationRouter.getPlanList();
		
		for (NotificationPlan plan : planList) {
			
			PacketNotifier notifier = plan.getNotifier();
			
			if(notifier.isActive()){
				
				notifier.stopNotifier();
			}
		}
	}

//<-- Stop server processes

}
