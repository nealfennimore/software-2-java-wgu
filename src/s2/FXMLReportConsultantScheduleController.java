/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s2;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author neal
 */
public class FXMLReportConsultantScheduleController implements Initializable {
    @FXML
    private TableView<Appointment> appointments;
    @FXML
    private TableColumn<Appointment, String> customerId;
    @FXML
    private TableColumn<Appointment, String> title;
    @FXML
    private TableColumn<Appointment, String> start;
    @FXML
    private TableColumn<Appointment, String> end;


    private void renderAppointments() throws SQLException {
        ObservableList items = AppointmentList.getAppointmentsByConsultant();
        appointments.setItems(items);
    }

    @FXML
    private void handleReportAppointmentTypes(ActionEvent event) throws IOException {
        SceneLoader.loadReportAppointmentTypes();
    }

    @FXML
    private void handleReportConsultantSchedule(ActionEvent event) throws IOException {
        SceneLoader.loadReportConsultantSchedule();
    }

    @FXML
    private void handleReportAllAppointments(ActionEvent event) throws IOException {
        SceneLoader.loadReportAllAppointments();
    }

    @FXML
    private void handleManagement(ActionEvent event) throws IOException {
        SceneLoader.loadManagement();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
        try {
            renderAppointments();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
