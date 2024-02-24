package main.java.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.java.GamePanel;

public abstract class Entity extends Object {
	public GamePanel gp;

	public int x = 0, y = 0, width = 0, height = 0;
	public BufferedImage image;

	public double rotation = 0.0;

	public Rectangle hitbox = new Rectangle(0, 0, 0, 0);

	public Entity(GamePanel gp) {
		this.gp = gp;
	}

	public Entity(GamePanel gp, int x, int y) {
		this.gp = gp;
		this.x = x;
		this.y = y;
	}

	public Entity(GamePanel gp, int x, int y, int width, int height) {
		this.gp = gp;
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

	protected BufferedImage rotateImage(double rotation, int width, int height, BufferedImage imageToRotate) {
		double rads, sin, cos;
		int w = 1;
		int h = 1;

		BufferedImage rotatedImage;
		AffineTransform at = new AffineTransform();
		AffineTransformOp atOp;

		// STUFF THAT ROTATES IMAGE
		rads = Math.toRadians(rotation);
		sin = Math.abs(Math.sin(rads));
		cos = Math.abs(Math.cos(rads));
		w = (int) Math.floor(imageToRotate.getWidth() * cos + imageToRotate.getHeight() * sin);
		h = (int) Math.floor(imageToRotate.getHeight() * cos + imageToRotate.getWidth() * sin);
		rotatedImage = new BufferedImage(w, h, imageToRotate.getType());
		at.translate(w / 2, h / 2);
		at.rotate(rads, 0, 0);
		at.translate(-w / 2, -h / 2);
		atOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		atOp.filter(imageToRotate, rotatedImage);

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
