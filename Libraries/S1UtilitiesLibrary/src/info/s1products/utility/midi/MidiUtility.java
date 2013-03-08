/*******************************************************************************
 * Copyright (c) 2013 Shuichi Miura.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Shuichi Miura - initial API and implementation
 ******************************************************************************/
package info.s1products.utility.midi;

/**
 * MIDI utility class
 * @author Shuichi Miura
 */
public class MidiUtility {

	public static final int INVALID_MESSAGE_LENGTH = 0;
	public static final int SYS_EX_LENGTH = -1;
	
	/**
	 * Check specified byte array is valid MIDI data with MIDI command and data length.
	 * @param midiData MIDI data
	 * @return True: Valid message, False: Invalid message
	 */
	public static boolean isValidMidiMessage(byte[] midiData){

		if(midiData == null || midiData.length == 0){
			
			return false;
		}
		
		int length = getMidiMessageLength(midiData[0]);

		if(length == SYS_EX_LENGTH){
			return true;
			
		}else if(midiData.length == length){
			return true;
		}
		
		return false;
	}

	/**
	 * Get MIDI message length for MIDI Specification 
	 * @param midiData raw MIDI message
	 * @return message length (-1: Unlimited, 0: Not MIDI message)
	 */
	public static int getMidiMessageLength(byte[] midiData){
		
		if(isValidMidiMessage(midiData) == false){
			return 0;
		}

		return getMidiMessageLength(midiData[0]);
	}
	
	/**
	 * Get MIDI message length for MIDI Specification 
	 * @param firstByte first byte of MIDI message 
	 * @return message length (-1: Unlimited, 0: Not MIDI message)
	 */
	public static int getMidiMessageLength(byte firstByte){

		int command = firstByte & 0x0F;
		switch(command){

			case 0x80:
				return 2;
				
			case 0x90:
				return 2;
				
			case 0xA0:
				return 2;
				
			case 0xB0:
				return 2;
				
			case 0xC0:
				return 1;
				
			case 0xD0:
				return 1;
				
			case 0xE0:
				return 2;
				
			case 0xF0:
				return -1;
		}
		
		return 0;
	}
	
}
