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
package info.s1products.server.core.mock.driver;

import info.s1products.server.device.DeviceMessageSender;
import info.s1products.server.message.Message;
import info.s1products.server.router.RoutingResult;

import java.util.Properties;

public class MockDriver extends DeviceMessageSender {

	public MockDriver(Properties driverProp) {
		super(driverProp);
	}

	@Override
	public boolean isTargetMessage(Message message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onInitialize(Properties prop) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onClose() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RoutingResult onReceiveMessage(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

}
