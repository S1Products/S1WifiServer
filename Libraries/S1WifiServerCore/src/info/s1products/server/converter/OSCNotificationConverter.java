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

import info.s1products.server.message.Message;
import info.s1products.server.message.Packet;

/**
 * 
 * @author Shuichi Miura
 */
public class OSCNotificationConverter implements NotificationConverter {

	@Override
	public boolean isTarget(Message message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Packet convertPacket(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

}
