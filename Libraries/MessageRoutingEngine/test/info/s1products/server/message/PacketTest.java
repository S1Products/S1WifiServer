package info.s1products.server.message;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class PacketTest {

	@Test
	public void testPacket() {
		
		byte[] data = new byte[]{1, 2, 3};
		
		Packet packet = new Packet(data);
		assertNotNull(packet);
	}

	@Test
	public void testPacketWithEmptyArray() {
		
		byte[] data = new byte[0];
		
		Packet packet = new Packet(data);
		assertNotNull(packet);
	}

	@Test
	public void testPacketWithNull() {
		
		Packet packet = new Packet(null);
		assertNotNull(packet);
	}

	@Test
	public void testGetContents() {
		
		byte[] data = new byte[]{1, 2, 3};
		
		Packet packet = new Packet(data);
		assertEquals(data, packet.getContents());
	}

	@Test
	public void testGetContentsWithEmptyArray() {
		
		byte[] data = new byte[0];
		
		Packet packet = new Packet(data);
		assertEquals(data, packet.getContents());
	}

	@Test
	public void testGetContentsWithNull() {
		
		byte[] data = null;
		
		Packet packet = new Packet(data);
		assertNull(packet.getContents());
	}

	@Test
	public void testSetContents() {
		
		Packet packet = new Packet(new byte[]{1, 2, 3});
		
		byte[] data = new byte[]{4, 5, 6};
		packet.setContents(data);
		
		assertEquals(data, packet.getContents());
	}

	@Test
	public void testSetContentsWithNull() {
		
		Packet packet = new Packet(new byte[]{1, 2, 3});
		
		packet.setContents(null);
		
		assertNull(packet.getContents());
	}
	
	@Test
	public void testSetContentsFromNull() {
		
		Packet packet = new Packet(null);

		byte[] data = new byte[]{1, 2, 3};

		packet.setContents(data);
		
		assertEquals(data, packet.getContents());
	}

}
