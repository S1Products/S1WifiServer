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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Shuichi Miura
 */
public class Message {

	private boolean isValidMessage = false;
	private long timetag;
	private String address;
	private List<Argument> argumentList = new ArrayList<Argument>();

// Constructors	
	
	public Message(){
	}
	
	public Message(String address, List<Argument> argumentList){

		this.address = address;
		this.argumentList = argumentList;
		isValidMessage = true;
	}

// Properties
	
	public boolean isValidMessage(){
		return isValidMessage;
	}
	
	public void setIsValidMessage(boolean isValid){
		
		this.isValidMessage = isValid;
	}
	
	public long getTimetag() {
		return timetag;
	}

	public void setTimetag(long timetag) {
		this.timetag = timetag;
	}

	public String getAddress() {
		return address;
	}
	
	public void setAddress(String addressPattern) {
		this.address = addressPattern;
	}
	
	public List<Argument> getArgumentList() {
		return argumentList;
	}
	
	public void setArgumentList(List<Argument> argumentList) {
		this.argumentList = argumentList;
	}

	public void add(Argument arg){
		this.argumentList.add(arg);
	}

	public void remove(Argument arg){
		this.argumentList.remove(arg);
	}
	
	public void remove(int index){
		this.argumentList.remove(index);
	}
}
