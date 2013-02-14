package info.s1products.server.notify;

import info.s1products.server.message.Packet;
import info.s1products.server.router.RoutingResult;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class UDPPacketNotifier extends PacketNotifier {

	private int portNo;
	private DatagramSocket socket; 
	
	public UDPPacketNotifier(int portNo){
		this.portNo = portNo;
	}
	
	public void setPortNo(int portNo){
		this.portNo = portNo;
	}
	
	public int getPortNo(){
		return this.portNo;
	}
	
	@Override
	boolean onInitialize() throws Exception {
		
		socket = new DatagramSocket();
		return true;
	}

	@Override
	RoutingResult onNotifyPacket(Packet packet) throws Exception {

		InetSocketAddress address 
			= new InetSocketAddress("255.255.255.255", portNo);

		DatagramPacket dgPacket 
			= new DatagramPacket(packet.getContents(), 
								 packet.getContents().length, 
								 address);

		DatagramSocket socket = new DatagramSocket();
		socket.send(dgPacket);
		socket.close();
		
		RoutingResult result = new RoutingResult();
		result.setSuccess(true);
		
		return result;
	}

	@Override
	boolean onStop() throws Exception {
		
		socket.close();
		return true;
	}
}
