package com.snakegame.utilities;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	
	private static Keyboard instance;
	private boolean keys[];
	
	public Keyboard() {
		keys = new boolean[256]; //ASCII
	}
	
	public static Keyboard getInstance() {
		if (instance == null) {
			instance = new Keyboard();
		}
		return instance;
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() >= 0 && e.getKeyCode() < keys.length) {
			keys[e.getKeyCode()] = true; // key is pressed now
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() >= 0 && e.getKeyCode() < keys.length) {
			keys[e.getKeyCode()] = false; 
		}
	}
	
	public boolean isDown(int key) {
		if (key >= 0 && key < keys.length) { //within ascii range
			return keys[key];
		}
		return false;
	}

}
