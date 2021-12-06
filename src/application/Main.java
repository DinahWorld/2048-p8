package application;
/**
 * C'est le jeu 2048 avec la libraire JavaFX
 * @author Dinath
 * Université Paris 8 
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception{

		FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
		Parent root = loader.load();

		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("2048");
		primaryStage.show();
			
	}
		
	
	public static void main(String[] args) {
		launch(args);
	}
	

}
