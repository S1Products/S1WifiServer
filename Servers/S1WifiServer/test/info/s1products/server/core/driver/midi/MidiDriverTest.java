/**
 * 
 */
package info.s1products.server.core.driver.midi;

import static info.s1products.server.device.MidiSender.PROP_DEVICE_ID;
import static org.junit.Assert.assertTrue;
import info.s1products.server.device.MidiSender;
import info.s1products.server.message.Argument;
import info.s1products.server.message.Message;
import info.s1products.server.message.DataType.DataTypeEnum;
import info.s1products.server.router.RoutingResult;

import java.util.Properties;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Shuichi
 *
 */
public class MidiDriverTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSendNoteOn() {
		
		Properties prop = new Properties();
		prop.setProperty(PROP_DEVICE_ID, "Microsoft GS Wavetable Synth");
		
		MidiSender driver = new MidiSender(prop);
		driver.open();

		Message message = new Message();
		Argument arg = new Argument(DataTypeEnum.MidiMessage);
		arg.setData(new byte[] {(byte)0x00, (byte)0x90, (byte)64, (byte)100});
		message.add(arg);
		
		RoutingResult result = driver.receiveMessageFromRouter(message);
		assertTrue(result.isSuccess());

		try{ Thread.sleep(2000); }catch(Exception ex){}
		
		arg.setData(new byte[]{(byte)0x00, (byte)0x80, (byte)64, (byte)100});
		
		result = driver.receiveMessageFromRouter(message);
		assertTrue(result.isSuccess());
		
		driver.close();
	}

}
