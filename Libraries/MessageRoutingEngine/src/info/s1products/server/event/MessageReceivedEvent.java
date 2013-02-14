package info.s1products.server.event;

import info.s1products.server.message.Message;

import java.util.EventObject;

public class MessageReceivedEvent extends EventObject {

	private static final long serialVersionUID = -5925500012391533776L;

	private Message receivedMessage;
	
	public MessageReceivedEvent(Object source, Message message) {
		super(source);
		this.receivedMessage = message;
	}

	public Message getReceivedMessage() {
		return receivedMessage;
	}
}
