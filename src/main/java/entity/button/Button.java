package main.java.entity.button;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.java.GamePanel;
import main.java.entity.Entity;
import main.java.handlers.MouseHandler;

public class Button extends Entity implements OnClickFunctionEntity {
	public BufferedImage normalImage;
	public BufferedImage hoveringImage;
	public BufferedImage clickedImage;

	public Button.Builder builder;

	public String dimensionsString;
	public String text;
	public Dimension dimensions;

	public Button(Builder builder) {
		super(builder.x, builder.y, builder.width, builder.height);

		this.dimensionsString = builder.dimensionsString;
		this.text = builder.text;

		this.builder = builder;

		getButtonImages();
	}

	public void getButtonImages() {
		try {
			normalImage = ImageIO.read(getClass()
					.getResourceAsStream("/textures/button/normal/button_" + dimensionsString + "_normal.png"));
			hoveringImage = ImageIO.read(getClass()
					.getResourceAsStream("/textures/button/hovering/button_" + dimensionsString + "_hover.png"));
			clickedImage = ImageIO.read(getClass()
					.getResourceAsStream("/textures/button/clicked/button_" + dimensionsString + "_clicked.png"));
		} catch (IOException e) {
			e.getStackTrace();
		}
		this.image = normalImage;
	}

	@Override
	public void onClick() {
		if (this.builder.onClickMethod != null) {
			this.builder.onClickMethod.onClick();
		} else {
			System.out.println("ERROR: NO ONCLICK FUNCTION");
		}
	}

	@Override
	public void update(GamePanel gp) {
		this.gp = gp;

		if (isTouching(GamePanel.p)) {
			if (MouseHandler.clicked) {
				clickChecker = true;
			} else if (clickChecker && MouseHandler.released) {
				onClick();
				clickChecker = false;
				MouseHandler.released = false;
			}
		} else {
			clickChecker = false;
		}
	}

	@Override
	public void render(Graphics2D g2) {
		this.g2 = g2;

		BufferedImage image = this.image;

		if (!isTouching(GamePanel.p)) {
			g2.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
		} else {
			if (MouseHandler.clicked) {
				g2.drawImage(clickedImage, getX(), getY(), getWidth(), getHeight(), null);
			} else {
				g2.drawImage(hoveringImage, getX(), getY(), getWidth(), getHeight(), null);
			}
		}
		g2.setColor(GamePanel.COLORS.get(GamePanel.colorIndex));
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, (int) (GamePanel.tileSize * 1.5)));
		g2.drawString(text, this.getXForCenteredText(text), this.getYForCenteredText(text));
	}

	public int getXForCenteredText(String text) {
		int width = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = getX() + getWidth() / 2 - width / 2;
		return x;
	}

	public int getYForCenteredText(String text) {
		int height = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
		int y = getY() + getHeight() / 2 + height / 3;
		return y;
	}

	public static class Builder {
		int x = 0;
		int y = 0;
		int width = 0;
		int height = 0;
		String text = "";
		String dimensionsString;
		Dimension dimensions;
		OnClickFunctionEntity onClickMethod;

		public Button.Builder onClick(OnClickFunctionEntity onClickMethod) {
			this.onClickMethod = onClickMethod;
			return this;
		}

		public Button.Builder x(int x) {
			this.x = x;
			return this;
		}

		public Button.Builder y(int y) {
			this.y = y;
			return this;
		}

		public Button.Builder pos(int x, int y) {
			this.x = x;
			this.y = y;
			return this;
		}

		public Button.Builder width(int width) {
			this.width = width;
			return this;
		}

		public Button.Builder height(int height) {
			this.height = height;
			return this;
		}

		public Button.Builder dimensions(int width, int height) {
			this.width = width;
			this.height = height;
			return this;
		}

		public Button.Builder text(String text) {
			this.text = text;
			return this;
		}

		public Button.Builder dimensionsString(String dimensionsString) {
			this.dimensionsString = dimensionsString;
			return this;
		}

		public Dimension getDimensions() {
			String[] dimensionList = dimensionsString.split("x");
			this.dimensions = new Dimension(Integer.parseInt(dimensionList[0]), Integer.parseInt(dimensionList[1]));
			return this.dimensions;
		}

		public Button build() {
			return new Button(this);
		}
	}
}
