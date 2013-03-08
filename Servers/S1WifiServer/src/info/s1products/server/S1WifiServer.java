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
package info.s1products.server;

import info.s1products.server.ServerBase;
import info.s1products.server.ServerConstants;
import info.s1products.server.converter.OSCRequestConverter;
import info.s1products.server.converter.RequestConverter;
import info.s1products.server.converter.SimpleMidiNotificationConverter;
import info.s1products.server.converter.SimpleMidiRequestConverter;
import info.s1products.server.device.DeviceMessageReceiver;
import info.s1products.server.device.DeviceMessageSender;
import info.s1products.server.device.MidiReceiver;
import info.s1products.server.device.MidiSender;
import info.s1products.server.event.MessageReceivedListener;
import info.s1products.server.event.MessageSentListener;
import info.s1products.server.event.PacketNotifiedListener;
import info.s1products.server.event.RequestReceivedListener;
import info.s1products.server.event.RoutingErrorListener;
import info.s1products.server.notify.UDPPacketNotifier;
import info.s1products.server.request.RequestHandler;
import info.s1products.server.request.UDPRequestHandler;
import info.s1products.server.router.NotificationPacketRouter;
import info.s1products.server.router.NotificationPlan;
import info.s1products.server.router.RequestPacketRouter;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class S1WifiServer extends ServerBase {

	private boolean useMidiOut = true;
	private boolean useMidiIn  = false;

	private int requestPortNo  = ServerConstants.DEFAULT_REQUEST_PORT;
	private int notifierPortNo = ServerConstants.DEFAULT_NOTIFIER_PORT;

	private Properties midiOutProp = new Properties();
	private Properties midiInProp = new Properties();
	
	private MessageSentListener   midiOutListener;
	private MessageReceivedListener midiInListener;
	private RequestReceivedListener requestListener;
	private PacketNotifiedListener  sendedListener;
	private RoutingErrorListener    errorListener;
	
	public static void main(String[] args){

		new S1WifiServer().startServer();

		while(true){

			try{ Thread.sleep(1000); }catch(Exception ex){}
		}
	}

// Properties	
	
	public int getRequestPortNo() {
		return requestPortNo;
	}

	public void setRequestPortNo(int requestPortNo) {
		this.requestPortNo = requestPortNo;
	}
	
	public int getNotifierPortNo() {
		return notifierPortNo;
	}

	public void setNotifierPortNo(int notifierPortNo) {
		this.notifierPortNo = notifierPortNo;
	}

	public Properties getMidiOutProp() {
		return midiOutProp;
	}

	public void setMidiOutProp(Properties midiOutProp) {
		this.midiOutProp = midiOutProp;
	}

	public Properties getMidiInProp() {
		return midiInProp;
	}

	public void setMidiInProp(Properties midiInProp) {
		this.midiInProp = midiInProp;
	}

	public boolean isUseMidiOut() {
		return useMidiOut;
	}

	public void setUseMidiOut(boolean useMidiOut) {
		this.useMidiOut = useMidiOut;
	}

	public boolean isUseMidiIn() {
		return useMidiIn;
	}

	public void setUseMidiIn(boolean useMidiIn) {
		this.useMidiIn = useMidiIn;
	}

	public void setMidiOutListener(MessageSentListener listener){
		this.midiOutListener = listener;
	}

	public MessageSentListener getMidiOutListener() {
		return midiOutListener;
	}

	public MessageReceivedListener getMidiInListener() {
		return midiInListener;
	}

	public void setMidiInListener(MessageReceivedListener midiInListener) {
		this.midiInListener = midiInListener;
	}

	public RequestReceivedListener getRequestListener() {
		return requestListener;
	}

	public void setRequestListener(RequestReceivedListener requestListener) {
		this.requestListener = requestListener;
	}

	public PacketNotifiedListener getPacketNotifiedListener() {
		return sendedListener;
	}

	public void setPacketNotifiedListener(PacketNotifiedListener sendedListener) {
		this.sendedListener = sendedListener;
	}

	public RoutingErrorListener getErrorListener() {
		return errorListener;
	}

	public void setErrorListener(RoutingErrorListener errorListener) {
		this.errorListener = errorListener;
	}

// Methods	

	@Override
	protected void prepareServer() {
	}

	@Override
	protected void onStartRequestRouter(RequestPacketRouter router) {
		router.addListener(errorListener);
	}

	@Override
	protected void onStartNotificationRouter(NotificationPacketRouter router) {
		router.addListener(errorListener);
	}
	
// For MIDI out	
	
	@Override
	public List<RequestHandler> createRequestHandlerList() {

		List<RequestHandler> handlerList = new ArrayList<RequestHandler>();

		UDPRequestHandler udpHandler 
			= new UDPRequestHandler(requestPortNo);
		
		udpHandler.addListener(requestListener);
		handlerList.add(udpHandler);
		
		return handlerList;
	}

	@Override
	public List<RequestConverter> createRequestConverterList() {
		
		List<RequestConverter> converterList = new ArrayList<RequestConverter>();
		
		SimpleMidiRequestConverter simpleConverter = new SimpleMidiRequestConverter();
		converterList.add(simpleConverter);
		
		OSCRequestConverter oscConverter = new OSCRequestConverter();
		converterList.add(oscConverter);
		
		return converterList;	
	}

	@Override
	public List<DeviceMessageSender> createDeviceMessageSenderList() {
		
		List<DeviceMessageSender> senderList 
			= new ArrayList<DeviceMessageSender>();
		
		if(useMidiOut){
			
			MidiSender sender = new MidiSender(midiOutProp);
			sender.addListener(midiOutListener);
			senderList.add(sender);
		}
		
		return senderList;
	}

// For MIDI in	
	
	@Override
	public List<NotificationPlan> createNotificationPlanList() {
		
		SimpleMidiNotificationConverter converter 
			= new SimpleMidiNotificationConverter();

		UDPPacketNotifier notifier = new UDPPacketNotifier(notifierPortNo);
		notifier.addListener(sendedListener);

		NotificationPlan plan 
			= new NotificationPlan(ServerConstants.ADDRESS_MIDI_NOTIFICATION, 
					converter, notifier);

		List<NotificationPlan> planList = new ArrayList<NotificationPlan>();
		planList.add(plan);
		
		return planList;
	}

	@Override
	public List<DeviceMessageReceiver> createDeviceMessageReceiverList() {
		
		List<DeviceMessageReceiver> receiverList
			= new ArrayList<DeviceMessageReceiver>();
		
		if(useMidiIn){
			
			MidiReceiver receiver = new MidiReceiver(midiInProp);
			receiver.addListener(midiInListener);
			receiverList.add(receiver);
		}

		return receiverList;
	}

	@Override
	protected void cleanupServer() {
	}
}
