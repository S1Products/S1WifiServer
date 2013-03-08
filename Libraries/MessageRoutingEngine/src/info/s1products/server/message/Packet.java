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
package info.s1products.server.message;

/**
 * 
 * @author Shuichi Miura
 */
public class Packet {

	private byte[] contents;
	
	public Packet(byte[] contents){
		this.contents = contents;
	}
	
	public byte[] getContents() {
		return contents;
	}
	
	public void setContents(byte[] contents) {
		this.contents = contents;
	}
}
