package info.s1products.server.message;

/**
 * 
 * @author Shuichi Miura
 */
public class Packet {

	private byte[] contents;
	
	public Packet(byte[] contents){
		this.contents = contents;
	}
	
	public byte[] getContents() {
		return contents;
	}
	
	public void setContents(byte[] contents) {
		this.contents = contents;
	}
}
