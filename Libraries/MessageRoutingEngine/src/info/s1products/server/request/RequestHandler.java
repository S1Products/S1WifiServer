package info.s1products.server.request;

import info.s1products.server.event.RequestReceivedEvent;
import info.s1products.server.event.RequestReceivedListener;
import info.s1products.server.message.Packet;
import info.s1products.server.router.RequestPacketRouter;
import info.s1products.server.router.Router;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Shuichi Miura
 */
public abstract class RequestHandler extends Thread {

// Abstract methods
	
	public abstract boolean onInitialize() throws Exception;
	public abstract byte[] waitClientRequest() throws Exception;
	public abstract boolean onStop() throws Exception;

// Properties	
	
	private RequestPacketRouter router;
	private boolean doInterrupt;

	private List<RequestReceivedListener> listenerList 
		= new ArrayList<RequestReceivedListener>();

	public Router getRouter() {
		return router;
	}

	public void setRouter(RequestPacketRouter router) {
		this.router = router;
	}

	public void addListener(RequestReceivedListener listener){
		listenerList.add(listener);
	}

	public List<RequestReceivedListener> getListenerList() {
		return listenerList;
	}

	public void setListenerList(List<RequestReceivedListener> listenerList) {
		this.listenerList = listenerList;
	}
	
// Methods	

	private boolean initializeListener(){
		
		try{
			return onInitialize();
			
		}catch(Exception ex){
			
			//TODO: Error handle
			return false;
		}
	}
	
	public void run(){
		
		initializeListener();
		
		while(true){
			
			if(doInterrupt){
				
				return ;
			}
			
			byte[] data = null;
			
			try{
				data = waitClientRequest();
				
			}catch(Exception ex){
				//TODO: error handling
				continue;
			}

			// if receive invalid message
			if(data.length == 0){
				continue;
			}
			
			Packet packet = new Packet(data);
			notifyPacketReceivedEvent(packet);
			
			// Create packet and add routing queue
			router.addQueue(packet);
		}
	}
	
	public boolean stopHandler(){

		//TODO:Send interrupt message to handlers

		try{
			doInterrupt = true;
			return onStop();
			
		}catch(Exception ex){
			
			//TODO: error handle
			return false;
		}
	}
	
	private void notifyPacketReceivedEvent(Packet packet){
		
		if(this.listenerList == null 
				|| listenerList.size() == 0){
			
			return;
		}

		RequestReceivedEvent event
			= new RequestReceivedEvent(this, packet);
		
		for (RequestReceivedListener listener : listenerList) {
			
			if(listener != null){
				
				listener.packetReceived(event);
			}
		}
	}
}
