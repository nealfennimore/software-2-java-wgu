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
    public static void loadManagement() throws IOException {
        load("FXMLManagement.fxml");
    }

    @FXML
    public static void loadCustomerCreate() throws IOException {
        load("FXMLCustomerCreate.fxml");
    }

    @FXML
    public static void loadCustomerEdit() throws IOException {
        load("FXMLCustomerEdit.fxml");
    }

    @FXML
    public static void loadAppointmentCreate() throws IOException {
        load("FXMLAppointmentCreate.fxml");
    }

    @FXML
    public static void loadAppointmentEdit() throws IOException {
        load("FXMLAppointmentEdit.fxml");
    }

    @FXML
    public static void loadReportAppointmentTypes() throws IOException {
        load("FXMLReportAppointmentTypes.fxml");
    }

    @FXML
    public static void loadReportConsultantSchedule() throws IOException {
        load("FXMLReportConsultantSchedule.fxml");
    }

    @FXML
    public static void loadReportAllAppointments() throws IOException {
        load("FXMLReportAllAppointments.fxml");
    }
}
