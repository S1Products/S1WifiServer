package info.s1products.server;

import info.s1products.server.converter.RequestConverter;
import info.s1products.server.device.DeviceMessageReceiver;
import info.s1products.server.device.DeviceMessageSender;
import info.s1products.server.notify.PacketNotifier;
import info.s1products.server.request.RequestHandler;
import info.s1products.server.router.NotificationPacketRouter;
import info.s1products.server.router.NotificationPlan;
import info.s1products.server.router.RequestPacketRouter;

import java.util.List;

public abstract class ServerBase {

	public abstract void prepareServer();
	public abstract void cleanupServer();
	
	public abstract void onStartRequestRouter(RequestPacketRouter router);
	public abstract void onStartNotificationRouter(NotificationPacketRouter router);
	
	public abstract List<RequestConverter>      createRequestConverterList();
	public abstract List<DeviceMessageSender>   createDeviceMessageSenderList();
	public abstract List<RequestHandler>  		createRequestHandlerList();

	public abstract List<NotificationPlan>      createNotificationPlanList();
	public abstract List<DeviceMessageReceiver> createDeviceMessageReceiverList();

	private boolean isWorking;

	private RequestPacketRouter 	 requestRouter;
	private NotificationPacketRouter notificationRouter;

	private List<RequestHandler>  handlerList;
	private List<DeviceMessageReceiver> receiverList;

	public boolean isWorking() {
		return isWorking;
	}

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
			
			sender.open();
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
			
			sender.close();
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
}
