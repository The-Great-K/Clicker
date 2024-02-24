package main.java.entity;

import java.awt.Graphics2D;

import main.java.GamePanel;

public class Pointer extends Entity {
	public Pointer(GamePanel gp) {
		super(gp);

		this.hitbox.width = 1;
		this.hitbox.height = 1;
	}

	@Override
	public void update(GamePanel gp) {
		this.hitbox.setLocation((int) gp.mouseH.location.getX(), (int) gp.mouseH.location.getY());
	}

	@Override
	public void render(Graphics2D g2) {
	}
}
