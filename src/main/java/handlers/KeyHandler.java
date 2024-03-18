package main.java.handlers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import main.java.GamePanel;

public class KeyHandler extends KeyAdapter {
	public GamePanel gp;

	public boolean paused = false;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;

		gp.addKeyListener(this);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_ESCAPE) {
			paused = paused ? false : true;
		}
	}
}
