package main.java.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.java.GamePanel;

public abstract class Entity extends Object {
	public GamePanel gp;
	public Graphics2D g2;

	public int x = 0, y = 0, width = 0, height = 0;
	public BufferedImage image;

	public double rotation = 0.0;

	public Rectangle hitbox = new Rectangle(0, 0, 0, 0);

	public boolean clickChecker = false;

	public Entity() {
	}

	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Entity(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		hitbox.setBounds(x, y, width, height);
	}

	public void getImage(String path) {
		try {
			image = ImageIO.read(getClass().getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isTouching(Entity entity) {
		try {
			if (entity.hitbox.intersects(hitbox)) {
				return true;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public BufferedImage rotateImage(BufferedImage image, double angle) {
		int width = image.getWidth();
		int height = image.getHeight();

		BufferedImage rotatedImage = new BufferedImage(width, height, image.getType());
		Graphics2D g2d = rotatedImage.createGraphics();

		AffineTransform transform = new AffineTransform();
		transform.rotate(Math.toRadians(angle), width / 2, height / 2);
		g2d.setTransform(transform);

		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();

		return rotatedImage;
	}

	public abstract void update(GamePanel gp);

	public abstract void render(Graphics2D g2);

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
