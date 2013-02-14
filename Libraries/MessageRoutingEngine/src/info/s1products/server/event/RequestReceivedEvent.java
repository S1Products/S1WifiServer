package info.s1products.server.event;

import info.s1products.server.message.Packet;

import java.util.EventObject;

public class RequestReceivedEvent extends EventObject {

	private static final long serialVersionUID = 911294150839716690L;

	private Packet packet;
	
	public RequestReceivedEvent(Object source, Packet packet) {
		super(source);
		
		this.packet = packet;
	}

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}
}
