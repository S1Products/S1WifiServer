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
package info.s1products.server.event;

import info.s1products.server.message.Packet;

import java.util.EventObject;

public class RequestReceivedEvent extends EventObject {

	private static final long serialVersionUID = 911294150839716690L;

	private Packet packet;
	
	public RequestReceivedEvent(Object source, Packet packet) {
		super(source);
		
		this.packet = packet;
	}

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}
}
