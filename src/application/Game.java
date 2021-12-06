package application;

import javafx.scene.control.Label;

public class Game {
	private Label labelMeilleurScore;
	private Label labelScore;
	private Label labelScoreBas;
	private Label niveau;
	public static int score = 0;
	public static int taillePolice = 53;
	private int niveauInt;

	public Game() {
		this.niveauInt = 0;
	}

	public Game(Label labelMeilleurScore, Label labelScore, Label labelScoreBas, Label niveau) {
		this.labelMeilleurScore = labelMeilleurScore;
		this.labelScore = labelScore;
		this.labelScoreBas = labelScoreBas;
		this.niveau = niveau;
		this.labelScore.setText("0");
		this.labelScoreBas.setText("");
		this.niveauInt = 4;

	}

	//Méthode qui incrémente le niveau et la taille de la Police
	public void levelUP() {
		this.niveauInt += 1;

		if (niveauInt < 9) taillePolice -= 10;
		else taillePolice = 10;

		this.niveau.setText("Niveau : " + getIntNiveau() + "x" + getIntNiveau());
	}

	//Méthode qui remet à zero le score et les différents labels
	public void resetScore() {
		score = 0;
		this.labelScore.setText("0");
		this.labelScoreBas.setText("");
	}

	//Méthode qui augmente le score en fonction du nombre
	public void addScore(int nombre) {
		score += nombre;
	}

	//!--Des Mutateurs--! 
	public void setScoreText(int score) {
		this.labelScore.setText(String.valueOf(score));
	}

	public void setMeilleurScoreText(String meilleurScore) {
		this.labelMeilleurScore.setText(meilleurScore);
	}

	public void setScoreTextBas() {
		this.labelScore.setText("Bravo");
		this.labelScoreBas.setText(String.valueOf("Score : " + this.score));
	}

	//!--Des Accesseurs--!
	public int getLevel() {
		return niveauInt;
	}

	public int getTaillePolice() {
		return taillePolice;
	}

	public int getScore() {
		return score;
	}
	public Label getLabelScore() {
		return labelScore;
	}
	public int getMeilleurScore() {
		return Integer.parseInt(labelMeilleurScore.getText());
	}
	public String getIntNiveau() {
		return String.valueOf(this.niveauInt);
	}
}