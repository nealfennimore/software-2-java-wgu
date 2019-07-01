/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

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

    @FXML
    private void handleLogin(ActionEvent event) {
        String user = userName.getText();
        String pass = password.getText();

        if( Utils.isEmptyString(user) || Utils.isEmptyString(pass)  ){
            alertNoInput();
            return;
        }

        boolean isLoggedIn = User.login(user, pass);
        System.out.println(isLoggedIn);

        if (isLoggedIn) {
            
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
