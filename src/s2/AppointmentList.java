/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author neal
 */
public class AppointmentList {

    public static ArrayList<Appointment> getAppointments(ResultSet rs, ArrayList<Appointment> appointments) throws SQLException {
        rs.beforeFirst();
        while (rs.next()) {
            Appointment appointment = new Appointment(rs.getInt("appointmentId"), rs.getInt("customerId"),
                    rs.getInt("userId"), rs.getString("title"), rs.getString("description"), rs.getString("location"),
                    rs.getString("contact"), rs.getString("type"), rs.getString("url"), rs.getTimestamp("start"),
                    rs.getTimestamp("end"));
            appointments.add(appointment);
        }
        return appointments;
    }

    public static ArrayList<Appointment> getAll() throws SQLException {
        ResultSet rs = DBAppointment.getAll();
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();

        return AppointmentList.getAppointments(rs, appointments);
    }

    public static ObservableList getTabulated() throws SQLException {
        ArrayList<Appointment> list = AppointmentList.getAll();
        return FXCollections.observableArrayList(list);
    }

    public static ObservableList getTabulatedByDateRange(Timestamp start, Timestamp end) throws SQLException {
        ResultSet rs = DBAppointment.getByDateRange(start, end);
        ArrayList<Appointment> appointments = new ArrayList<>();
        AppointmentList.getAppointments(rs, appointments);
        return FXCollections.observableArrayList(appointments);
    }

    public static ObservableList getTabulatedTypeCountsByDateRange(Timestamp start, Timestamp end) throws SQLException {
        ArrayList<Appointment> appointments = new ArrayList<>();

        ResultSet rs = DBAppointment.getAppointmentTypeCountsByDateRange(start, end);
        rs.beforeFirst();
        while (rs.next()) {
            Appointment appointment = new Appointment(rs.getInt("appointmentId"), rs.getInt("customerId"),
                    rs.getInt("userId"), rs.getString("title"), rs.getString("description"), rs.getString("location"),
                    rs.getString("contact"), rs.getString("type"), rs.getString("url"), rs.getTimestamp("start"),
                    rs.getTimestamp("end"));
            appointment.setTypeCount(rs.getInt("typeCount"));
            appointments.add(appointment);
        }

        return FXCollections.observableArrayList(appointments);
    }

    public static ObservableList getAppointmentsByConsultant() throws SQLException {
        ResultSet rs = DBAppointment.getAppointmentsByConsultant();
        ArrayList<Appointment> appointments = new ArrayList<>();
        AppointmentList.getAppointments(rs, appointments);
        return FXCollections.observableArrayList(appointments);
    }
}
