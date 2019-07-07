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
public class AppointmentList {

    public static ArrayList<Appointment> getAll() throws SQLException {
        ResultSet rs = DBAppointment.getAll();
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();

        rs.beforeFirst();
        while(rs.next()) {
            Appointment apointment = new Appointment(
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
            appointments.add(apointment);
        }

        return appointments;
    }

    public static ObservableList getTabulated() throws SQLException {
        ArrayList<Appointment> list = AppointmentList.getAll();
        return FXCollections.observableArrayList(list);
    }
}
