/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s2;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author neal
 */
public class FXMLAppointmentEditController implements Initializable {

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

    public int getIndex(ArrayList<Customer> list, int customerId) {
        for (int i = 0; i < list.size(); i++) {
            Customer customer = list.get(i);
            if ( customerId == customer.getCustomerId() ) {
                return i;
            }
        }

        return -1;
    }

    @FXML
    private void handleSave(ActionEvent event) throws IOException {
        Appointment appointment = StateManager.getActiveAppointment();

        LocalDateTime startDateTime = Timestamp.valueOf( start.getText() ).toLocalDateTime();
        LocalDateTime endDateTime = Timestamp.valueOf( end.getText() ).toLocalDateTime();

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
            DBAppointment.update(
                appointment.getAppointmentId(),
                getSelectedCustomer().getCustomerId(),
                1,
                title.getText(),
                description.getText(),
                location.getText(),
                contact.getText(),
                type.getText(),
                url.getText(),
                Timestamp.valueOf(start.getText()),
                Timestamp.valueOf(end.getText())
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
    public void initialize(URL _url, ResourceBundle rb) {
        Appointment appointment = StateManager.getActiveAppointment();

        customers.setConverter(new StringConverter<Customer>() {
            @Override
            public String toString(Customer customer) {
                return customer.getCustomerName();
            }

            @Override
            public Customer fromString(String s) {
                return null;
            }
        });

        try {
            int index = getIndex(CustomerList.getAll(), appointment.getCustomerId());
            System.out.println(index);
            customers.getItems().setAll(CustomerList.getTabulated());
            customers.getSelectionModel().select(index);
            title.setText(appointment.getTitle());
            description.setText(appointment.getDescription());
            location.setText(appointment.getLocation());
            contact.setText(appointment.getContact());
            type.setText(appointment.getType());
            url.setText(appointment.getUrl());
            start.setText(appointment.getStart().toString());
            end.setText(appointment.getEnd().toString());
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAppointmentCreateController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
