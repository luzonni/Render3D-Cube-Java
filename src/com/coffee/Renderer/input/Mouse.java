package com.coffee.Renderer.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {
	
	private int mouseX = -1;
	private int mouseY = -1;
	private int mouseB = -1;
	private int scrool = 0;
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		this.mouseB = e.getButton();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.mouseB = e.getButton();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.mouseB = -1;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		this.scrool = e.getWheelRotation();
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		this.mouseX = event.getX();
		this.mouseY = event.getY();
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		this.mouseX = event.getX();
		this.mouseY = event.getY();
		
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public ClickType getButton() {
		switch(this.mouseB) {
		case 1: 
			return ClickType.LeftClick;
		case 2: 
			return ClickType.ScroolClick;
		case 3: 
			return ClickType.RigthClick;
		case 4: 
			return ClickType.BackPage;
		case 5: 
			return ClickType.ForwardPage;
		default:
			return ClickType.Unknown;
		}
	}

	public boolean isScroolUp() {
		return scrool == -1;
	}
	
	public boolean isScroolDown() {
		return scrool == 1;
	}
	
	public void reserScrool() {
		scrool = 0;
	}
	
	public void resetButton() {
		this.mouseB = -1;
	}

}
