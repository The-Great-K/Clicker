package main.java;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

import main.java.entity.Avocado;
import main.java.entity.Entity;
import main.java.handlers.KeyHandler;
import main.java.handlers.MouseHandler;
import main.java.screens.PauseMenu;

public class GamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = -5026029300298381915L;

	public Thread gameThread;
	public static final int TPS = 60;

	public static Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();

	public static int tileSize = (int) Math.floor(screenDimensions.getHeight() / 36);

	// COLOR THEMES
	public static int colorIndex = 0;
	public static final List<Color> COLORS = List.of(new Color(255, 0, 0), new Color(191, 94, 10),
			new Color(209, 181, 21), new Color(0, 255, 0), new Color(6, 105, 11), new Color(0, 255, 255),
			new Color(24, 9, 203), new Color(122, 14, 158), new Color(235, 16, 202), new Color(255, 255, 255));

	public static final List<String> COLOR_NAMES = List.of("Red", "Orange", "Yellow", "Lime", "Green", "Cyan", "Blue",
			"Purple", "Pink", "White");

	public Font defaultFont;

	public KeyHandler keyH = new KeyHandler(this);
	public MouseHandler mouseH = new MouseHandler(this);

	public PauseMenu pauseMenu = new PauseMenu(this);

	public static P p = new P();

	public Random rand = new Random();

	public Avocado avocado = new Avocado(rand.nextInt(0, (int) (screenDimensions.getWidth() - 5 * tileSize)),
			rand.nextInt(0, (int) (screenDimensions.getHeight() - 5 * tileSize)), 5 * tileSize, 5 * tileSize);

	public static int score = 0;

	public GamePanel() {
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setLayout(null);
		this.setFocusable(true);
		this.setPreferredSize(screenDimensions);

		try (InputStream is = getClass().getResourceAsStream("/fonts/rainyhearts.ttf")) {
			defaultFont = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void update() {
		p.update(this);

		if (keyH.paused) {
			pauseMenu.update(this);
		} else {
			avocado.update(this);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		avocado.render(g2);

		g2.setColor(COLORS.get(colorIndex));
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, (int) (GamePanel.tileSize * 2)));
		g2.drawString("Score: " + score, 10, tileSize * 2);

		if (keyH.paused) {
			pauseMenu.render(g2);
		}

		g2.dispose();
	}

	public void logConsole() {
		System.out.println(GamePanel.score);
		System.out.println(pauseMenu.colorButton.clickChecker);
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

	public static class P extends Entity {
		public P() {
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
}
