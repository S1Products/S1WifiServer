package info.s1products.server.message;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import info.s1products.server.message.DataType.DataTypeEnum;

import org.junit.Test;

public class ArgumentTest {

	@Test
	public void testArgumentDataTypeEnum() {
		
		Argument arg = new Argument(DataTypeEnum.MidiMessage);

		assertNotNull(arg);
	}

	@Test
	public void testArgumentDataTypeEnumCharObject() {
		
		String obj = "Test";
		
		Argument arg = new Argument(DataTypeEnum.Undefined, 'X', obj);

		assertNotNull(arg);
		
		assertEquals(DataTypeEnum.Undefined, arg.getDataType());
		assertEquals(DataTypeEnum.Undefined.getTypeTag(), arg.getDataTypeTag());
		assertEquals('X', arg.getRawDataType());
		assertEquals(obj, arg.getData());
	}

	@Test
	public void testGetRawDataType() {
		
		Argument arg = new Argument(DataTypeEnum.Undefined, 'X', null);
		assertEquals('X', arg.getRawDataType());
	}

	@Test
	public void testSetRawDataType() {
		
		Argument arg = new Argument(DataTypeEnum.Blob);
		arg.setRawDataType('X');
		
		assertEquals('X', arg.getRawDataType());
		assertEquals(DataTypeEnum.Undefined, arg.getDataType());
		assertEquals(DataTypeEnum.Undefined.getTypeTag(), arg.getDataTypeTag());
		
		arg.setRawDataType(DataTypeEnum.MidiMessage.getTypeTag());

		assertEquals(DataTypeEnum.MidiMessage.getTypeTag(), arg.getRawDataType());
		assertEquals(DataTypeEnum.MidiMessage, arg.getDataType());
		assertEquals(DataTypeEnum.MidiMessage.getTypeTag(), arg.getDataTypeTag());
	}

	@Test
	public void testGetDataTypeTag() {
	
		Argument arg = new Argument(DataTypeEnum.MidiMessage);

		assertEquals(DataTypeEnum.MidiMessage.getTypeTag(), arg.getDataTypeTag());
	}

	@Test
	public void testGetData() {

		String obj = "Test";
		
		Argument arg = new Argument(DataTypeEnum.Undefined, 'X', obj);

		assertEquals(obj, arg.getData());
	}

	@Test
	public void testSetData() {
		
		byte[] data = new byte[]{1, 2, 3};
		
		Argument arg = new Argument(DataTypeEnum.MidiMessage);
		arg.setData(data);

		assertEquals(data, arg.getData());
	}

	@Test
	public void testGetDataType() {
		
		Argument arg = new Argument(DataTypeEnum.MidiMessage);

		assertEquals(DataTypeEnum.MidiMessage, arg.getDataType());
	}

	@Test
	public void testSetDataType() {
		
		Argument arg = new Argument(DataTypeEnum.MidiMessage);
		arg.setDataType(DataTypeEnum.Blob);
		
		assertEquals(DataTypeEnum.Blob, arg.getDataType());
		assertEquals(DataTypeEnum.Blob.getTypeTag(), arg.getDataTypeTag());
		assertEquals(DataTypeEnum.Blob.getTypeTag(), arg.getRawDataType());
	}

}
