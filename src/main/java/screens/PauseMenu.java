package main.java.screens;

import java.awt.Graphics2D;

import main.java.GamePanel;
import main.java.entity.button.Button;

public class PauseMenu {
	public GamePanel gp;

	public Button colorButton, quitButton;

	public PauseMenu(GamePanel gp) {
		this.gp = gp;

		colorButton = new Button.Builder().dimensionsString("16x2").pos(0, 0)
				.dimensions(GamePanel.tileSize * 24, GamePanel.tileSize * 3)
				.text("Color: " + GamePanel.COLOR_NAMES.get(GamePanel.colorIndex)).onClick(() -> {
					if (GamePanel.colorIndex < 8)
						GamePanel.colorIndex++;
					else
						GamePanel.colorIndex = 0;
				}).build();
		quitButton = new Button.Builder().dimensionsString("16x2").pos(0, GamePanel.tileSize * 3)
				.dimensions(GamePanel.tileSize * 24, GamePanel.tileSize * 3).text("QUIT").onClick(() -> System.exit(0))
				.build();
	}

	public void update(GamePanel gp) {
		colorButton.update(gp);

		colorButton.text = "Color: " + GamePanel.COLOR_NAMES.get(GamePanel.colorIndex);

		quitButton.update(gp);
	}

	public void render(Graphics2D g2) {
		colorButton.render(g2);
		quitButton.render(g2);
	}
}
