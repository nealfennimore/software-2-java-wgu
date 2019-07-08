/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;   

/**
 * FXML Controller class
 *
 * @author neal
 */
public class FXMLLoginController implements Initializable {

    @FXML TextField userName;
    @FXML TextField password;

    private void alertBadLogin() {
        try {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(S2_i18n.getString("bad_login_title"));
            alert.setContentText(S2_i18n.getString("bad_login"));

            alert.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void alertNoInput() {
        try {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(S2_i18n.getString("no_login_input_title"));
            alert.setContentText(S2_i18n.getString("no_login_input"));

            alert.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void reportUserLogin(String user) {
        Logger logger = Logger.getLogger("log.txt");

        try {
            FileHandler fh = new FileHandler("log.txt", true);
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
            logger.addHandler(fh);
            logger.setLevel(Level.INFO);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (SecurityException ex) {
            System.out.println(ex.getMessage());
        }
        logger.log(Level.INFO, String.format("User logged in: %s", user));
    }

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String user = userName.getText();
        String pass = password.getText();

        if( Utils.isEmptyString(user) || Utils.isEmptyString(pass)  ){
            alertNoInput();
            return;
        }

        boolean isLoggedIn = DBUser.login(user, pass);
        System.out.println(isLoggedIn);
        reportUserLogin(user);  

        if (isLoggedIn) {
            SceneLoader.loadManagement();
        } else {
            alertBadLogin();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
