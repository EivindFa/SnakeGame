package com.snakegame.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import com.snakegame.form.GameForm;

public class Snake {
	
	private int snakeSize = 4; // initial snakesnize
	private int snakeHeadLocation = 270;
	private ArrayList<Point> snakeBody;
	//private Point [] snakeBody1 = new Point[4];
	
	
	public Snake() {
		snakeBody = new ArrayList<>();
		
		snakeBody.add(new Point(snakeHeadLocation, snakeHeadLocation));
		
		for (int i = 0; i<snakeSize; i++) {
			// snakeBody.get(i) -> array.get(i)
			// .y gir y-verdien til Point-objectet
			snakeBody.add(new Point(snakeHeadLocation, snakeBody.get(i).y-10));
			
		}
		
	}
	
	//handles collision
	public boolean isDead() {
		Point head = snakeBody.get(0); //initial point in arraylist snake body (Head)
		//Collided with the right wall
		if (head.getLocation().x + 10 >= GameForm.WIDTH ||
				head.getLocation().x < 0) { //collided with left wall
			return true;
			
		}
		//colliding top or down
		if (head.getLocation().y + 10 >= GameForm.HEIGHT || 
				head.getLocation().y <0) {
			return true;
		}
		for (int i = 1; i < snakeBody.size(); i++) {
			if (head.x == snakeBody.get(i).x && 
					head.y == snakeBody.get(i).y) {
				return true;
			}
		}
		
		return false;
		
	}
	
	public void setSnakeHead(int x, int y, int oldX, int oldY) { //update 
		try {
			snakeBody.get(0).setLocation(x,y);
			for (int i=1; i<snakeBody.size(); i++) {
				int tempX = snakeBody.get(i).getLocation().x;
				int tempY = snakeBody.get(i).getLocation().y;
				snakeBody.get(i).setLocation(oldX, oldY);
				oldX = tempX;
				oldY = tempY;
			}	
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public boolean isFoodEaten(Food f) {
		Point head = snakeBody.get(0);
		if (head.getLocation().x == f.getFoodLocation().x) {
			if (head.getLocation().y == f.getFoodLocation().y) {
				return true;
			}
		}
		return false;
	}
	
	
	public void draw(Graphics g) {
		
		for (int i = 0; i <snakeBody.size(); i++) {
			g.setColor(Color.GRAY);
			g.drawRect(snakeBody.get(i).getLocation().x, snakeBody.get(i).getLocation().y, snakeSize,snakeSize);
			g.setColor(Color.BLUE);
			g.fillRect(snakeBody.get(i).getLocation().x, snakeBody.get(i).getLocation().y, snakeSize,snakeSize);
			
		}	
	}
	
	public void growSnake() {
		int size = snakeBody.size()-1;
		snakeBody.add(new Point(
				snakeBody.get(size).getLocation().x,
				snakeBody.get(size).getLocation().y));
	}

}
