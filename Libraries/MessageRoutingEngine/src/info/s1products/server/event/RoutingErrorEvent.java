package info.s1products.server.event;

import info.s1products.server.router.RoutingResult;

import java.util.EventObject;

public class RoutingErrorEvent extends EventObject {

	private static final long serialVersionUID = -3923893585690179136L;

	private RoutingResult result;

	public RoutingErrorEvent(Object source, RoutingResult result) {
		super(source);
		this.result = result;
	}

	public RoutingResult getResult() {
		return result;
	}

	public void setResult(RoutingResult result) {
		this.result = result;
	}
	
}
