/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s2;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
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
public class FXMLReportAppointmentTypesController implements Initializable {
    @FXML
    private TableView<Appointment> appointments;
    @FXML
    private TableColumn<Appointment, String> type;
    @FXML
    private TableColumn<Appointment, String> typeCount;

    private void renderAppointments() throws SQLException {
        LocalDateTime startDateTime = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime endDateTime = startDateTime.with(TemporalAdjusters.lastDayOfMonth());
        ObservableList items = AppointmentList.getTabulatedTypeCountsByDateRange(Timestamp.valueOf(startDateTime),
                Timestamp.valueOf(endDateTime));
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
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeCount.setCellValueFactory(new PropertyValueFactory<>("typeCount"));
        try {
            renderAppointments();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
