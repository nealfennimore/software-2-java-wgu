/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author neal
 */
public class Customer {

    public static int create(String customerName, int addressId ) {
        int id = 0;

        try {   
            PreparedStatement ps = DatabaseQuery.prepare(
                "INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, 1, now(), 'test', now(), 'test')"
            );

            ps.setString(1, customerName);
            ps.setInt(2, addressId);
            ps.execute();
            
            ResultSet rs = DatabaseQuery.select("SELECT LAST_INSERT_ID() FROM customer");
            id = rs.getInt(1);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return id;
    }

    public static boolean update(int customerId, String customerName, int addressId ) {
        boolean hasUpdated = false;

        try {   
            PreparedStatement ps = DatabaseQuery.prepare(
                "UPDATE customer SET customerName = ?, addressId = ?, lastUpdate = now() WHERE customerId = ?"
            );

            ps.setString(1, customerName);
            ps.setInt(2, addressId);
            ps.setInt(3, customerId);
            ps.execute();
            hasUpdated = ps.getUpdateCount() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return hasUpdated;
    }

    public static boolean delete(int customerId ) {
        boolean hasDeleted = false;

        try {   
            PreparedStatement ps = DatabaseQuery.prepare(
                "DELETE FROM customer WHERE customerId = ?"
            );

            ps.setInt(1, customerId);
            ps.execute();
            hasDeleted = ps.getUpdateCount() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return hasDeleted;
    }

}
