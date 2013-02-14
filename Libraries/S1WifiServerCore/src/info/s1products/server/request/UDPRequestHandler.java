package info.s1products.server.request;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * 
 * @author Shuichi Miura
 */
public class UDPRequestHandler extends RequestHandler {

	public static final int MAX_PACKET_SIZE = 20480;
	
	private int portNo;
	private DatagramSocket socket;
	private int maxPacketSize = MAX_PACKET_SIZE;
	
	public UDPRequestHandler(int portNo){
		this.portNo = portNo;
	}
	
	public int getPortNo() {
		return portNo;
	}

	public void setPortNo(int portNo) {
		this.portNo = portNo;
	}

	public int getMaxPacketSize() {
		return maxPacketSize;
	}

	public void setMaxPacketSize(int maxPacketSize) {
		this.maxPacketSize = maxPacketSize;
	}

	@Override
	public boolean onInitialize() throws Exception{
		
		try{
			socket = new DatagramSocket(portNo);
			return true;
			
		}catch(SocketException sockEx){
			//TODO: error handle
			return false;
		}
	}

	@Override
	public byte[] waitClientRequest() throws Exception {
		
		byte[] data = new byte[maxPacketSize];
		
		DatagramPacket packet = new DatagramPacket(data, maxPacketSize);
		socket.receive(packet);
		
		byte[] receivedData = new byte[packet.getLength()];
		System.arraycopy(data, 0, receivedData, 0, packet.getLength());
		
		return receivedData;
	}
	
	@Override
	public boolean onStop() throws Exception {
		
		socket.close();
		return true;
	}
	
}
