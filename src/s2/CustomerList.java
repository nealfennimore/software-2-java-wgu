/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author neal
 */
public class CustomerList {

    public static ArrayList<Customer> getAll() throws SQLException {
        ResultSet rs = DBCustomer.getAll();
        ArrayList<Customer> customers = new ArrayList<Customer>();

        rs.beforeFirst();
        while(rs.next()) {
            Customer customer = new Customer(
                rs.getInt("customerId"),
                rs.getString("customerName"),
                rs.getInt("addressId"),
                rs.getInt("active")
            );      
            customers.add(customer);
        }

        return customers;
    }

    public static ObservableList getTabulated() throws SQLException {
        ArrayList<Customer> list = CustomerList.getAll();
        return FXCollections.observableArrayList(list);
    }
}
