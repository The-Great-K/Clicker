package main.java;

import java.awt.Toolkit;

import javax.swing.JFrame;

public class Main {
	public static JFrame window;

	public static void main(String[] args) {
		window = new JFrame("Avocado Clicker");

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setUndecorated(true);

		GamePanel gp = new GamePanel();
		gp.startGameThread();
		window.add(gp);
		window.pack();

		window.setLocationRelativeTo(null);
		window.setIconImage(Toolkit.getDefaultToolkit().getImage("resources/textures/entity/avocado.png"));
		window.setVisible(true);
	}
}
