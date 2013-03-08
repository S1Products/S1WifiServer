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

import info.s1products.server.message.DataType.DataTypeEnum;

public class Argument {
	
	private char rawDataType;
	private DataTypeEnum dataType;
	private Object data;

	public Argument(DataTypeEnum dataType){
		this.dataType = dataType;
		this.rawDataType = dataType.getTypeTag();
	}
	
	public Argument(DataTypeEnum dataType, char rawData, Object data){
		this.rawDataType = rawData;
		this.dataType = dataType;
		this.data = data;
	}

	public char getRawDataType(){
		return this.rawDataType;
	}
	
	public void setRawDataType(char rawDataType){
		this.rawDataType = rawDataType;
		this.dataType = DataTypeEnum.parse(rawDataType);
	}

	public char getDataTypeTag(){
		return this.dataType.getTypeTag();
	}

	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}

	public DataTypeEnum getDataType() {
		return dataType;
	}
	
	public void setDataType(DataTypeEnum dataType) {
		this.dataType = dataType;
		this.rawDataType = dataType.getTypeTag();
	}
}
