/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author neal
 */
public class DBAppointment {

    public static ResultSet get(int appointmentId) {
        ResultSet rs = null;

        try {
            PreparedStatement ps = DatabaseQuery.prepare("SELECT * FROM appointment WHERE appointmentId = ?");

            ps.setInt(1, appointmentId);
            rs = ps.executeQuery();
            rs.first();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }

    public static ResultSet getAll() {
        return DatabaseQuery.select("SELECT * FROM appointment");
    }

    public static ResultSet getByDateRange(Timestamp start, Timestamp end){
        ResultSet rs = null;

        try {
            PreparedStatement ps = DatabaseQuery.prepare(
                "SELECT * FROM appointment WHERE start >= ? AND end <= ?"
            );

            ps.setTimestamp(1, start);
            ps.setTimestamp(2, end);
            rs = ps.executeQuery();
            rs.first();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }

    public static ResultSet getAppointmentTypeCountsByDateRange(Timestamp start, Timestamp end){
        ResultSet rs = null;

        try {
            PreparedStatement ps = DatabaseQuery.prepare(
                "SELECT *, COUNT(*) AS typeCount FROM appointment WHERE start >= ? AND end <= ? GROUP BY type"
            );

            ps.setTimestamp(1, start);
            ps.setTimestamp(2, end);
            rs = ps.executeQuery();
            rs.first();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }

    public static ResultSet getAppointmentsByConsultant(){
        ResultSet rs = null;

        try {
            PreparedStatement ps = DatabaseQuery.prepare(
                "SELECT * FROM appointment ORDER BY customerId ASC, start ASC"
            );

            rs = ps.executeQuery();
            rs.first();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }

    public static ResultSet getBetweenDateRange(Timestamp start, Timestamp end){
        ResultSet rs = null;

        try {
            PreparedStatement ps = DatabaseQuery.prepare(
                "SELECT * FROM appointment WHERE (start BETWEEN ? AND ?) OR (end BETWEEN ? AND ?)"
            );

            ps.setTimestamp(1, start);
            ps.setTimestamp(2, end);
            ps.setTimestamp(3, start);
            ps.setTimestamp(4, end);
            rs = ps.executeQuery();
            rs.first();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }

    public static int create(int customerId, int userId, String title, String description, String location, String contact, String type, String url, Timestamp start, Timestamp end ) {
        int id = 0;

        try {
            PreparedStatement ps = DatabaseQuery.prepare(
                    "INSERT INTO appointment (customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), 'test', now(), 'test')");

            ps.setInt(1, customerId);
            ps.setInt(2, userId);
            ps.setString(3, title);
            ps.setString(4, description);
            ps.setString(5, location);
            ps.setString(6, contact);
            ps.setString(7, type);
            ps.setString(8, url);
            ps.setTimestamp(9, start);
            ps.setTimestamp(10, end);
            ps.execute();

            ResultSet rs = DatabaseQuery.select("SELECT LAST_INSERT_ID() FROM appointment");
            id = rs.getInt(1);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return id;
    }

    public static boolean update(int appointmentId, int customerId, int userId, String title, String description,
            String location, String contact, String type, String url, Timestamp start, Timestamp end) {
        boolean hasUpdated = false;

        try {
            PreparedStatement ps = DatabaseQuery.prepare(
                "UPDATE appointment SET customerId = ?, userId = ?, title = ?, description = ?, location = ?, contact = ?, type = ?, url = ?, start = ?, end = ?, lastUpdate = now() WHERE appointmentId = ?"
            );

            ps.setInt(1, customerId);
            ps.setInt(2, userId);
            ps.setString(3, title);
            ps.setString(4, description);
            ps.setString(5, location);
            ps.setString(6, contact);
            ps.setString(7, type);
            ps.setString(8, url);
            ps.setTimestamp(9, start);
            ps.setTimestamp(10, end);
            ps.setInt(11, appointmentId);
            ps.execute();
            hasUpdated = ps.getUpdateCount() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return hasUpdated;
    }

    public static boolean delete(int appointmentId) {
        boolean hasDeleted = false;

        try {
            PreparedStatement ps = DatabaseQuery.prepare("DELETE FROM appointment WHERE appointmentId = ?");

            ps.setInt(1, appointmentId);
            ps.execute();
            hasDeleted = ps.getUpdateCount() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return hasDeleted;
    }

}
