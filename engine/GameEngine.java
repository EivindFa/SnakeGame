package com.snakegame.engine;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.snakegame.entities.*;
import com.snakegame.form.GameForm;
import com.snakegame.utilities.Direction;
import com.snakegame.utilities.Keyboard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

public class GameEngine extends JPanel implements ActionListener{
	
	private Food food;
	private Snake snake;
	private Keyboard keyboard;
	private Timer timer;
	private int delay = 50; // game runs every ms - set speed of snakemovement
	private Direction direction;
	
	private int posX;
	private int posY;
	private int oldX;
	private int oldY;
	
	private boolean isGameOver = false; //controlling game flow
	private boolean isStarted = true;
	
	private boolean restrictLeftRight = false;
	private boolean restrictUpDown = false;
	
	private int foodsEaten =0;
	
	// constructor
	public GameEngine() {
		food = new Food(0, 0); // sets point food
		snake = new Snake();
		keyboard = Keyboard.getInstance();
		this.addKeyListener(keyboard); //this panel will listen to keyboard
		
		timer = new Timer(delay, this); //this correspont to every actionPerformed
		timer.start();
		direction = Direction.DOWN;
		
	}
	
	private void initialize() {
		snake = new Snake(); //move snake if it hits the wall
		direction = Direction.DOWN; 
		posX = oldX = 270; //init 
		posY = oldY = 270;
		foodsEaten = 0;
		produceFood();
	}
	
	private int readHighScore() {
		String fileName = "/Users/eivindfalun/highscore.txt";
		String line = null;
		String val = "0";
		
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader reader = new BufferedReader(fileReader);
			while ((line = reader.readLine()) != null){
				val = line;
			}
			reader.close();
			if (val != null && !val.equals("")) {
				return Integer.valueOf(val);
			}
		} catch(FileNotFoundException ex) {
			System.out.println("File not found");
		}
		catch(Exception ex) {
			System.out.println("There is a problem loading the file");;
		}
		return 0;
		
	}
	private void updateHighScore() {
		String fileName = "/Users/eivindfalun/highscore.txt";
		try {
			FileWriter writer = new FileWriter(fileName);
			BufferedWriter buffWriter = new BufferedWriter(writer);
			buffWriter.write(foodsEaten + "");
			buffWriter.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (isStarted) {
			g.setColor(Color.GRAY);
			g.drawString("Press ENTER to start the game.",
					GameForm.WIDTH /2 -100, GameForm.HEIGHT /2);
			if (keyboard.isDown(KeyEvent.VK_ENTER)) {
				isStarted = false;
				initialize();
				
			}
		}else if (isGameOver) {
			int score = readHighScore();
			if (score == 0){
				updateHighScore();
			} else if (foodsEaten > score) {
				updateHighScore();
			}
			g.setColor(Color.GRAY);
			g.drawString("Game Over.", GameForm.HEIGHT /2- 100, GameForm.HEIGHT /2);
			g.drawString("Press R to restart the game.", GameForm.HEIGHT /2 -18, GameForm.HEIGHT /2);
		}
		else {
			snake.draw(g);
			food.draw(g);
			g.setColor(Color.blue);
			g.drawString("Score : " + foodsEaten, 10, GameForm.HEIGHT -40);
			g.drawString("Highscore  : " + readHighScore(), GameForm.WIDTH -100, GameForm.HEIGHT -40);

		}
		
		food.draw(g); 
		snake.draw(g);
	}
	
	
	private void checkSnakeMovement() {
		if (restrictLeftRight) {
			if (keyboard.isDown(KeyEvent.VK_W)) {
				direction = Direction.UP;
			}
			else if (keyboard.isDown(KeyEvent.VK_S)) {
				direction = Direction.DOWN;
			}
			
		} else if (restrictUpDown){
			if (keyboard.isDown(KeyEvent.VK_A)) { // VK_A is A on the keyboard
				direction = Direction.LEFT;
			}
			else if (keyboard.isDown(KeyEvent.VK_D)) {
				direction = Direction.RIGHT;
			}
		}
			
		
		
			/*
		if (keyboard.isDown(KeyEvent.VK_A)) { // VK_A is A on the keyboard
			direction = Direction.LEFT;
		}
		else if (keyboard.isDown(KeyEvent.VK_D)) {
			direction = Direction.RIGHT;
		}
		else if (keyboard.isDown(KeyEvent.VK_W)) {
			direction = Direction.UP;
		}
		else if (keyboard.isDown(KeyEvent.VK_S)) {
			direction = Direction.DOWN;
		}
		*/
	}
	
	private void produceFood() {
		int locX = generateRandomOnGrid(GameForm.WIDTH / 20 -5,1);
		int locY = generateRandomOnGrid(GameForm.WIDTH / 20 -5,1);
		food.setFoodLocation(locX, locY);
	}
	
	private int generateRandomOnGrid(int high, int low) {
		return (int) (Math.floor(Math.random() * (1+high-low))+ low) * 20;
	}
	
	private void restrictSnakeMovement() {
		if (direction == Direction.LEFT || direction == Direction.RIGHT) {
			restrictLeftRight = true;
			restrictUpDown = false;
		}
		if (direction == Direction.UP || direction == Direction.DOWN) {
			restrictLeftRight = false;
			restrictUpDown = true;
		}
		
	}
	
	private void moveSnake() {
		switch(direction) {
		case UP: posY -= 10; break;
		case DOWN: posY += 10; break;
		case LEFT: posX -= 10; break;
		case RIGHT: posX += 10; break;
		}
	}
	
	public void update() {
		if (!isGameOver) {
			repaint();
			checkSnakeMovement();
			restrictSnakeMovement();
			moveSnake();
			snake.setSnakeHead(posX, posY, oldX, oldY);
			oldX = posX;
			oldY = posY;
			if (snake.isFoodEaten(food)) {
				produceFood();
				snake.growSnake();//grow snake
				foodsEaten++;
			}
			
			if (snake.isDead()) {
				isGameOver = true;
			}
		}
		if (isGameOver && keyboard.isDown(KeyEvent.VK_R)) {
			isGameOver = false;
			initialize();
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		update(); // everytime there is a change in the form, we need to repaint (snake is mooving)
	}
	
}
