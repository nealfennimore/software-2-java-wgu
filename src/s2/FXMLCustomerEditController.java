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
public class FXMLCustomerEditController implements Initializable {

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
        Customer customer = StateManager.getActiveCustomer();
        Address customerAddress = customer.getAddress();
        int addressId = customerAddress.getAddressId();
          
        boolean isValid = CustomerValidator.validate(
            customerName.getText(),
            address.getText(),
            address2.getText(),
            city.getText(),
            country.getText(),
            postalCode.getText(),
            phone.getText()
        );

        if (isValid) {
            int countryId = DBCountry.createOrSelect(country.getText());
            int cityId = DBCity.createOrSelect(city.getText(), countryId);
            DBAddress.update(
                addressId,
                address.getText(),
                address2.getText(),
                cityId,
                postalCode.getText(),
                phone.getText()
            );
            DBCustomer.update(
                customer.getCustomerId(),
                customerName.getText(),
                addressId
            );
            SceneLoader.loadManagement();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("INVALID");
            alert.setContentText("Invalid customer input.");
            alert.showAndWait();
        }
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
            SceneLoader.loadManagement();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Customer customer = StateManager.getActiveCustomer();
        Address customerAddress = customer.getAddress();
        City customerCity = customerAddress.getCity();
        Country customerCountry = customerCity.getCountry();

        customerName.setText(customer.getCustomerName());
        address.setText(customerAddress.getAddress());
        address2.setText(customerAddress.getAddress2());
        city.setText(customerCity.getCity());
        postalCode.setText(customerAddress.getPostalCode());
        country.setText(customerCountry.getCountry());
        phone.setText(customerAddress.getPhone());
    }
}
