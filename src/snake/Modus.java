package snake;

import java.awt.HeadlessException;
import java.io.FileNotFoundException;

import javax.swing.JOptionPane;
import processing.core.*;

/**
 * Klasse zum Darstellen der verschiedenen Modi
 * @author Sylvain Gilgen
 *
 */
public class Modus{
	public static final int GAME_MENU = 0;
	public static final int GAME_RUNNING = 1;
	public static final int PAUSED = 2;
	public static final int GAME_OVER = 3;
	public static final int GAME_RESTART = 4;
	public static final int MULTIPLAYER = 7;
	public static final int ARCADE = 8;
	public static final int VS_PC = 9;
	private PApplet p;
	private boolean print;
	private PVector mouse, btn1, btn2, btn3, btn4;
	private int rad, selectedMode, gameState, menuTime, countdown, punkteSnakeOne, punkteSnakeTwo;
	private PImage snake, logo;
	private String spielerName;

	/**
	 * Konstruktor zum Erstellen eines Modusobjektes
	 * @param p PApplet
	 */
	public Modus(PApplet p) {
		this.p = p;
		btn1 = new PVector(p.width / 2 - 100, 500);
		btn2 = new PVector(p.width / 2 - 100, 600);
		btn3 = new PVector(p.width / 2 - 100, 700);
		btn4 = new PVector(p.width / 2 - 100, 800);
		rad = 50;
		snake = p.loadImage("Snake.png");
		logo = p.loadImage("logoLogo.png");
		gameState = 0;
		selectedMode = 0;
		print = false;
		p.getSurface().setIcon(logo);
		p.getSurface().setTitle("Snake by Topomedics");
	}

	/**
	 * menu anzeigen
	 */
	public void menu() {
		mouse = new PVector(p.mouseX, p.mouseY);
		p.image(snake, 0, 0, p.width, p.height);
		p.fill(255);
		p.frameRate(60);
		p.textSize(48);
		p.text("Multiplayer", btn1.x + rad, btn1.y + 20);
		p.text("Arcade", btn2.x + rad, btn2.y + 20);
		p.text("Vs PC", btn3.x + rad, btn3.y + 20);
		p.text("Anleitung", btn4.x + rad, btn4.y + 20);
		p.ellipse(btn1.x, btn1.y, rad, rad);
		p.ellipse(btn2.x, btn2.y, rad, rad);
		p.ellipse(btn3.x, btn3.y, rad, rad);
		p.ellipse(btn4.x, btn4.y, rad, rad);
		menuTime = p.millis();
		if (p.mousePressed && mouse.dist(btn1) < rad) {
			gameState = 1;
			selectedMode = 7;
		}
		if (p.mousePressed && mouse.dist(btn2) < rad) {
			do {
				spielerName = JOptionPane.showInputDialog("Spielername");
				if(spielerName == null) {
					return;
				}
			} while (spielerName.equals(""));
			gameState = 1;
			selectedMode = 8;
		}
		if (p.mousePressed && mouse.dist(btn3) < rad) {
			gameState = 1;
			selectedMode = 9;
		}
		if (p.mousePressed && mouse.dist(btn4) < rad) {
			try {
				JOptionPane.showMessageDialog(null, FileInterpreter.scanManual());
			} catch (HeadlessException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Pausenmenu anzeigen
	 */
	public void pause() {
		mouse = new PVector(p.mouseX, p.mouseY);
		if (p.mousePressed && mouse.dist(btn1) < rad) {
			gameState = 1;
		} else if (p.mousePressed && mouse.dist(btn2) < rad) {
			gameState = 4;
		} else if (p.mousePressed && mouse.dist(btn3) < rad) {
			System.exit(0);
		}
		p.fill(255);
		p.rectMode(2);
		p.rect(p.width / 2, p.height / 2 + 100, 400, 200);
		p.fill(0);
		p.text("Resume", btn1.x + rad, btn1.y + 20);
		p.text("Menu", btn2.x + rad, btn2.y + 20);
		p.text("Quit", btn3.x + rad, btn3.y + 20);
		p.ellipse(btn1.x, btn1.y, rad, rad);
		p.ellipse(btn2.x, btn2.y, rad, rad);
		p.ellipse(btn3.x, btn3.y, rad, rad);

	}

	/**
	 * Fenster für Arcademodus anzeigen
	 */
	public void arcade() {
		basis();
		timer();
		p.text("Punkte: " + punkteSnakeOne, p.width - 300, 150);

	}

	/**
	 * Fenster für Zweispielermodis anzeigen
	 */
	public void twoPlayer() {
		basis();
		timer();
		p.text("Punkte: " + punkteSnakeTwo, 20, 150);
		p.text("Punkte: " + punkteSnakeOne, p.width - 300, 150);
	}

	/**
	 * Basisfenster anzeigen
	 */
	private void basis() {
		p.rectMode(0);
		snake = p.loadImage("SnakeSmall.png");
		p.image(snake, 0, 0, p.width, 200);
		p.frameRate(10);
		p.stroke(255);
		p.fill(100, 100, 100);
		p.rect(0, 200, p.width - 1, p.height - 200);
		p.textSize(48);
		p.fill(255);
	}

	/**
	 * Spieltimer
	 */
	private void timer() {
		countdown = (p.millis() - menuTime) / 1000;
		p.text("Time: " + countdown, 20, 50);
		if (countdown == MainSnake.timer) {
			gameState = 3;
		}
	}

	/**
	 * Highscore anzeigen
	 */
	public void highscore() {
		try {
			JOptionPane.showMessageDialog(null, "Highscores: \n" + FileInterpreter.scan());
		} catch (Exception e) {
			System.out.println("Fehler");
			e.printStackTrace();
		}
	}

	/**
	 * Punkteanzeige für Spieler
	 * 
	 * @param punkteSnakeOne
	 * @param punkteSnakeTwo
	 */
	public void pointsAusgabe(int punkteSnakeOne, int punkteSnakeTwo) {
		this.punkteSnakeOne = punkteSnakeOne;
		this.punkteSnakeTwo = punkteSnakeTwo;
	}

	/**
	 * Fenster für das Ende des Arcademoduses anzeigen
	 */
	public void fertigArcade() {
		mouse = new PVector(p.mouseX, p.mouseY);
		if (print == false) {
			FileInterpreter.print(spielerName, punkteSnakeOne);
			print = true;
		}
		p.fill(255);
		p.stroke(255);
		p.rectMode(0);
		p.rect(0, 200, p.width - 1, p.height - 200);
		p.fill(0);
		p.text("Fertig " + spielerName, btn1.x + rad, 400);
		p.text("Menu", btn1.x + rad, btn1.y + 20);
		p.text("Highscore", btn2.x + rad, btn2.y + 20);
		p.text("Quit", btn3.x + rad, btn3.y + 20);
		p.ellipse(btn1.x, btn1.y, rad, rad);
		p.ellipse(btn2.x, btn2.y, rad, rad);
		p.ellipse(btn3.x, btn3.y, rad, rad);
		if (p.mousePressed && mouse.dist(btn1) < rad) {
			gameState = 4;
		} else if (p.mousePressed && mouse.dist(btn2) < rad) {
			highscore();
		} else if (p.mousePressed && mouse.dist(btn3) < rad) {
			System.exit(0);
		}

	}

	/**
	 * Fenster für das Ende des Zweispielermodi anzeigen
	 */
	public void fertigTwoPlayer() {
		mouse = new PVector(p.mouseX, p.mouseY);
		p.fill(255);
		p.stroke(255);
		p.rectMode(0);
		p.rect(0, 200, p.width - 1, p.height - 200);
		p.fill(0);
		p.text("Fertig", btn1.x + rad, 400);
		p.text("Menu", btn1.x + rad, btn1.y + 20);
		p.text("Quit", btn2.x + rad, btn2.y + 20);
		p.ellipse(btn1.x, btn1.y, rad, rad);
		p.ellipse(btn2.x, btn2.y, rad, rad);
		if (p.mousePressed && mouse.dist(btn1) < rad) {
			gameState = 4;
		} else if (p.mousePressed && mouse.dist(btn2) < rad) {
			System.exit(0);
		}
	}

	/**
	 * Getters und Setters
	 */

	public int getSelectedMode() {
		return selectedMode;
	}

	public int getGameState() {
		return gameState;
	}

	public void setGameState(int gameState) {
		this.gameState = gameState;
	}

}
