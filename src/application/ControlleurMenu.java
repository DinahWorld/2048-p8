package application;

import java.io.IOException;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;

/**
 * C'est le jeu 2048 avec la libraire JavaFX
 * @author Dinath
 * Université Paris 8 
 */


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class ControlleurMenu extends Main{

	@FXML 
	public Label jouer,quitter,titre;

	@FXML private AnchorPane window;
	
	
	public void initialize() {
		window.setStyle("-fx-background-color: #1F252D");
		animation(titre,8);
	}
	
	/*
	 * Méthode qui, lorsque le joueur appuiera sur Jouer, on execute le fichier fxml du jeu
	 */
	public void choixJouer(MouseEvent Action) throws IOException {
		
        //Les instructions suivantes ne s'effectueront qu'une fois que l'animation soit terminé
		animation(jouer,2).setOnFinished(event -> {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("2048.fxml"));
    		Parent root;
			try {
				root = loader.load();
				ControlleurGame controller = loader.getController();
	    		
	    		Stage stage = (Stage) window.getScene().getWindow();
	    		Scene scene = new Scene(root);
	    		
	    		controller.keyEvent(scene);
	    		
	    		stage.setScene(scene);
	    		stage.show();
			} catch (IOException e) {
			
				e.printStackTrace();
			}

        });
	}
	
	//Méthode qui quitte le jeu, une fois le joueur appuiera sur Quitter
	public void choixQuitter(MouseEvent Action){
		animation(quitter,2).setOnFinished(event -> {
        	Platform.exit();
  	      	System.exit(0);
        }); 
	}
	
	public ScaleTransition animation(Label label,int count) {
		ScaleTransition taille = new ScaleTransition(Duration.millis(150), label);
		taille.setFromX(1);
        taille.setFromY(1);
        taille.setToX(1.2);
        taille.setToY(1.2);
        taille.setCycleCount(count);
        taille.setAutoReverse(true);
        taille.play();
        
		return taille;
	}
	
}
