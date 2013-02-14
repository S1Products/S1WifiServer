package info.s1products.server.notify;

import info.s1products.server.event.PacketNotifiedEvent;
import info.s1products.server.event.PacketNotifiedListener;
import info.s1products.server.message.Packet;
import info.s1products.server.router.RoutingResult;

import java.util.ArrayList;
import java.util.List;

public abstract class PacketNotifier {

	private boolean isActive = false;
	
	abstract boolean onInitialize() throws Exception;
	abstract RoutingResult onNotifyPacket(Packet packet) throws Exception;
	abstract boolean onStop() throws Exception;

	private List<PacketNotifiedListener> listenerList 
		= new ArrayList<PacketNotifiedListener>();

	public void addListener(PacketNotifiedListener listener){
		listenerList.add(listener);
	}

	public List<PacketNotifiedListener> getListenerList() {
		return listenerList;
	}
	
	public void setListenerList(List<PacketNotifiedListener> listenerList) {
		this.listenerList = listenerList;
	}

	public boolean isActive() {
		return isActive;
	}

	public boolean startNotifier(){

		if(isActive){
			return true;
		}
		
		try{
			isActive = onInitialize();
			
			return isActive;
			
		}catch(Exception ex){
			
			ex.printStackTrace();
			return false;
		}
	}
	
	public RoutingResult notifyPacket(Packet packet){

		try{
			RoutingResult result = onNotifyPacket(packet);

			if(result == null){
				
				result = new RoutingResult();
				result.setSuccess(false);
			}
			
			if(result.isSuccess()){
				
				notifyPacketNotifiedEvent(packet);
			}

			return result;
			
		}catch(Exception ex){

			RoutingResult result = new RoutingResult();
			result.setSuccess(false);
			
			return result;
		}
	}
	
	public boolean stopNotifier(){
		
		if(isActive == false){
			return true;
		}
		
		try{
			return onStop();
			
		}catch(Exception ex){
			
			ex.printStackTrace();
			return false;

		}finally{
			isActive = false;
		}
	}
	
	private void notifyPacketNotifiedEvent(Packet packet){
		
		if(this.listenerList == null 
				|| listenerList.size() == 0){
			
			return;
		}

		PacketNotifiedEvent event
			= new PacketNotifiedEvent(this, packet);
		
		for (PacketNotifiedListener listener : listenerList) {
			
			listener.packetNotified(event);
		}
	}
}
