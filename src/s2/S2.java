/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author neal
 */
public class S2 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        S2_i18n.setup();

        Parent root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
        
        Scene scene = new Scene(root);
        SceneController.setMain(scene);

        Database.setup();
        DatabaseSeeder.seed();
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
