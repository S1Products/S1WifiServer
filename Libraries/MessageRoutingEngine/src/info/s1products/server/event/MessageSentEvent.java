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

public class MessageSentEvent extends EventObject {

	private static final long serialVersionUID = -8639692837173256639L;

	private Message message;
	
	public MessageSentEvent(Object source, Message message) {
		super(source);
		this.message = message;
	}

	public Message getMessage() {
		return message;
	}
}
