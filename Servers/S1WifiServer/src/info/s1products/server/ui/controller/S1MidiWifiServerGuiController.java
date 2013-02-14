package info.s1products.server.ui.controller;

import static info.s1products.server.S1MidiConstants.KEY_MIDI_IN_INDEX;
import static info.s1products.server.S1MidiConstants.KEY_MIDI_OUT_INDEX;
import static info.s1products.server.S1MidiConstants.KEY_NOTIFIER_PORT_NO;
import static info.s1products.server.S1MidiConstants.KEY_REQUEST_PORT_NO;
import static info.s1products.server.S1MidiConstants.KEY_USE_MIDI_IN;
import static info.s1products.server.S1MidiConstants.KEY_USE_MIDI_OUT;
import static info.s1products.server.S1MidiConstants.PROP_FILE;
import static info.s1products.server.ServerConstants.DEFAULT_NOTIFIER_PORT;
import static info.s1products.server.ServerConstants.DEFAULT_REQUEST_PORT;
import info.s1products.server.S1WifiServer;
import info.s1products.server.device.MidiSender;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class S1MidiWifiServerGuiController {

	public static final String MONITOR_ON  = "/GreenOn.png";
	public static final String MONITOR_OFF = "/GreenOff.png";

	private S1WifiServer wifiServer = new S1WifiServer();
	
	public S1WifiServer getS1MidiWifiServer(){
		return wifiServer;
	}
	
	public Properties loadSettings(){

		Properties appProp = new Properties();
		
		try{
			InputStream inStream 
				= new BufferedInputStream(new FileInputStream(PROP_FILE));
			
			appProp.load(inStream);
	        inStream.close();
	        
		}catch(FileNotFoundException ex){
			
			System.err.println("Could not load properties. Maybe first boot.");
	        createDefaultProperties(appProp);
			
		}catch(Exception ex){
			
			System.err.println("Could not load properties.");
			ex.printStackTrace();
			
	        createDefaultProperties(appProp);
		}
		
		return appProp;
	}
	
	private void createDefaultProperties(Properties prop){
		
		prop.setProperty(KEY_MIDI_OUT_INDEX, "0");
		prop.setProperty(KEY_MIDI_IN_INDEX,  "0");
		prop.setProperty(KEY_REQUEST_PORT_NO, String.valueOf(DEFAULT_REQUEST_PORT));
		prop.setProperty(KEY_NOTIFIER_PORT_NO, String.valueOf(DEFAULT_NOTIFIER_PORT));
		prop.setProperty(KEY_USE_MIDI_IN,  "false");
		prop.setProperty(KEY_USE_MIDI_OUT, "true");
	}
	
	public void startServer(boolean useOut, String midiOut, 
			boolean useIn, String midiIn, 
			int reqPortNo, int notifyPortNo){

		// Setup midi out
		Properties outProp = new Properties();
		outProp.setProperty(MidiSender.PROP_DEVICE_ID, midiOut);
		wifiServer.setMidiOutProp(outProp);
		wifiServer.setUseMidiOut(useOut);
		
		// Setup midi in
		Properties inProp = new Properties();
		inProp.setProperty(MidiSender.PROP_DEVICE_ID, midiIn);
		wifiServer.setMidiInProp(inProp);
		wifiServer.setUseMidiIn(useIn);
		
		// Setup UDP port
		wifiServer.setRequestPortNo(reqPortNo);
		wifiServer.setNotifierPortNo(notifyPortNo);
		
		if(wifiServer.isWorking()){

			wifiServer.stopServer();
		}
		
		wifiServer.startServer();
	}
	
	public void stopServer(){

		if(wifiServer.isWorking()){

			wifiServer.stopServer();
		}
	}
	
	public void close(){
		
		if(wifiServer.isWorking()){
			
			wifiServer.stopServer();
		}
	}
	
	public void saveSettings(boolean useOut, boolean useIn, 
			int midiOut, int midiIn, 
			String reqPortNo, String notifyPortNo){
		
		Properties appProp = new Properties();
		
		try{
			appProp.setProperty(KEY_USE_MIDI_OUT,     String.valueOf(useOut));
			appProp.setProperty(KEY_USE_MIDI_IN,      String.valueOf(useIn));
			appProp.setProperty(KEY_MIDI_OUT_INDEX,   String.valueOf(midiOut));
			appProp.setProperty(KEY_MIDI_IN_INDEX,    String.valueOf(midiIn));
			appProp.setProperty(KEY_REQUEST_PORT_NO,  reqPortNo);
			appProp.setProperty(KEY_NOTIFIER_PORT_NO, notifyPortNo);
			
			OutputStream outStream 
				= new BufferedOutputStream(new FileOutputStream(PROP_FILE));
			
			appProp.store(outStream, "S1 MIDI Wifi Server setting file");
			
		}catch(Exception ex){
			
			System.err.println("Could not store properties.");
			ex.printStackTrace();
		}
	}
}
