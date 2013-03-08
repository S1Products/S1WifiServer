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
package info.s1products.server.router;

import static info.s1products.server.util.SleepUtil.waitMilli;
import info.s1products.server.event.RoutingErrorEvent;
import info.s1products.server.event.RoutingErrorListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 
 * @author Shuichi Miura
 */
public abstract class Router extends Thread {

	abstract List<RoutingResult> onRoute(Object obj);
	
	/**
	 * Packet queue (FIFO queue)
	 */
	private Queue<Object> routingQueue = new ConcurrentLinkedQueue<Object>();

	/**
	 * Run loop interval for routing message
	 */
	private int routingInterval = 10;
	
	private boolean isInterrupt = false;

	private List<RoutingErrorListener> listenerList
		= new ArrayList<RoutingErrorListener>();
	
	public int getRoutingInterval() {
		return routingInterval;
	}

	public void setRoutingInterval(int routingInterval) {
		this.routingInterval = routingInterval;
	}
	
	public void addListener(RoutingErrorListener listener){
		listenerList.add(listener);
	}
	
	public List<RoutingErrorListener> getListenerList() {
		return listenerList;
	}

	public void setListenerList(List<RoutingErrorListener> listenerList) {
		this.listenerList = listenerList;
	}

	/**
	 * Add packet to FIFO queue
	 * @param packet
	 */
	public void addQueue(Object obj){

		routingQueue.add(obj);
	}

	/**
	 * Run loop for routing packets
	 */
	public void run(){
		
		while(true){

			if(isInterrupt){
				
				break;
			}
			
			// Wait until next interval
			if(routingQueue.size() == 0){
				waitMilli(routingInterval);
				continue;
			}

			// Get packet from FIFO queue and route
			route(routingQueue.poll());
		}
	}
	
	private void route(Object packet){
		try{
			List<RoutingResult> resultList = onRoute(packet);

			exportRoutingError(resultList);

		}catch(Exception ex){
			//TODO:Error handle
		}
	}
	
	public void stopRouter(){
		
		isInterrupt = true;
	}
	
	private void exportRoutingError(List<RoutingResult> resultList){
		
		//TODO:error log handling
		for (RoutingResult result : resultList) {
		
			if(result.isSuccess() == false){

				notifyErrorEvent(result);
			}
		}
	}
	
	private void notifyErrorEvent(RoutingResult result){
		
		RoutingErrorEvent event = new RoutingErrorEvent(this, result);
		
		for (RoutingErrorListener listener : listenerList) {

			listener.routingErrorOccurred(event);
		}
	}
}
