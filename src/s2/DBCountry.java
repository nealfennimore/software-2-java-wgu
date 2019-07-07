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
public class DBCountry {

    public static ResultSet get(int countryId) {
        ResultSet rs = null;

        try {
            PreparedStatement ps = DatabaseQuery.prepare("SELECT * FROM country WHERE countryId = ?");

            ps.setInt(1, countryId);
            rs = ps.executeQuery();
            rs.first();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }

    public static ResultSet getByCountry(String country) {
        ResultSet rs = null;

        try {
            PreparedStatement ps = DatabaseQuery.prepare("SELECT * FROM country WHERE country = ?");

            ps.setString(1, country);
            rs = ps.executeQuery();
            rs.first();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }

    public static int create(String country) {
        int id = 0;

        try {
            PreparedStatement ps = DatabaseQuery.prepare(
                    "INSERT INTO country (country, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, now(), 'test', now(), 'test')");

            ps.setString(1, country);
            ps.execute();

            ResultSet rs = DatabaseQuery.select("SELECT LAST_INSERT_ID() FROM country");
            id = rs.getInt(1);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return id;
    }

    public static int createOrSelect(String country) {
        int id = 0;

        try {
            ResultSet rs = DBCountry.getByCountry(country);
            if ( rs.first() ){
                id = rs.getInt(1);
            } else {
                id = DBCountry.create(country);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return id;
    }

    public static boolean update(int countryId, String country) {
        boolean hasUpdated = false;

        try {
            PreparedStatement ps = DatabaseQuery.prepare(
                    "UPDATE country SET country = ?, lastUpdate = now() WHERE countryId = ?");

            ps.setString(1, country);
            ps.setInt(2, countryId);
            ps.execute();
            hasUpdated = ps.getUpdateCount() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return hasUpdated;
    }

    public static boolean delete(int countryId) {
        boolean hasDeleted = false;

        try {
            PreparedStatement ps = DatabaseQuery.prepare("DELETE FROM country WHERE countryId = ?");

            ps.setInt(1, countryId);
            ps.execute();
            hasDeleted = ps.getUpdateCount() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return hasDeleted;
    }

}
