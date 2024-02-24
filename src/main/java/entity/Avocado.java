package main.java.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.java.GamePanel;

public class Avocado extends Entity {
	public GamePanel gp;

	public boolean xDirection, yDirection;
	public int xSpeed, ySpeed;

	public double rotateSpeed = 5;

	public BufferedImage rotatedImage;

	public Random rand = new Random();

	public Avocado(GamePanel gp, int x, int y, int width, int height) {
		super(gp, x, y, width, height);

		xDirection = rand.nextBoolean();
		yDirection = rand.nextBoolean();

		xSpeed = xDirection ? 5 : -5;
		ySpeed = yDirection ? 5 : -5;

		this.getImage("/textures/entity/avacado.png");
		rotatedImage = new BufferedImage(1, 1, image.getType());
	}

	public void update(GamePanel gp) {
		this.gp = gp;

		if (x <= 0 || x + width >= GamePanel.screenDimensions.getWidth()) {
			xSpeed *= -1;
		}
		if (y <= 0 || y + height >= GamePanel.screenDimensions.getHeight()) {
			ySpeed *= -1;
		}

		x += xSpeed;
		y += ySpeed;
		rotation += rotateSpeed;

		hitbox.x = x;
		hitbox.y = y;
		hitbox.width = width;
		hitbox.height = height;

		rotatedImage = this.rotateImage(rotation, width, height, image);

		if (isTouchingPlayer() && gp.mouseH.clicked) {
			GamePanel.score++;
			gp.mouseH.clicked = false;
		}
	}

	public void render(Graphics2D g2) {
		g2.drawImage(rotatedImage, x, y, width, height, null);
	}

	public boolean isTouchingPlayer() {
		if (gp.pointer.hitbox.intersects(hitbox)) {
			return true;
		}
		return false;
	}
}
