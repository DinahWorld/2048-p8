package application;

/**
 * C'est le jeu 2048 avec la libraire JavaFX
 * @author Dinath
 * Université Paris 8 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;


public class ControlleurGame extends Main {

    @FXML
    public List < Label > labelArray;

    @FXML
    public Case[][] bloc;

    @FXML
    public AnchorPane myAnchorPane;

    @FXML
    public GridPane tableauDeCase;

    @FXML
    public Label score, niveau, entrer, meilleurScore, botScore;

    @FXML
    public HBox haut;

    //La classe Score va contenir le meilleur score et le score en cours
    public Game oScore;

    //Attribut qui indiquera le nombre de colonnes
    public int nbreColl;

    //Attribut qui va compter chaque case remplie
    public int verif;


    /*
     * Cet attribut sera true lorsqu'on aura besoin de verifier s'il y a toujours un mouvement disponible 
     * sinon elle sera false
     */
    public boolean check;


    //On initialise nos attributs
    public void initialize() throws FileNotFoundException, IOException {
        nbreColl = 4;
        check = false;
        verif = 0;

        //On crée un fichier "bestScore", s'il existe on ne le crée pas
        new FileOutputStream("bestScore.txt", true).close();
        
        //Cette classe contiendra tout nos scores
        oScore = new Game(meilleurScore, score, botScore, niveau);

        //On ajoute les couleurs
        myAnchorPane.setStyle("-fx-background-color: #1F252D");
        haut.setStyle("-fx-background-color: #1F252D");
        niveau.setText("Niveau 4x4");

        

        //Ce tableau contiendra tout les labels (cases) du jeu
        bloc = new Case[30][30];

        for (int y = 0, count = 0; y < nbreColl; y++) {
            for (int x = 0; x < nbreColl; x++) {
                bloc[y][x] = new Case(labelArray.get(count++));
            }
        }

        //On aura plus besoin de cette liste.
        labelArray.clear();

        //Cette methode va mettre le meilleur score sur l'interface graphique
        mettreMeilleurScore();

        //On apelle la methode random qui va mettre au hasard un chiffre, sur deux cases au hasard
        random(2);




    }

    //Methode qui en fonction des touches presse par l'utilisateur va executer une methode
    public void keyEvent(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT -> {
                    if (check != true) movGauche();random(1);
                }
                case RIGHT -> {
                    if (check != true) movDroite();random(1);
                }
                case UP -> {
                    if (check != true) movHaut();random(1);
                }
                case DOWN -> {
                    if (check != true) movBas();random(1);
                }
                case ENTER -> {
                	//Redemarre le jeu
                    reset();animation(entrer, 1, 1.3, 2, true, 200);
                }
                case SPACE -> {
                	//Augmente le niveau
                    if (nbreColl != 30) ajoutNiveau();
                }
                case ESCAPE -> {
                	//Quitte le jeu
                	Platform.exit();
          	      	System.exit(0);
                }
                case F1 -> {
                	//Active ou non les lignes de la grille
                	if(tableauDeCase.isGridLinesVisible() == false) {
                		 tableauDeCase.setGridLinesVisible(true);
                	}else {
                		 tableauDeCase.setGridLinesVisible(false);
                	}
                }
            }
        });

    }

    //Methoque qui va animer les différents éléments 
    public void animation(Label label, int from, double to, int cyclecount, boolean bool, int duree) {
        
       //Cette methode va  modifier la taille des labels en fonction des parametre donnee
        ScaleTransition taille = new ScaleTransition(Duration.millis(duree), label);
        taille.setFromX(from);
        taille.setFromY(from);
        taille.setToX(to);
        taille.setToY(to);
        taille.setCycleCount(cyclecount);
        taille.setAutoReverse(bool);
        taille.play();
    }


    public void mettreMeilleurScore() {
        //On recupere notre fichier où est ecrit le meilleur score.
        try {
            File fichier = new File("bestScore.txt");
            Scanner lireFichier = new Scanner(fichier);
            while (lireFichier.hasNextLine()) {
                String data = lireFichier.nextLine();

                //Une fois le score du fichier txt récupéré, je le met dans le label 
                oScore.setMeilleurScoreText(data);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erreur : fichier");
            e.printStackTrace();
        }
    }


    //Lorsque le joueur voudra recommencer une partie, on remet les differents parametre a 0
    public void reset() {
        verif = 0;
        check = false;
        //Les cases seront remis a l'etat initial
        for (int y = 0; y < nbreColl; y++) {
            for (int x = 0; x < nbreColl; x++) {
                bloc[y][x].defaut();
            }
        }
        random(2);
        oScore.resetScore();
        mettreMeilleurScore();
    }


    //Methode qui verifie s'il reste des mouvements disponible dans la partie
    public void checkToutLesMove() {

        /*
         * On met l'attribut check sur true qui va faire en sorte que le programme principale
         * ne bouge pas les differentes cases lors des verifications
         */

        check = true;

        movGauche();
        if (check != false) movDroite();
        if (check != false) movHaut();
        if (check != false) movBas();

        //Si elle est false on arrete la partie
        if (check != false) {

            /*
             * On compare si le score de la partie est superieur au meilleur score
             * Si elle l'est, on rentre ce score dans notre fichier texte
             */

            if (oScore.getScore() > oScore.getMeilleurScore()) {
                try {
                    FileWriter fichier = new FileWriter("bestScore.txt");
                    fichier.write(String.valueOf(oScore.getScore()));
                    fichier.close();
                } catch (IOException e) {
                    System.out.println("Erreur");
                    e.printStackTrace();
                }
            }

            oScore.setScoreTextBas();
            animation(oScore.getLabelScore(), 1, 1.4, 6, true, 200);
        }
    }


  //Methode qui déplace les cases en bas
    public void movBas() {

        /*
         * Cette methode consiste à envoyer à la methode "programmePrincipale", chaque information du label du tableau 
         * Pour que la methode puisse utiliser le label en question
         */

        loop: for (int x = 0; x < nbreColl; x++) {
            for (int y = nbreColl - 2; y >= 0; y--) {
                y = programmePrincipale(y, x, +1, 0, nbreColl - 2, y);
                if (y == -10) break loop;
            }
        }
        aZero();
    }
    //Methode qui déplace les cases en haut
    public void movHaut() {

        /*
         * Cette methode consiste à envoyer à la methode "programmePrincipale", chaque information du label du tableau 
         * Pour que la methode puisse utiliser le label en question	
         */

        loop: for (int x = 0; x < nbreColl; x++) {
            for (int y = 1; y < nbreColl; y++) {
                y = programmePrincipale(y, x, -1, 0, 1, y);
                if (y == -10) break loop;
            }
        }
        aZero();
    }
    //Methode qui déplace les cases à gauche
    public void movGauche() {

        /*
         * Cette methode consiste à envoyer à la methode "programmePrincipale", chaque information du label du tableau 
         * Pour que la methode puisse utiliser le label en question
         */

        loop: for (int y = 0; y < nbreColl; y++) {
            for (int x = 1; x < nbreColl; x++) {
                x = programmePrincipale(y, x, 0, -1, 1, x);
                if (x == -10) break loop;
            }
        }
        aZero();
    }
    //Methode qui déplace les cases à doite
    public void movDroite() {
        /*
         * Cette methode consiste à envoyer à la methode "programmePrincipale", chaque information du label du tableau 
         * Pour que la methode puisse utiliser le label en question
         */
        loop: for (int y = 0; y < nbreColl; y++) {
            for (int x = nbreColl - 2; x >= 0; x--) {
                x = programmePrincipale(y, x, 0, +1, nbreColl - 2, x);
                if (x == -10) break loop;
            }
        }
        aZero();
    }
    //On parcourt tout le tableau afin que chaque case soit a un etat "non transforme"
    public void aZero() {
        for (int y = 0; y < nbreColl; y++) {
            for (int x = 0; x < nbreColl; x++) {
                bloc[x][y].nonTransfo();
            }
        }
    }


    //Cette methode va gerer les deplacements des cases
    private int programmePrincipale(int y, int x, int y2, int x2, int limit, int i) {
        /*
         * On recupere la valeur de la case à l'index qu'on a reçu
         * et on recupere aussi le nombre de la case suivante ou precedante 
         */
        int nombre = bloc[y][x].getNombre();
        int nombre2 = bloc[y + y2][x + x2].getNombre();

        //Si la case qu'on a eut n'est pas vide
        if (bloc[y][x].getVide() != true) {
            /*
             * On verifie si la case precedante ou suivante (en fonction du mouvement)
             * est vide ou pas. Si elle l'est, on deplace la valeur de la case reçu à cette case
             * suivante ou precedante
             * 
             * Une fois que l'etat de la case suivante ou precedante ait été modifié
             * Et qu'elle correspond à la case qu'on a reçu, on remet à l'etat initial la case
             * 
             * Une fois deplace, on veut aller à l'index où on a deplacer la case
             * Donc en fonction l'attribut limit qui si elle est egale à 1 se situe à gauche
             * On renvoie i - 1 qui va correspondre une fois sortie du programme
             * à l'index de la case suivante 
             */

            if (bloc[y + y2][x + x2].getVide() == true) {

                bloc[y + y2][x + x2].changeEtat(bloc[y][x]);
                bloc[y][x].defaut();

                if (i != limit)
                    return limit == 1 ? i - 2 : i + 2;
            } else if (nombre == nombre2 && bloc[y + y2][x + x2].getTransfo() != true) {
                /*
                 * Si la valeur de la case qu'on a est egale a la case suivante ou precedante,
                 * et que la case suivante ou precedante n'a pas eu de modification
                 * on les additionne
                 */

                //On verifie si le programme veut qu'on les déplace ou pas
                if (check != true) {
                    /*
                     * Une fois que l'etat de la case suivante ou precedante  ait ete modifie
                     * Et qu'elle correspond à la case qu'on a reçu, on remet à l'etat initial la case
                     */

                    bloc[y + y2][x + x2].multNombre();
                    oScore.setScoreText(oScore.getScore());

                    //Une methode qui va animer le score
                    animation(oScore.getLabelScore(), 1, 1.1, 2, true, 200);
                    bloc[y][x].defaut();
                    verif -= 1;

                    //Une methode qui va animer la case
                    animation(bloc[y + y2][x + x2].getLabel(), 1, 1.1, 2, true, 150);

                } else {
                    /*
                     * Si le programme ne veut juste verifier si il peut y avoir des changements
                     * on renvoit false et l'attribut check sera false
                     */
                    check = false;
                    return -10;
                }

            }
        }
        /*
         * En fonction de la valeur de i si elle egale a y on renvoit
         * sinon on renvoi x
         * 
         * Elle va permettre au boucle des methodes mouv de parcourir normalement le tableau
         */
        return i == y ? y : x;
    }

    //Méthode qui permet de remplir au hasard une ou deux cases de chiffre
    public void random(int limit) {

        //Tant que l'attribut verif n'est pas égales aux nombres de cases on continue le programme
        if (verif != nbreColl * nbreColl) {
            Random rand = new Random();
            int n = 0;

            //A chaque case avec un chiffre généré on incrémente n, limit correspond à combien de case on veut
            while (n != limit) {

                //On crée deux variables aléatoires de 0 à au nombres de colonnes
                int randomPos_x = rand.nextInt(nbreColl);
                int randomPos_y = rand.nextInt(nbreColl);

                //Si la case aux coordonnée random sont vides on lui ajoute un chiffre
                if (bloc[randomPos_y][randomPos_x].getVide() == true) {

                    /*
                     * Pour qu'il choississe un chiffre entre 2 et 4 on genere une réponse true ou false
                     * si elle est true, on met le chiffre 2 sinon 4
                     */

                    bloc[randomPos_y][randomPos_x].ajoutChiffre(rand.nextBoolean() == true ? 2 : 4);
                    animation(bloc[randomPos_y][randomPos_x].getLabel(), 0, 1, 0, false, 200);
                    verif += 1;
                    n++;

                    /*
                     * Si l'attribut verif est égales aux nombres de cases du jeu
                     * On regarde s'il existe un mouvement disponible
                     */
                    if (verif == nbreColl * nbreColl) checkToutLesMove();
                }
            }
        }
    }

    //Méthode qui ajoute une ligne et une colonne dans le jeu
    public void ajoutNiveau() {

        check = false;
        nbreColl += 1;
        oScore.levelUP();
        animation(niveau, 1, 1.2, 2, true, 200);

        //On crée les lignes et colonnes et on définit les parametres
        ColumnConstraints colConst = new ColumnConstraints();
        RowConstraints rowConst = new RowConstraints();

        colConst.setMinWidth(10.0);
        colConst.setPrefWidth(100.0);
        colConst.setHgrow(Priority.SOMETIMES);


        rowConst.setMinHeight(10);
        rowConst.setPrefHeight(30);
        rowConst.setVgrow(Priority.SOMETIMES);

        tableauDeCase.getColumnConstraints().add(colConst);
        tableauDeCase.getRowConstraints().add(rowConst);

 
        //Je crée un label pour case 
        for (int i = 0; i < nbreColl; i++) {
            Label label = new Label();
            label.setFont(Font.font("System", FontWeight.BOLD, oScore.getTaillePolice()));
            label.setAlignment(Pos.CENTER);
            label.setPrefHeight(176.0);
            label.setPrefWidth(228.0);
            tableauDeCase.add(label, i, nbreColl - 1);
            bloc[nbreColl - 1][i] = new Case(label);
            
            Label label2 = new Label();
            label2.setFont(Font.font("System", FontWeight.BOLD, oScore.getTaillePolice()));
            label2.setAlignment(Pos.CENTER);
            label2.setPrefHeight(176.0);
            label2.setPrefWidth(228.0);
            tableauDeCase.add(label2, nbreColl - 1, i);
            bloc[i][nbreColl - 1] = new Case(label2);
        }

        reset();
    }

}