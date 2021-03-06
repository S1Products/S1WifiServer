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

import info.s1products.server.router.RoutingResult;

import java.util.EventObject;

public class RoutingErrorEvent extends EventObject {

	private static final long serialVersionUID = -3923893585690179136L;

	private RoutingResult result;

	public RoutingErrorEvent(Object source, RoutingResult result) {
		super(source);
		this.result = result;
	}

	public RoutingResult getResult() {
		return result;
	}

	public void setResult(RoutingResult result) {
		this.result = result;
	}
	
}
