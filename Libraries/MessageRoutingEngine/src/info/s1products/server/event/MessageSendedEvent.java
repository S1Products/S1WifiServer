package info.s1products.server.event;

import info.s1products.server.message.Message;

import java.util.EventObject;

public class MessageSendedEvent extends EventObject {

	private static final long serialVersionUID = -8639692837173256639L;

	private Message message;
	
	public MessageSendedEvent(Object source, Message message) {
		super(source);
		this.message = message;
	}

	public Message getMessage() {
		return message;
	}
}
