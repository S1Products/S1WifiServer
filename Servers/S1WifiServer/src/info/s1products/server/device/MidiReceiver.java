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
package info.s1products.server.device;

import info.s1products.server.ServerConstants;
import info.s1products.server.message.Argument;
import info.s1products.server.message.DataType.DataTypeEnum;
import info.s1products.server.message.Message;

import java.util.Properties;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

public class MidiReceiver extends DeviceMessageReceiver 
	implements Receiver {

	public static final String PROP_DEVICE_ID = "DeviceID";

	private MidiDevice  device;
	private Transmitter transmitter;
	
	public MidiReceiver(Properties driverProp) {
		super(driverProp);
	}

	@Override
	public boolean onInitialize(Properties prop) {
		
		try{
			device = MidiDeviceUtil.getMidiInDevice(prop.getProperty(PROP_DEVICE_ID));

			if(device == null){
				
				//TODO:error handling
				return false;
			}
			
			device.open();
			transmitter = device.getTransmitter();
			transmitter.setReceiver(this);
			
		}catch(MidiUnavailableException ex){
			
			//TODO:error handling
			ex.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public boolean onClose() {
		
		transmitter.close();
		device.close();
		
		return true;
	}

// javax.sound.midi.Receiver implementations
	
	@Override
	public void send(MidiMessage midiMessage, long timeStamp) {

		byte[] data = midiMessage.getMessage();
		
		Message message = createMessage(data);
		
		this.getRouter().notify(message);
		this.notifyReceivedEvent(message);
	}

	private Message createMessage(byte[] data){
		
		Message message = new Message();
		message.setAddress(ServerConstants.ADDRESS_MIDI_NOTIFICATION);
		
		//TODO: SysEx		
		Argument arg = new Argument(DataTypeEnum.MidiMessage);
		arg.setData(data);
		message.add(arg);
		
		return message;
	}

	@Override
	public void close() {
	}
}
