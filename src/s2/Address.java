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
public class Address {

    public static int create(String address, String address2, int cityId, String postalCode, String phone ) {
        int id = 0;

        try {
            PreparedStatement ps = DatabaseQuery.prepare(
                    "INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, ?, ?, ?, now(), 'test', now(), 'test')");

            ps.setString(1, address);
            ps.setString(2, address2);
            ps.setInt(3, cityId);
            ps.setString(4, postalCode);
            ps.setString(5, phone);
            ps.execute();

            ResultSet rs = DatabaseQuery.select("SELECT LAST_INSERT_ID() FROM address");
            id = rs.getInt(1);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return id;
    }

    public static boolean update(int addressId, String address, String address2, int cityId, String postalCode,
            String phone) {
        boolean hasUpdated = false;

        try {
            PreparedStatement ps = DatabaseQuery.prepare(
                "UPDATE address SET addressName = ?, addressId = ?, cityId = ?, postalCode = ?, phone = ?, lastUpdate = now() WHERE addressId = ?"
            );

            ps.setString(1, address);
            ps.setString(2, address2);
            ps.setInt(3, cityId);
            ps.setString(4, postalCode);
            ps.setString(5, phone);
            ps.setInt(6, addressId);
            ps.execute();
            hasUpdated = ps.getUpdateCount() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return hasUpdated;
    }

    public static boolean delete(int addressId) {
        boolean hasDeleted = false;

        try {
            PreparedStatement ps = DatabaseQuery.prepare("DELETE FROM address WHERE addressId = ?");

            ps.setInt(1, addressId);
            ps.execute();
            hasDeleted = ps.getUpdateCount() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return hasDeleted;
    }

}
