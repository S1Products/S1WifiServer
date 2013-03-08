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

import info.s1products.server.message.Message;

import java.util.EventObject;

public class MessageReceivedEvent extends EventObject {

	private static final long serialVersionUID = -5925500012391533776L;

	private Message receivedMessage;
	
	public MessageReceivedEvent(Object source, Message message) {
		super(source);
		this.receivedMessage = message;
	}

	public Message getReceivedMessage() {
		return receivedMessage;
	}
}
