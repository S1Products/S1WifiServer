package info.s1products.server.ui;

import static info.s1products.server.S1MidiConstants.KEY_MIDI_IN_INDEX;
import static info.s1products.server.S1MidiConstants.KEY_MIDI_OUT_INDEX;
import static info.s1products.server.S1MidiConstants.KEY_NOTIFIER_PORT_NO;
import static info.s1products.server.S1MidiConstants.KEY_REQUEST_PORT_NO;
import static info.s1products.server.S1MidiConstants.KEY_USE_MIDI_IN;
import static info.s1products.server.S1MidiConstants.KEY_USE_MIDI_OUT;
import static info.s1products.server.ServerConstants.DEFAULT_NOTIFIER_PORT;
import static info.s1products.server.ServerConstants.DEFAULT_REQUEST_PORT;
import info.s1products.server.device.MidiDeviceUtil;
import info.s1products.server.event.MessageReceivedEvent;
import info.s1products.server.event.MessageReceivedListener;
import info.s1products.server.event.MessageSendedEvent;
import info.s1products.server.event.MessageSendedListener;
import info.s1products.server.event.PacketNotifiedEvent;
import info.s1products.server.event.PacketNotifiedListener;
import info.s1products.server.event.RequestReceivedEvent;
import info.s1products.server.event.RequestReceivedListener;
import info.s1products.server.ui.controller.S1MidiWifiServerGuiController;
import info.s1products.server.ui.widget.MonitorLightField;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Properties;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;

public class S1WifiServerGui {

	private S1MidiWifiServerGuiController controller 
		= new S1MidiWifiServerGuiController();

	private JFrame frmSWifiServer;

	private MonitorLightField readyLight;
	private MonitorLightField requestLight;
	private MonitorLightField sendLight;
	private MonitorLightField outLight;
	private MonitorLightField inLight;
	private MonitorLightField errorLight;
	private JToggleButton toggleServerState;
	private JPanel panelOperation;
	private JCheckBox checkMidiOut;
	private JCheckBox checkMidiIn;
	private JPanel panelMain;
	@SuppressWarnings("rawtypes")
	private JComboBox comboMidiOut;
	@SuppressWarnings("rawtypes")
	private JComboBox comboMidiIn;
	private JLabel lblUdpPort;
	private JSpinner spinRequestPort;
	private JLabel lblSenderPort;
	private JSpinner spinSenderPort;
	private JTabbedPane tabbedPane;
	private JPanel panelNetwork;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		try{
			Properties lafProp = new Properties();
			lafProp.setProperty("backgroundColorLight", "40 40 40");
			lafProp.setProperty("backgroundColorDark",  "30 30 30");
			
			HiFiLookAndFeel.setCurrentTheme(lafProp);
	        UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");

		}catch(Exception ex){
			ex.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					S1WifiServerGui window = new S1WifiServerGui();
					
                    SwingUtilities.updateComponentTreeUI(window.frmSWifiServer);

					window.frmSWifiServer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void initializeDeviceList(){
		
		comboMidiOut.removeAllItems();
		comboMidiIn.removeAllItems();

		controller.loadSettings();
		
		List<String> outDeviceList = MidiDeviceUtil.getMidiOutDriverList();
		for(String deviceName : outDeviceList){
			
			comboMidiOut.addItem(deviceName);
		}

		List<String> inDeviceList = MidiDeviceUtil.getMidiInDriverList();
		for(String deviceName : inDeviceList){
			
			comboMidiIn.addItem(deviceName);
		}
	}

	private void initializeInputControls(){
		
		Properties appProp = controller.loadSettings();

		String useOutStr = appProp.getProperty(KEY_USE_MIDI_OUT);
		if(useOutStr == null){
			checkMidiOut.setSelected(true);
			
		}else{
			boolean useOut = Boolean.parseBoolean(useOutStr);
			checkMidiOut.setSelected(useOut);
		}
		
		String outStr = appProp.getProperty(KEY_MIDI_OUT_INDEX);
		if(outStr == null){
			comboMidiOut.setSelectedIndex(0);
		}else{
			int selection = Integer.parseInt(outStr);
			if(comboMidiOut.getItemCount() > selection){
				comboMidiOut.setSelectedIndex(selection);
			}else{
				comboMidiOut.setSelectedIndex(0);
			}
		}

		
		String useInStr = appProp.getProperty(KEY_USE_MIDI_IN);
		if(useInStr == null){
			checkMidiIn.setSelected(false);
			
		}else{
			boolean useIn = Boolean.parseBoolean(useInStr);
			checkMidiIn.setSelected(useIn);
		}

		String inStr = appProp.getProperty(KEY_MIDI_IN_INDEX);
		if(inStr == null){
			comboMidiIn.setSelectedIndex(0);
		}else{
			int selection = Integer.parseInt(inStr);
			if(comboMidiIn.getItemCount() > selection){
				comboMidiIn.setSelectedIndex(selection);
			}else{
				comboMidiIn.setSelectedIndex(0);
			}
		}

        String reqPortNo = appProp.getProperty(KEY_REQUEST_PORT_NO);
        if(reqPortNo == null){
        	spinRequestPort.setValue(DEFAULT_REQUEST_PORT);
        }else{
        	spinRequestPort.setValue(Integer.valueOf(reqPortNo));
        }
        
        String notifyPortNo = appProp.getProperty(KEY_NOTIFIER_PORT_NO);
        if(notifyPortNo == null){
        	spinSenderPort.setValue(DEFAULT_NOTIFIER_PORT);
        }else{
        	spinSenderPort.setValue(Integer.valueOf(notifyPortNo));
        }
	}
	
	private void initializeUI(){
		
		initializeDeviceList();
		initializeInputControls();
	}

	private void startServer(){
		
		int reqPort = Integer.parseInt(spinRequestPort.getValue().toString());
		int sndPort = Integer.parseInt(spinSenderPort.getValue().toString());
		
		controller.startServer(checkMidiOut.isSelected(),
				(String)comboMidiOut.getSelectedItem(), 
				checkMidiIn.isSelected(),
				(String)comboMidiIn.getSelectedItem(), 
				reqPort,
				sndPort);
		
		toggleServerState.setText("Stop");
		
		readyLight.setOn(true);
		
		disableControls();
	}

	private void disableControls(){
		
		spinRequestPort.setEnabled(false);
		spinSenderPort.setEnabled(false);
		checkMidiIn.setEnabled(false);
		checkMidiOut.setEnabled(false);
		comboMidiIn.setEnabled(false);
		comboMidiOut.setEnabled(false);
	}

	private void stopServer(){
		
		controller.stopServer();
		toggleServerState.setText("Start");
		
		readyLight.setOn(false);
		
		enableControls();
	}

	private void enableControls(){
		
		spinRequestPort.setEnabled(true);
		spinSenderPort.setEnabled(true);
		checkMidiIn.setEnabled(true);
		checkMidiOut.setEnabled(true);
		comboMidiIn.setEnabled(true);
		comboMidiOut.setEnabled(true);
	}

	private void disposeUI(){

		controller.close();
		
		controller.saveSettings(checkMidiOut.isSelected(),
								checkMidiIn.isSelected(),
								comboMidiOut.getSelectedIndex(), 
								comboMidiIn.getSelectedIndex(), 
								spinRequestPort.getValue().toString(),
								spinSenderPort.getValue().toString());
	}
	
	/**
	 * Create the application.
	 */
	public S1WifiServerGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("rawtypes")
	private void initialize() {
		frmSWifiServer = new JFrame();
		frmSWifiServer.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				
				initializeUI();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				disposeUI();
			}
		});
		frmSWifiServer.setTitle("S1 Wifi Server");
		frmSWifiServer.setBounds(100, 100, 330, 270);
		frmSWifiServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panelOperation = new JPanel();
		frmSWifiServer.getContentPane().add(panelOperation, BorderLayout.SOUTH);
		panelOperation.setLayout(new GridLayout(1, 3, 0, 0));
		
		toggleServerState = new JToggleButton("Start");
		toggleServerState.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(toggleServerState.isSelected()){
					startServer();
				}else{
					stopServer();
				}
			}
		});
		panelOperation.add(toggleServerState);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmSWifiServer.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		panelMain = new JPanel();
		tabbedPane.addTab("MIDI", null, panelMain, null);
		comboMidiOut = new JComboBox();
		comboMidiIn = new JComboBox();
		
		checkMidiOut = new JCheckBox("Enable Out:");
		
		checkMidiIn = new JCheckBox("Enable In:");
		GroupLayout gl_panelMain = new GroupLayout(panelMain);
		gl_panelMain.setHorizontalGroup(
			gl_panelMain.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelMain.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelMain.createParallelGroup(Alignment.LEADING, false)
						.addComponent(checkMidiOut, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(checkMidiIn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelMain.createParallelGroup(Alignment.LEADING)
						.addComponent(comboMidiOut, 0, 209, Short.MAX_VALUE)
						.addComponent(comboMidiIn, 0, 202, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelMain.setVerticalGroup(
			gl_panelMain.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMain.createSequentialGroup()
					.addGap(8)
					.addGroup(gl_panelMain.createParallelGroup(Alignment.BASELINE)
						.addComponent(checkMidiOut)
						.addComponent(comboMidiOut, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelMain.createParallelGroup(Alignment.BASELINE)
						.addComponent(checkMidiIn)
						.addComponent(comboMidiIn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(77, Short.MAX_VALUE))
		);
		gl_panelMain.setAutoCreateGaps(true);
		gl_panelMain.setAutoCreateContainerGaps(true);
		panelMain.setLayout(gl_panelMain);
		
		panelNetwork = new JPanel();
		tabbedPane.addTab("Network", null, panelNetwork, null);
		
		lblSenderPort = new JLabel("Sender Port:");
		
		spinRequestPort = new JSpinner();
		spinRequestPort.setModel(new SpinnerNumberModel(new Integer(9000), null, null, new Integer(1)));
		
		lblUdpPort = new JLabel("Request Port:");
		
		spinSenderPort = new JSpinner();
		spinSenderPort.setModel(new SpinnerNumberModel(new Integer(9010), null, null, new Integer(1)));
		GroupLayout gl_panelNetwork = new GroupLayout(panelNetwork);
		gl_panelNetwork.setHorizontalGroup(
			gl_panelNetwork.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelNetwork.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelNetwork.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblSenderPort, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblUdpPort, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelNetwork.createParallelGroup(Alignment.LEADING, false)
						.addComponent(spinRequestPort)
						.addComponent(spinSenderPort, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
					.addContainerGap(146, Short.MAX_VALUE))
		);
		gl_panelNetwork.setVerticalGroup(
			gl_panelNetwork.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelNetwork.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelNetwork.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUdpPort)
						.addComponent(spinRequestPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelNetwork.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSenderPort)
						.addComponent(spinSenderPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(62, Short.MAX_VALUE))
		);
		panelNetwork.setLayout(gl_panelNetwork);
		
		JPanel panelMonitor = new JPanel();
		panelMonitor.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Status", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		frmSWifiServer.getContentPane().add(panelMonitor, BorderLayout.NORTH);
		panelMonitor.setLayout(new GridLayout(0, 3, 0, 0));
		
		readyLight = new MonitorLightField();
		readyLight.setText("Ready");
		panelMonitor.add(readyLight);
		
		requestLight = new MonitorLightField();
		requestLight.setBlinkInterval(100);
		requestLight.setText("Request");
		panelMonitor.add(requestLight);
		
		outLight = new MonitorLightField();
		outLight.setBlinkInterval(100);
		outLight.setText("MIDI Out");
		panelMonitor.add(outLight);
		
		errorLight = new MonitorLightField();
		errorLight.setGreenColor(false);
		panelMonitor.add(errorLight);
		errorLight.setBlinkInterval(500);
		errorLight.setText("Error");
		
		sendLight = new MonitorLightField();
		sendLight.setBlinkInterval(100);
		sendLight.setText("Send");
		panelMonitor.add(sendLight);
		
		inLight = new MonitorLightField();
		inLight.setBlinkInterval(100);
		inLight.setText("MIDI In");
		panelMonitor.add(inLight);
		
		controller.getS1MidiWifiServer().setMidiOutListener(new MessageSendedListener() {
			
			@Override
			public void messageSended(MessageSendedEvent event) {
				outLight.setOn(true);
			}
		});
		
		controller.getS1MidiWifiServer().setMidiInListener(new MessageReceivedListener() {
			
			@Override
			public void messageReceived(MessageReceivedEvent event) {
				inLight.setOn(true);
			}
		});
		
		controller.getS1MidiWifiServer().setRequestListener(new RequestReceivedListener() {
			
			@Override
			public void packetReceived(RequestReceivedEvent event) {
				requestLight.setOn(true);
			}
		});
		
		controller.getS1MidiWifiServer().setPacketNotifiedListener(new PacketNotifiedListener() {
			
			@Override
			public void packetNotified(PacketNotifiedEvent event) {
				sendLight.setOn(true);
			}
		});
	}
}
