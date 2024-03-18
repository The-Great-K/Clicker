package main.java.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.java.GamePanel;
import main.java.handlers.MouseHandler;

public class Avocado extends Entity {
	public GamePanel gp;

	public boolean xDirection, yDirection;
	public int xSpeed, ySpeed;

	public int collisions = 0;

	public double rotateSpeed = 5;

	public BufferedImage rotatedImage;

	public Random rand = new Random();

	public Avocado(int x, int y, int width, int height) {
		super(x, y, width, height);

		xDirection = rand.nextBoolean();
		yDirection = rand.nextBoolean();

		xSpeed = xDirection ? 5 : -5;
		ySpeed = yDirection ? 5 : -5;

		this.getImage("/textures/entity/avocado.png");
		rotatedImage = new BufferedImage(100, 100, image.getType());
	}

	public void update(GamePanel gp) {
		if (x <= 0 || x + width >= GamePanel.screenDimensions.getWidth()) {
			xSpeed *= -1;
			collisions++;
		}
		if (y <= 0 || y + height >= GamePanel.screenDimensions.getHeight()) {
			ySpeed *= -1;
			collisions++;
		}

		x += xSpeed;
		y += ySpeed;
		rotation += rotateSpeed;

		hitbox.x = x;
		hitbox.y = y;

		rotatedImage = this.rotateImage(image, rotation);

		if (isTouching(GamePanel.p)) {
			if (MouseHandler.clicked) {
				clickChecker = true;
			} else if (clickChecker && MouseHandler.released) {
				GamePanel.score++;
				clickChecker = false;
				MouseHandler.released = false;
			}
		} else {
			clickChecker = false;
		}
	}

	public void render(Graphics2D g2) {
		this.g2 = g2;

		g2.setColor(Color.red);
		g2.drawImage(rotatedImage, x, y, width, height, null);
//		g2.drawRect(x, y, width, height);
	}
}
