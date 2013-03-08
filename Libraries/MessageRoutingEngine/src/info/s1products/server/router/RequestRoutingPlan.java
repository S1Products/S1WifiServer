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

import java.util.List;

import info.s1products.server.device.DeviceMessageSender;

public class RequestRoutingPlan {

	private List<DeviceMessageSender> senderList;

	public RequestRoutingPlan(List<DeviceMessageSender> senderList){
		
		this.senderList = senderList;
	}
	
	public List<DeviceMessageSender> getSenderList() {
		return senderList;
	}

	public void setSenderList(List<DeviceMessageSender> senderList) {
		this.senderList = senderList;
	}
}
