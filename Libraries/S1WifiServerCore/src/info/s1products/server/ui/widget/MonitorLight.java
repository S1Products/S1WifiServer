package info.s1products.server.ui.widget;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MonitorLight extends JPanel {

	private static final long serialVersionUID = 2883005257299582933L;

	public static final String GREEN_ON  = "GreenOn.png";
	public static final String GREEN_OFF = "GreenOff.png";
	
	public static final String RED_ON  = "RedOn.png";
	public static final String RED_OFF = "RedOff.png";
	
	private Image onImage;
	private Image offImage;
	
	private Timer blinkTimer;
	private int blinkingInterval;
	
	private boolean isGreenLight = true;

	private boolean isOn;
	
	public MonitorLight(){
		super();
		loadImages();
		setDoubleBuffered(true);
	}
	
// Size methods
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(1024, 1024);
	}

	@Override
	public Dimension getMinimumSize() {
		return new Dimension(10, 10);
	}
	
	public boolean isOn() {
		return isOn;
	}

	public boolean isGreenLight() {
		return isGreenLight;
	}

	public void setGreenLight(boolean isGreenLight) {
		this.isGreenLight = isGreenLight;
		reloadImages();
		this.repaint();
	}

	public void setOn(boolean isOn) {

		this.isOn = isOn;

		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				MonitorLight.this.repaint();
				SwingUtilities.getRoot(MonitorLight.this).repaint();
			}
		});
	}

	protected void paintComponent(Graphics g){
		
		if(onImage == null || offImage == null){
			loadImages();
		}

		Image img = null;
		
		if(isOn){
			
			img = onImage;
			
			if(blinkingInterval > 0){
				startBlinkTimer();
			}
            
 		}else{
			img = offImage;
 		}

		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);	
    }

	public int getBlinkingInterval(){
		return this.blinkingInterval;
	}

	public void setBlinkingInterval(int interval){
		this.blinkingInterval = interval;
	}
	
	public void reloadImages(){
		loadImages();
	}
	
	private void loadImages(){

		if(isGreenLight){

			onImage  = createMonitorLightImage(GREEN_ON);
			offImage = createMonitorLightImage(GREEN_OFF);
			
		}else{
			
			onImage  = createMonitorLightImage(RED_ON);
			offImage = createMonitorLightImage(RED_OFF);
		}
	}
	
	private Image createMonitorLightImage(String fileName){

		URL imageUrl 
			= this.getClass().getClassLoader().getResource(fileName);

		Image image 
			= Toolkit.getDefaultToolkit().getImage(imageUrl);

		return image;
	}

	private void startBlinkTimer(){

		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				
				blinkTimer.cancel();
				
				MonitorLight.this.setOn(false);
			}
		};
		
		blinkTimer = new Timer();
		blinkTimer.schedule(task, blinkingInterval);
	}
}
