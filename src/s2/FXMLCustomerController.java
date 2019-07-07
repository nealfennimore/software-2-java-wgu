/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s2;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author neal
 */
public class FXMLCustomerController implements Initializable {
    @FXML
    private TableView<Customer> customers;
    @FXML
    private TableColumn<Customer, String> customerId;
    @FXML
    private TableColumn<Customer, String> customerName;
    @FXML
    private TableColumn<Customer, String> active;

    private Customer getSelectedCustomer() {
        return customers.getSelectionModel().getSelectedItem();
    }

    private void renderCustomers() throws SQLException {
        ObservableList items = CustomerList.getTabulated();
        customers.setItems(items);
    }

    @FXML
    private void handleCustomerCreate(ActionEvent event) throws IOException {
        SceneLoader.loadCustomerCreate();
    }

    @FXML
    private void handleCustomerUpdate(ActionEvent event) throws IOException {
        Customer customer = getSelectedCustomer();
        if (customer != null) {
            StateManager.setActiveCustomer(customer);
            // TODO: Load update screen
        }
    }

    @FXML
    private void handleCustomerDelete(ActionEvent event) {
        Customer customer = getSelectedCustomer();

        try {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Do you want to set this customer as inactive?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() != ButtonType.OK) {
                throw new Exception("Cancelled.");
            } else if (customer == null) {
                throw new Exception("No customer");
            }
            DBCustomer.delete(customer.getCustomerId());
            renderCustomers();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        active.setCellValueFactory(new PropertyValueFactory<>("active"));
        try {
            renderCustomers();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
