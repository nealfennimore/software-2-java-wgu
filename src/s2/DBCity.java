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
public class DBCity {

    public static ResultSet get(int cityId) {
        ResultSet rs = null;

        try {
            PreparedStatement ps = DatabaseQuery.prepare("SELECT * FROM city WHERE cityId = ?");

            ps.setInt(1, cityId);
            rs = ps.executeQuery();
            rs.first();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }

    public static ResultSet getByCity(String city) {
        ResultSet rs = null;

        try {
            PreparedStatement ps = DatabaseQuery.prepare("SELECT * FROM city WHERE city = ?");

            ps.setString(1, city);
            rs = ps.executeQuery();
            rs.first();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }

    public static int create(String city, int countryId) {
        int id = 0;

        try {
            PreparedStatement ps = DatabaseQuery.prepare(
                    "INSERT INTO city (city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, now(), 'test', now(), 'test')");

            ps.setString(1, city);
            ps.setInt(2, countryId);
            ps.execute();

            ResultSet rs = DatabaseQuery.select("SELECT LAST_INSERT_ID() FROM city");
            id = rs.getInt(1);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return id;
    }

    public static int createOrSelect(String city, int countryId) {
        int id = 0;

        try {
            ResultSet rs = DBCity.getByCity(city);
            if (rs.first()) {
                id = rs.getInt(1);
            } else {
                id = DBCity.create(city, countryId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return id;
    }

    public static boolean update(int cityId, String city, int countryId) {
        boolean hasUpdated = false;

        try {
            PreparedStatement ps = DatabaseQuery.prepare(
                    "UPDATE city SET city = ?, countryId = ?, lastUpdate = now() WHERE cityId = ?");

            ps.setString(1, city);
            ps.setInt(2, countryId);
            ps.setInt(3, cityId);
            ps.execute();
            hasUpdated = ps.getUpdateCount() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return hasUpdated;
    }

    public static boolean delete(int cityId) {
        boolean hasDeleted = false;

        try {
            PreparedStatement ps = DatabaseQuery.prepare("DELETE FROM city WHERE cityId = ?");

            ps.setInt(1, cityId);
            ps.execute();
            hasDeleted = ps.getUpdateCount() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return hasDeleted;
    }

}
