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

import info.s1products.server.message.Message;
import info.s1products.server.message.Packet;

import java.util.ArrayList;
import java.util.List;

/**
 * Convert message to packet and route it to notifiers.
 * @author Shuichi Miura
 */
public class NotificationPacketRouter extends Router {

// Properties
	
	private List<NotificationPlan> planList 
		= new ArrayList<NotificationPlan>();

// Property accessors

	public List<NotificationPlan> getPlanList() {
		return planList;
	}

	public void setPlanList(List<NotificationPlan> planList) {
		this.planList = planList;
	}
	
// Methods	
	
	public void notify(Message message){
		this.addQueue(message);
	}

	@Override
	List<RoutingResult> onRoute(Object object) {

		List<RoutingResult> resultList = new ArrayList<RoutingResult>();

		if(object == null){
			
			RoutingResult result = new RoutingResult();
			result.setSuccess(false);
			resultList.add(result);
			
			return resultList;
		}
		
		Message message = (Message)object;

		for (NotificationPlan plan : planList) {
			
			if(plan.isTargetMessage(message)){
				
				Packet packet 
					= plan.getConverter().convertPacket(message);
				
				RoutingResult result 
					= plan.getNotifier().notifyPacket(packet);
				
				resultList.add(result);
			}
		}

		return resultList;
	}
}
