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
package info.s1products.server.core.message.converter;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import info.s1products.server.converter.OSCRequestConverter;
import info.s1products.server.converter.RequestConverter;
import info.s1products.server.message.DataType.DataTypeEnum;
import info.s1products.server.message.Message;
import info.s1products.server.message.Packet;

import java.awt.Color;
import java.util.List;

import org.junit.Test;

public class OSCConverterTest {

	private String replaceSpaceToNull(String data){
		return data.replace(" ", "\0");
	}

// Packet to message	
	
	@Test
	public void testOSCPacketWithSingleIntMessage() {
		
		String command = replaceSpaceToNull("/A/B/C  ,i     \1");
		byte[] sourceData = command.getBytes();

		Packet packet = new Packet(sourceData);

		byte[] data = packet.getContents();
		assertArrayEquals(sourceData, data);
		
		RequestConverter converter = new OSCRequestConverter();
		
		List<Message> messageList = converter.convertMessageList(packet);
		Message message = messageList.get(0);
		
		assertSingleArgMessage(message, "/A/B/C", DataTypeEnum.Int32, 'i', 'i');
		assertEquals(1, message.getArgumentList().get(0).getData());
	}

	@Test
	public void testOSCPacketWithSingleFloatMessage() throws Exception{
		
		byte[] sourceData = new byte[]{47, 65, 47, 66, 47, 67, 0, 0, 44, 102, 0, 0, 63, -116, -52, -51};

		Packet packet = new Packet(sourceData);

		byte[] data = packet.getContents();
		assertArrayEquals(sourceData, data);
		
		RequestConverter converter = new OSCRequestConverter();
		
		List<Message> messageList = converter.convertMessageList(packet);
		Message message = messageList.get(0);
		
		assertSingleArgMessage(message, "/A/B/C", DataTypeEnum.Float32, 'f', 'f');
		assertEquals(1.1f, message.getArgumentList().get(0).getData());
	}

	@Test
	public void testOSCPacketWithSingleLongMessage() {
		
		String command = replaceSpaceToNull("/A/B/C  ,l         \1");
		byte[] sourceData = command.getBytes();

		Packet packet = new Packet(sourceData);

		byte[] data = packet.getContents();
		assertArrayEquals(sourceData, data);
		
		RequestConverter converter = new OSCRequestConverter();
		
		List<Message> messageList = converter.convertMessageList(packet);
		Message message = messageList.get(0);
		
		assertSingleArgMessage(message, "/A/B/C", DataTypeEnum.Long64, 'l', 'l');
		assertEquals(1L, message.getArgumentList().get(0).getData());
	}

	@Test
	public void testOSCPacketWithSingleStringMessage() {
		
		String command = replaceSpaceToNull("/A/B/C  ,s  ABC ");
		byte[] sourceData = command.getBytes();

		Packet packet = new Packet(sourceData);

		byte[] data = packet.getContents();
		assertArrayEquals(sourceData, data);
		
		RequestConverter converter = new OSCRequestConverter();
		
		List<Message> messageList = converter.convertMessageList(packet);
		Message message = messageList.get(0);
		
		assertSingleArgMessage(message, "/A/B/C", DataTypeEnum.String, 's', 's');
		assertEquals("ABC", message.getArgumentList().get(0).getData());
	}

	@Test
	public void testOSCPacketWithSingleBlobMessage() {
		
		String command = replaceSpaceToNull("/A/B/C  ,b     \6\1\2\3\4\5\6\7");
		byte[] sourceData = command.getBytes();

		Packet packet = new Packet(sourceData);

		byte[] data = packet.getContents();
		assertArrayEquals(sourceData, data);
		
		RequestConverter converter = new OSCRequestConverter();
		
		List<Message> messageList = converter.convertMessageList(packet);
		Message message = messageList.get(0);
		
		assertSingleArgMessage(message, "/A/B/C", DataTypeEnum.Blob, 'b', 'b');
		
		byte[] actualData = (byte[])message.getArgumentList().get(0).getData();
		assertArrayEquals("\1\2\3\4\5\6".getBytes(), actualData);
	}

	@Test
	public void testOSCPacketWithSingleBigEndianMessage() {
		
		String command = replaceSpaceToNull("/A/B/C  ,h         \1");
		byte[] sourceData = command.getBytes();

		Packet packet = new Packet(sourceData);

		byte[] data = packet.getContents();
		assertArrayEquals(sourceData, data);
		
		RequestConverter converter = new OSCRequestConverter();
		
		List<Message> messageList = converter.convertMessageList(packet);
		Message message = messageList.get(0);
		
		assertSingleArgMessage(message, "/A/B/C", DataTypeEnum.BigEndian64, 'h', 'h');
		assertEquals(1L, message.getArgumentList().get(0).getData());
	}

	@Test
	public void testOSCPacketWithSingleTimeTagMessage() {
		
		String command = replaceSpaceToNull("/A/B/C  ,t         \1");
		byte[] sourceData = command.getBytes();

		Packet packet = new Packet(sourceData);

		byte[] data = packet.getContents();
		assertArrayEquals(sourceData, data);
		
		RequestConverter converter = new OSCRequestConverter();
		
		List<Message> messageList = converter.convertMessageList(packet);
		Message message = messageList.get(0);
		
		assertSingleArgMessage(message, "/A/B/C", DataTypeEnum.Timetag, 't', 't');
		assertEquals(1L, message.getArgumentList().get(0).getData());
	}

	@Test
	public void testOSCPacketWithSingleDoubleMessage() {
		
		byte[] sourceData = new byte[] {47, 65, 47, 66, 47, 67, 0, 0, 44, 100, 0, 0, 63, -15, -103, -103, -103, -103, -103, -102};

		Packet packet = new Packet(sourceData);

		byte[] data = packet.getContents();
		assertArrayEquals(sourceData, data);
		
		RequestConverter converter = new OSCRequestConverter();
		
		List<Message> messageList = converter.convertMessageList(packet);
		Message message = messageList.get(0);
		
		assertSingleArgMessage(message, "/A/B/C", DataTypeEnum.Double64, 'd', 'd');
		assertEquals(Double.class, message.getArgumentList().get(0).getData().getClass());
	}

	@Test
	public void testOSCPacketWithSingleSymbolMessage() {
		
		String command = replaceSpaceToNull("/A/B/C  ,S  ABC ");
		byte[] sourceData = command.getBytes();

		Packet packet = new Packet(sourceData);

		byte[] data = packet.getContents();
		assertArrayEquals(sourceData, data);
		
		RequestConverter converter = new OSCRequestConverter();
		
		List<Message> messageList = converter.convertMessageList(packet);
		Message message = messageList.get(0);
		
		assertSingleArgMessage(message, "/A/B/C", DataTypeEnum.Symbol, 'S', 'S');
		assertEquals("ABC", message.getArgumentList().get(0).getData());
	}

	@Test
	public void testOSCPacketWithSingleCharacterMessage() {
		
		String command = replaceSpaceToNull("/A/B/C  ,c     A");
		byte[] sourceData = command.getBytes();

		Packet packet = new Packet(sourceData);

		byte[] data = packet.getContents();
		assertArrayEquals(sourceData, data);
		
		RequestConverter converter = new OSCRequestConverter();
		
		List<Message> messageList = converter.convertMessageList(packet);
		Message message = messageList.get(0);
		
		assertSingleArgMessage(message, "/A/B/C", DataTypeEnum.Character, 'c', 'c');
		assertEquals('A', message.getArgumentList().get(0).getData());
	}

	@Test
	public void testOSCPacketWithSingleColorMessage() {
		
		String command = replaceSpaceToNull("/A/B/C  ,r  \1\2\3\4");
		byte[] sourceData = command.getBytes();

		Packet packet = new Packet(sourceData);

		byte[] data = packet.getContents();
		assertArrayEquals(sourceData, data);
		
		RequestConverter converter = new OSCRequestConverter();
		
		List<Message> messageList = converter.convertMessageList(packet);
		Message message = messageList.get(0);
		
		assertSingleArgMessage(message, "/A/B/C", DataTypeEnum.Color, 'r', 'r');
		
		Color actual = (Color)message.getArgumentList().get(0).getData();
		assertEquals(1, actual.getRed());
		assertEquals(2, actual.getGreen());
		assertEquals(3, actual.getBlue());
		assertEquals(4, actual.getAlpha());
	}

	@Test
	public void testOSCPacketWithSingleMidiMessage() {
		
		String command = replaceSpaceToNull("/A/B/C  ,m  \1\2\3\4");
		byte[] sourceData = command.getBytes();

		Packet packet = new Packet(sourceData);

		byte[] data = packet.getContents();
		assertArrayEquals(sourceData, data);
		
		RequestConverter converter = new OSCRequestConverter();
		
		List<Message> messageList = converter.convertMessageList(packet);
		Message message = messageList.get(0);
		
		assertSingleArgMessage(message, "/A/B/C", DataTypeEnum.MidiMessage, 'm', 'm');
		assertArrayEquals("\1\2\3\4".getBytes(), (byte[])message.getArgumentList().get(0).getData());
	}

	@Test
	public void testOSCPacketWithSingleTrueMessage() {
		
		String command = replaceSpaceToNull("/A/B/C  ,T  ");
		byte[] sourceData = command.getBytes();

		Packet packet = new Packet(sourceData);

		byte[] data = packet.getContents();
		assertArrayEquals(sourceData, data);
		
		RequestConverter converter = new OSCRequestConverter();
		
		List<Message> messageList = converter.convertMessageList(packet);
		Message message = messageList.get(0);
		
		assertSingleArgMessage(message, "/A/B/C", DataTypeEnum.True, 'T', 'T');
		assertEquals(true, message.getArgumentList().get(0).getData());
	}

	@Test
	public void testOSCPacketWithSingleFalseMessage() {
		
		String command = replaceSpaceToNull("/A/B/C  ,F  ");
		byte[] sourceData = command.getBytes();

		Packet packet = new Packet(sourceData);

		byte[] data = packet.getContents();
		assertArrayEquals(sourceData, data);
		
		RequestConverter converter = new OSCRequestConverter();
		
		List<Message> messageList = converter.convertMessageList(packet);
		Message message = messageList.get(0);
		
		assertSingleArgMessage(message, "/A/B/C", DataTypeEnum.False, 'F', 'F');
		assertEquals(false, message.getArgumentList().get(0).getData());
	}

	@Test
	public void testOSCPacketWithSingleNilMessage() {
		
		String command = replaceSpaceToNull("/A/B/C  ,N  ");
		byte[] sourceData = command.getBytes();

		Packet packet = new Packet(sourceData);

		byte[] data = packet.getContents();
		assertArrayEquals(sourceData, data);
		
		RequestConverter converter = new OSCRequestConverter();
		
		List<Message> messageList = converter.convertMessageList(packet);
		Message message = messageList.get(0);
		
		assertSingleArgMessage(message, "/A/B/C", DataTypeEnum.Nil, 'N', 'N');
		assertNull(message.getArgumentList().get(0).getData());
	}

	@Test
	public void testOSCPacketWithSingleInifiteMessage() {
		
		String command = replaceSpaceToNull("/A/B/C  ,I  ");
		byte[] sourceData = command.getBytes();

		Packet packet = new Packet(sourceData);

		byte[] data = packet.getContents();
		assertArrayEquals(sourceData, data);
		
		RequestConverter converter = new OSCRequestConverter();
		
		List<Message> messageList = converter.convertMessageList(packet);
		Message message = messageList.get(0);
		
		assertSingleArgMessage(message, "/A/B/C", DataTypeEnum.Infinite, 'I', 'I');
		assertNull(message.getArgumentList().get(0).getData());
	}

	@Test
	public void testOSCPacketWithUndefinedMessage() {
		
		String command = replaceSpaceToNull("/A/B/C  ,z     \1 \2");
		byte[] sourceData = command.getBytes();

		Packet packet = new Packet(sourceData);

		byte[] data = packet.getContents();
		assertArrayEquals(sourceData, data);
		
		RequestConverter converter = new OSCRequestConverter();
		
		List<Message> messageList = converter.convertMessageList(packet);
		Message message = messageList.get(0);
		
		assertSingleArgMessage(message, "/A/B/C", DataTypeEnum.Undefined, '\0', 'z');
		assertArrayEquals("\0\0\0\1\0\2".getBytes(), (byte[])message.getArgumentList().get(0).getData());
	}

	private void assertSingleArgMessage(Message message, String address, DataTypeEnum type, char tag, char rawTag){
		
		assertNotNull(message);
		assertEquals(address, message.getAddress());
		assertEquals(1, message.getArgumentList().size());
		assertEquals(type, message.getArgumentList().get(0).getDataType());
		assertEquals(tag, message.getArgumentList().get(0).getDataTypeTag());
		assertEquals(rawTag, message.getArgumentList().get(0).getRawDataType());
	}
	
	@Test
	public void testOSCPacketWithTwoIntMessage() {
		
		String command = replaceSpaceToNull("/A/B/C  ,ii    \1   \1");
		byte[] sourceData = command.getBytes();

		Packet packet = new Packet(sourceData);

		byte[] data = packet.getContents();
		assertArrayEquals(sourceData, data);
		
		RequestConverter converter = new OSCRequestConverter();
		
		List<Message> messageList = converter.convertMessageList(packet);
		Message message = messageList.get(0);
		
		assertTwoArgMessage(message, "/A/B/C", DataTypeEnum.Int32, 'i', 'i');
		assertEquals(1, message.getArgumentList().get(0).getData());
		assertEquals(1, message.getArgumentList().get(1).getData());
	}

	private void assertTwoArgMessage(Message message, String address, DataTypeEnum type, char tag, char rawTag){
		
		assertNotNull(message);
		assertEquals(address, message.getAddress());
		assertEquals(2, message.getArgumentList().size());
		assertEquals(type, message.getArgumentList().get(0).getDataType());
		assertEquals(tag, message.getArgumentList().get(0).getDataTypeTag());
		assertEquals(rawTag, message.getArgumentList().get(0).getRawDataType());
		
		assertEquals(type, message.getArgumentList().get(1).getDataType());
		assertEquals(tag, message.getArgumentList().get(1).getDataTypeTag());
		assertEquals(rawTag, message.getArgumentList().get(1).getRawDataType());
	}

// Message to Packet	

/*	
	
	@Test
	public void testMessageWithOneIntPacket() {

		Message message = new Message();
		message.setAddress("/A/B/C");
		
		Argument arg = new Argument(DataTypeEnum.Int32);
		arg.setData(1);
		message.add(arg);
		
		String command = replaceSpaceToNull("/A/B/C  ,i     \1");
		byte[] expectData = command.getBytes();

		RequestConverter converter = new OSCRequestConverter();
		Packet packet = converter.convertPacket(message);

		assertNotNull(packet);
		assertArrayEquals(expectData, packet.getContents());
	}

	@Test
	public void testMessageWithOneFloatPacket() {

		Message message = new Message();
		message.setAddress("/A/B/C");
		
		Argument arg = new Argument(DataTypeEnum.Float32);
		arg.setData(1.1f);
		message.add(arg);
		
		byte[] expectData = new byte[]{47, 65, 47, 66, 47, 67, 0, 0, 44, 102, 0, 0, 63, -116, -52, -51};

		RequestConverter converter = new OSCRequestConverter();
		Packet packet = converter.convertPacket(message);

		assertNotNull(packet);
		assertArrayEquals(expectData, packet.getContents());
	}
	
	@Test
	public void testMessageWithOneLongPacket() {

		Message message = new Message();
		message.setAddress("/A/B/C");
		
		Argument arg = new Argument(DataTypeEnum.Long64);
		arg.setData(1L);
		message.add(arg);
		
		String command = replaceSpaceToNull("/A/B/C  ,l         \1");
		byte[] expectData = command.getBytes();

		RequestConverter converter = new OSCRequestConverter();
		Packet packet = converter.convertPacket(message);

		assertNotNull(packet);
		assertArrayEquals(expectData, packet.getContents());
	}

	@Test
	public void testMessageWithOneStringPacket() {

		Message message = new Message();
		message.setAddress("/A/B/C");
		
		Argument arg = new Argument(DataTypeEnum.String);
		arg.setData("ABC");
		message.add(arg);
		
		String command = replaceSpaceToNull("/A/B/C  ,s  ABC ");
		byte[] expectData = command.getBytes();

		RequestConverter converter = new OSCRequestConverter();
		Packet packet = converter.convertPacket(message);

		assertNotNull(packet);
		assertArrayEquals(expectData, packet.getContents());
	}

	@Test
	public void testMessageWithOneBlobPacket() {

		Message message = new Message();
		message.setAddress("/A/B/C");
		
		Argument arg = new Argument(DataTypeEnum.Blob);
		arg.setData("\1\2\3\4\5\6".getBytes());
		message.add(arg);
		
		String command = replaceSpaceToNull("/A/B/C  ,b     \6\1\2\3\4\5\6");
		byte[] expectData = command.getBytes();

		RequestConverter converter = new OSCRequestConverter();
		Packet packet = converter.convertPacket(message);

		assertNotNull(packet);
		assertArrayEquals(expectData, packet.getContents());
	}

	@Test
	public void testMessageWithOneBigEndianPacket() {

		Message message = new Message();
		message.setAddress("/A/B/C");
		
		Argument arg = new Argument(DataTypeEnum.BigEndian64);
		arg.setData(1L);
		message.add(arg);
		
		String command = replaceSpaceToNull("/A/B/C  ,h         \1");
		byte[] expectData = command.getBytes();

		RequestConverter converter = new OSCRequestConverter();
		Packet packet = converter.convertPacket(message);

		assertNotNull(packet);
		assertArrayEquals(expectData, packet.getContents());
	}

	@Test
	public void testMessageWithOneTimetagPacket() {

		Message message = new Message();
		message.setAddress("/A/B/C");
		
		Argument arg = new Argument(DataTypeEnum.Timetag);
		arg.setData(1L);
		message.add(arg);
		
		String command = replaceSpaceToNull("/A/B/C  ,t         \1");
		byte[] expectData = command.getBytes();

		RequestConverter converter = new OSCRequestConverter();
		Packet packet = converter.convertPacket(message);

		assertNotNull(packet);
		assertArrayEquals(expectData, packet.getContents());
	}

	@Test
	public void testMessageWithOneDoublePacket() {

		Message message = new Message();
		message.setAddress("/A/B/C");
		
		Argument arg = new Argument(DataTypeEnum.Double64);
		arg.setData(1.1d);
		message.add(arg);
		
		byte[] expectData = new byte[] {47, 65, 47, 66, 47, 67, 0, 0, 44, 100, 0, 0, 63, -15, -103, -103, -103, -103, -103, -102};

		RequestConverter converter = new OSCRequestConverter();
		Packet packet = converter.convertPacket(message);

		assertNotNull(packet);
		assertArrayEquals(expectData, packet.getContents());
	}

	@Test
	public void testMessageWithOneSymbolPacket() {

		Message message = new Message();
		message.setAddress("/A/B/C");
		
		Argument arg = new Argument(DataTypeEnum.Symbol);
		arg.setData("ABC");
		message.add(arg);
		
		String command = replaceSpaceToNull("/A/B/C  ,S  ABC ");
		byte[] expectData = command.getBytes();

		RequestConverter converter = new OSCRequestConverter();
		Packet packet = converter.convertPacket(message);

		assertNotNull(packet);
		assertArrayEquals(expectData, packet.getContents());
	}

	@Test
	public void testMessageWithOneCharacterPacket() {

		Message message = new Message();
		message.setAddress("/A/B/C");
		
		Argument arg = new Argument(DataTypeEnum.Character);
		arg.setData('A');
		message.add(arg);
		
		String command = replaceSpaceToNull("/A/B/C  ,c  A   ");
		byte[] expectData = command.getBytes();

		RequestConverter converter = new OSCRequestConverter();
		Packet packet = converter.convertPacket(message);

		assertNotNull(packet);
		assertArrayEquals(expectData, packet.getContents());
	}

	@Test
	public void testMessageWithOneColorPacket() {

		Message message = new Message();
		message.setAddress("/A/B/C");
		
		Argument arg = new Argument(DataTypeEnum.Color);
		arg.setData(new Color(1, 1, 1, 1));
		message.add(arg);
		
		String command = replaceSpaceToNull("/A/B/C  ,r  \1\1\1\1");
		byte[] expectData = command.getBytes();

		RequestConverter converter = new OSCRequestConverter();
		Packet packet = converter.convertPacket(message);

		assertNotNull(packet);
		assertArrayEquals(expectData, packet.getContents());
	}

	@Test
	public void testMessageWithOneMidiMessagePacket() {

		Message message = new Message();
		message.setAddress("/A/B/C");
		
		Argument arg = new Argument(DataTypeEnum.MidiMessage);
		arg.setData(new byte[]{1, 1, 1, 1});
		message.add(arg);
		
		String command = replaceSpaceToNull("/A/B/C  ,m  \1\1\1\1");
		byte[] expectData = command.getBytes();

		RequestConverter converter = new OSCRequestConverter();
		Packet packet = converter.convertPacket(message);

		assertNotNull(packet);
		assertArrayEquals(expectData, packet.getContents());
	}

	@Test
	public void testMessageWithOneTruePacket() {

		Message message = new Message();
		message.setAddress("/A/B/C");
		
		Argument arg = new Argument(DataTypeEnum.True);
		arg.setData(true);
		message.add(arg);
		
		String command = replaceSpaceToNull("/A/B/C  ,T  ");
		byte[] expectData = command.getBytes();

		RequestConverter converter = new OSCRequestConverter();
		Packet packet = converter.convertPacket(message);

		assertNotNull(packet);
		assertArrayEquals(expectData, packet.getContents());
	}

	@Test
	public void testMessageWithOneFalsePacket() {

		Message message = new Message();
		message.setAddress("/A/B/C");
		
		Argument arg = new Argument(DataTypeEnum.False);
		arg.setData(false);
		message.add(arg);
		
		String command = replaceSpaceToNull("/A/B/C  ,F  ");
		byte[] expectData = command.getBytes();

		RequestConverter converter = new OSCRequestConverter();
		Packet packet = converter.convertPacket(message);

		assertNotNull(packet);
		assertArrayEquals(expectData, packet.getContents());
	}

	@Test
	public void testMessageWithOneNilPacket() {

		Message message = new Message();
		message.setAddress("/A/B/C");
		
		Argument arg = new Argument(DataTypeEnum.Nil);
		arg.setData(null);
		message.add(arg);
		
		String command = replaceSpaceToNull("/A/B/C  ,N  ");
		byte[] expectData = command.getBytes();

		RequestConverter converter = new OSCRequestConverter();
		Packet packet = converter.convertPacket(message);

		assertNotNull(packet);
		assertArrayEquals(expectData, packet.getContents());
	}

	@Test
	public void testMessageWithOneInfinitePacket() {

		Message message = new Message();
		message.setAddress("/A/B/C");
		
		Argument arg = new Argument(DataTypeEnum.Infinite);
		arg.setData(null);
		message.add(arg);
		
		String command = replaceSpaceToNull("/A/B/C  ,I  ");
		byte[] expectData = command.getBytes();

		RequestConverter converter = new OSCRequestConverter();
		Packet packet = converter.convertPacket(message);

		assertNotNull(packet);
		assertArrayEquals(expectData, packet.getContents());
	}

	@Test
	public void testMessageWithOneUndefinedPacket() {

		Message message = new Message();
		message.setAddress("/A/B/C");
		
		Argument arg = new Argument(DataTypeEnum.Undefined);
		arg.setRawDataType('z');
		arg.setData(new byte[]{1, 2, 3, 4, 5});
		message.add(arg);
		
		String command = replaceSpaceToNull("/A/B/C  ,z  \1\2\3\4\5");
		byte[] expectData = command.getBytes();

		RequestConverter converter = new OSCRequestConverter();
		Packet packet = converter.convertPacket(message);

		assertNotNull(packet);
		assertArrayEquals(expectData, packet.getContents());
	}
*/
}
