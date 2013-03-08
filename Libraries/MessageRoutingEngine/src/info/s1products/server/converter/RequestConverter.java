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

import java.util.List;

/**
 * This interface defines convert packet to message for request from client.
 * <p>
 * [ Feature list ]
 * <ul>
 * 	<li>Determine packet</li>
 *  <li>Convert packet to message</li>
 * </ul>
 * </p> 
 * @author Shuichi Miura
 */
public interface RequestConverter {

	/**
	 * Determines whether this packet is convert target
	 * @param packet Client requested packet
	 * @return True: Packet is target, False: Packet is not target
	 */
	public boolean isTarget(Packet packet);
	
	/**
	 * Convert packet to message for send message to device.
	 * @param packet Client requested packet
	 * @return Message list
	 */
	public List<Message> convertMessageList(Packet packet);
}
