package main.java;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.Random;

import javax.swing.JPanel;

import main.java.entity.Avocado;
import main.java.entity.Pointer;
import main.java.handlers.MouseHandler;

public class GamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = -5026029300298381915L;

	public Thread gameThread;
	public static final int TPS = 60;

	public static Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();

	public static int tileSize = (int) Math.floor(screenDimensions.getHeight() / 36);

	public MouseHandler mouseH = new MouseHandler(this);
	public Pointer pointer = new Pointer(this);

	public Random rand = new Random();

	public Avocado avocado = new Avocado(this, rand.nextInt(0, (int) (screenDimensions.getWidth() - 6 * tileSize)),
			rand.nextInt(0, (int) (screenDimensions.getHeight() - 6 * tileSize)), 6 * tileSize, 6 * tileSize);

	public static int score = 0;

	public GamePanel() {
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setLayout(null);
		this.setFocusable(true);
		this.setPreferredSize(screenDimensions);
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void update() {
		avocado.update(this);
		pointer.update(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		avocado.render(g2);

		g2.setColor(Color.RED);
		g2.setFont(new Font("Arial", Font.PLAIN, 30));
		g2.drawString("Score: " + score, 10, 40);

		g2.dispose();
	}

	public void logConsole() {
		System.out.println(GamePanel.score);
		System.out.println();
	}

	@Override
	public void run() {
		while (gameThread != null) {
			// DELTA GAME LOOP
			double drawInterval = 1000000000 / TPS;
			double delta = 0;
			long lastTime = System.nanoTime();
			long timer = System.currentTimeMillis();
			long fps = 0;

			while (gameThread != null) {
				long currentTime = System.nanoTime();
				delta += (currentTime - lastTime) / drawInterval;
				lastTime = currentTime;

				while (delta >= 1) {
					update();
					delta--;
				}
				if (gameThread != null) {
					repaint();
				}

				fps++;
				if (System.currentTimeMillis() - timer > 1000) {
					timer += 1000;
					System.out.println("FPS: " + fps);
					logConsole();
					fps = 0;
				}
			}
		}
	}
}
