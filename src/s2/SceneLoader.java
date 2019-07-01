/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s2;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

/**
 * FXML Controller class
 *
 * @author neal
 */
public class SceneLoader {

    @FXML
    private static void load(String resource) throws IOException {
        SceneController.load(FXMLLoader.load(S2.class.getResource(resource)));
    }
    
    @FXML
    public static void loadLogin() throws IOException {
        load("FXMLLogin.fxml");
    }

    @FXML
    public static void loadDocument() throws IOException {
        load("FXMLDocument.fxml");
    }
}
