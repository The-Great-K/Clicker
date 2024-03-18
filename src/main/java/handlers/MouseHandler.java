package main.java.handlers;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import main.java.GamePanel;

public class MouseHandler extends MouseAdapter {
	public GamePanel gp;

	public Point location = new Point(0, 0);
	public static boolean clicked = false;
	public static boolean released = false;

	public MouseHandler(GamePanel gp) {
		this.gp = gp;

		gp.addMouseListener(this);
		gp.addMouseMotionListener(this);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		clicked = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		clicked = false;
		released = true;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		location.x = e.getX();
		location.y = e.getY();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		location.x = e.getX();
		location.y = e.getY();
	}
}
