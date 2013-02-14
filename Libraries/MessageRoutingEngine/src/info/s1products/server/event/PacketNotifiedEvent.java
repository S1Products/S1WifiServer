package info.s1products.server.event;

import info.s1products.server.message.Packet;

import java.util.EventObject;

public class PacketNotifiedEvent extends EventObject {

	private static final long serialVersionUID = 6671412858177250660L;

	private Packet packet;
	
	public PacketNotifiedEvent(Object source, Packet packet) {
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
