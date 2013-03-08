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
 * This interface defines convert message to packet for notify message to client.
 * <p>
 * [ Feature list ]
 * <ul>
 * 	<li>Determine message</li>
 *  <li>Convert message to packet</li>
 * </ul>
 * </p> 
 * @author Shuichi Miura
 */
public interface NotificationConverter {

	/**
	 * Determines whether this message is convert target
	 * @param message Device message
	 * @return True: Message is target, False: Message is not target
	 */
	public boolean isTarget(Message message);
	
	/**
	 * Convert message to packet for notify message to client.
	 * @param message Device message
	 * @return Packet
	 */
	public Packet convertPacket(Message message);
}
