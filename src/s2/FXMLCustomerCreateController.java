/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s2;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author neal
 */
public class FXMLCustomerCreateController implements Initializable {

    @FXML
    TextField customerName;
    @FXML
    TextField address;
    @FXML
    TextField address2;
    @FXML
    TextField city;
    @FXML
    TextField postalCode;
    @FXML
    TextField country;
    @FXML
    TextField phone;

    @FXML
    private void handleSave(ActionEvent event) throws IOException {

        int countryId = DBCountry.createOrSelect(country.getText());
        int cityId = DBCity.createOrSelect(city.getText(), countryId);
        int addressId = DBAddress.create(
            address.getText(),
            address2.getText(),
            cityId,
            postalCode.getText(),
            phone.getText()
        );
        DBCustomer.create(
            customerName.getText(),
            addressId
        );
        SceneLoader.loadCustomer();
    }

    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        try {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Leave this screen?");
            alert.setContentText("Any unsaved changes will be lost.");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() != ButtonType.OK) {
                throw new Exception("Cancelled.");
            }
            SceneLoader.loadCustomer();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
