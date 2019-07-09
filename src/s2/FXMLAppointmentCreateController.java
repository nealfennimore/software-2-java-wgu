/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s2;

import java.io.IOException;
import java.sql.Timestamp;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author neal
 */
public class FXMLAppointmentCreateController implements Initializable {

    @FXML
    TextField title;
    @FXML
    TextField description;
    @FXML
    TextField location;
    @FXML
    TextField contact;
    @FXML
    TextField type;
    @FXML
    TextField url;
    @FXML
    TextField start;
    @FXML
    TextField end;
    @FXML
    ChoiceBox customers;

    private Customer getSelectedCustomer() {
        return (Customer) customers.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void handleSave(ActionEvent event) throws IOException {

        LocalDateTime startDateTime = LocalDateTime.parse(start.getText());
        LocalDateTime endDateTime = LocalDateTime.parse(end.getText());

        boolean isWithinBusinessHours = ScheduleValidator.isWithinBusinessHours(startDateTime, endDateTime);
        boolean hasOverlappingAppointments = ScheduleValidator.hasOverlappingAppointments(startDateTime, endDateTime);

        if ( ! isWithinBusinessHours ){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("Appointment is not within business hours.");

            alert.showAndWait();
        } else if ( hasOverlappingAppointments ) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("Schedule conflict with another appointment.");

            alert.showAndWait();
        } else {
            DBAppointment.create(
                getSelectedCustomer().getCustomerId(),
                1,
                title.getText(),
                description.getText(),
                location.getText(),
                contact.getText(),
                type.getText(),
                url.getText(),
                Timestamp.valueOf( startDateTime ),
                Timestamp.valueOf( endDateTime )
            );
    
            SceneLoader.loadManagement();
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
        customers.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer customer) {
                return customer.getCustomerName();
            }
            @Override
            public Customer fromString(String s) {
                return null ;
            }
        });

        try {
            customers.getItems().setAll(CustomerList.getTabulated());
            customers.getSelectionModel().selectFirst();
            start.setText( LocalDateTime.now().toString() );
            end.setText( LocalDateTime.now().plusDays(1).toString() );
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAppointmentCreateController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
