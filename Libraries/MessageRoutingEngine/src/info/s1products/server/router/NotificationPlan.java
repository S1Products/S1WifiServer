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

import info.s1products.server.converter.NotificationConverter;
import info.s1products.server.message.Message;
import info.s1products.server.notify.PacketNotifier;

public class NotificationPlan {

	private String targetAddress;
	
	private NotificationConverter converter;
	private PacketNotifier notifier;

	public NotificationPlan(String targetAddress, 
			NotificationConverter converter, PacketNotifier notifier){

		this.targetAddress = targetAddress;
		this.converter = converter;
		this.notifier = notifier;
	}
	
	public String getTargetAddress() {
		return targetAddress;
	}
	
	public NotificationConverter getConverter() {
		return converter;
	}
	
	public PacketNotifier getNotifier() {
		return notifier;
	}
	
	public boolean isTargetMessage(Message message){
		
		if(message.getAddress().equals(targetAddress)){
			
			return true;
		}

		return false;
	}
}
