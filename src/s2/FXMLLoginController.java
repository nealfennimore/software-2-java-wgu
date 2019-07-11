/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s2;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author neal
 */
public class FXMLLoginController implements Initializable {

    @FXML TextField userName;
    @FXML TextField password;
    @FXML Button loginButton;
    @FXML Label loginLabel;
    @FXML Label userNameLabel;
    @FXML Label passwordLabel;

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
    private void handleAppointmentWithin15Minutes() {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusMinutes(15);
        ResultSet rs = DBAppointment.getBetweenDateRange(Timestamp.valueOf(startDate), Timestamp.valueOf(endDate));

        try {
            if (rs.first()) {
                Appointment appointment = new Appointment(
                    rs.getInt("appointmentId"),
                    rs.getInt("customerId"),
                    rs.getInt("userId"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("location"),
                    rs.getString("contact"),
                    rs.getString("type"),
                    rs.getString("url"),
                    rs.getTimestamp("start"),
                    rs.getTimestamp("end")
                );
                Customer customer = appointment.getCustomer();
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Appointment");
                alert.setContentText(String.format("You have a meeting with %s", customer.getCustomerName()));
                alert.showAndWait();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
        reportUserLogin(user);  

        if (isLoggedIn) {
            handleAppointmentWithin15Minutes();
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
        loginButton.setText(S2_i18n.getString("login"));
        loginLabel.setText(S2_i18n.getString("login"));
        passwordLabel.setText(S2_i18n.getString("password"));
        userNameLabel.setText(S2_i18n.getString("username"));
    }
}
