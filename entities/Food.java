package com.snakegame.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Food {
	
	private int foodSize = 5;
	private Point foodLocation; //location (x,y)
	
	public Food(int x, int y) {
		this.foodLocation = new Point(x, y);
		
	}
	
	public void setFoodLocation(int x, int y) {
		this.foodLocation.setLocation(x, y);
	}
	
	
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawRect(foodLocation.x, foodLocation.y, foodSize, foodSize);
		g.setColor(Color.GRAY);
		g.fillRect(foodLocation.x, foodLocation.y, foodSize, foodSize);
	}
	
	public Point getFoodLocation() {
		return this.foodLocation;
	}

}
