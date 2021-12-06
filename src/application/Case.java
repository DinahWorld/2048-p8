package application;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class Case extends Game {
	private boolean vide;
	private int nombre;
	private Label label;
	private boolean transfo;

	private int r;
	private int g;
	private int b;

	public Case(Label strNombre) {
		this.r = 99;
		this.g = 69;
		this.b = 227;

		this.label = strNombre;
		this.label.setText("");
		this.transfo = false;
		this.vide = true;
		this.nombre = 0;
	}

	//Méthode qui met par défaut les différent label et attribut
	public void defaut() {
		this.r = 99;
		//On cache le label pour que l'animation ne soit pas obstrué par un label visible
		this.label.setVisible(false);
		this.label.setFont(Font.font("System", FontWeight.BOLD, super.getTaillePolice()));
		this.vide = true;
		this.transfo = false;
	}

	//Méthode qui va ajouter un chiffre lorsque l'on va crée des cases random
	public void ajoutChiffre(int chiffre) {
		this.label.setVisible(true);
		this.label.setStyle("-fx-background-color: #5648E3; -fx-background-radius: 100 100 100 100; -fx-border-radius: 100 100 100 100;");
		this.vide = false;
		this.transfo = false;
		this.nombre = chiffre;
		this.label.setText(String.valueOf(nombre));
	}

	//Méthode qui multiplie la valeur de case par 2 
	public void multNombre() {
		this.label.setVisible(true);
		this.nombre *= 2;
		if (this.nombre != 4) {
			//Seuls les nombres apres 4 auront une modification des couleurs
			this.r += 30;
			String text = "-fx-background-color:rgb(" + String.valueOf(this.r) + ", " + String.valueOf(this.g) + ", " + String.valueOf(this.b) + ");" + "-fx-background-radius: 100 100 100 100; " + "-fx-border-radius: 100 100 100 100;";
			this.label.setStyle(text);
		}

		this.label.setText(String.valueOf(this.nombre));
		super.addScore(this.nombre);
		this.transfo = true;
	}

	//Méthoqui change l'état d'une case en l'état d'une autre case
	public void changeEtat(Case bloc) {
		//Le label devient visible
		this.label.setVisible(true);

		this.r = bloc.getR();
		this.g = bloc.getG();
		this.b = bloc.getB();
		this.label.setStyle(bloc.getLabel().getStyle());

		this.vide = false;
		this.transfo = false;
		this.nombre = bloc.getNombre();

		this.label.setText(String.valueOf(nombre));
	}

	//!--Des Mutateurs--!

	public void estVide() {
		this.vide = true;
	}
	public void nonVide() {
		this.vide = false;
	}
	public void estTransfo() {
		this.transfo = true;
	}
	public void nonTransfo() {
		this.transfo = false;
	}

	//!--Des Accesseurs--!
	public boolean getVide() {
		return vide;
	}
	public int getNombre() {
		return nombre;
	}
	public boolean getTransfo() {
		return transfo;
	}
	public Label getLabel() {
		return label;
	}
	public int getR() {
		return r;
	}
	public int getG() {
		return g;
	}
	public int getB() {
		return b;
	}
}