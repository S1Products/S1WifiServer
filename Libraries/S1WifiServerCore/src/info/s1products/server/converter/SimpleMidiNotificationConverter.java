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
package info.s1products.server.converter;

import info.s1products.server.ServerConstants;
import info.s1products.server.message.Argument;
import info.s1products.server.message.Message;
import info.s1products.server.message.Packet;

public class SimpleMidiNotificationConverter implements NotificationConverter {

	public static final int BUNDLE_PREFIX  = 0x2;

	@Override
	public boolean isTarget(Message message) {

		if(message.getAddress().equals(ServerConstants.ADDRESS_MIDI_NOTIFICATION)){
			return true;
		}
		
		return false;
	}

	@Override
	public Packet convertPacket(Message message) {

		if(message.getArgumentList().size() == 0){
			return null;
		}

		//TODO: Convert sysex and multiple message
		Argument arg = message.getArgumentList().get(0);
		
		return new Packet((byte[])arg.getData());
	}
}
