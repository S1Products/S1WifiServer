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
package info.s1products.server.ui.widget;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MonitorLightField extends JPanel {
	
	private static final long serialVersionUID = 3375615408403387047L;

	private MonitorLight monitorLight;
	private JLabel label;
	
	public MonitorLightField() {
		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 2, 2);
		setLayout(flowLayout);
		
		monitorLight = new MonitorLight();
		monitorLight.setGreenLight(true);
		monitorLight.reloadImages();
		add(monitorLight);
		monitorLight.validate();
		monitorLight.repaint();
		
		label = new JLabel("Text");
		add(label);
	}
	
	public void setText(String text){
		label.setText(text);
	}
	
	public String getText(){
		return label.getText();
	}
	
	public void setOn(boolean isOn){
		monitorLight.setOn(isOn);
	}
	
	public boolean isOn(){
		return monitorLight.isOn();
	}
	
	public void setGreenColor(boolean isGreenLight){
		monitorLight.setGreenLight(isGreenLight);
	}
	
	public boolean isGreenColor(){
		return monitorLight.isGreenLight();
	}
	
	public void setBlinkInterval(int interval){
		monitorLight.setBlinkingInterval(interval);
	}
	
	public int getBlinkInterval(){
		return monitorLight.getBlinkingInterval();
	}
}
