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
public class User {

    public static int create(String userName, String password) {
        int id = 0;

        try {
            PreparedStatement ps = DatabaseQuery.prepare(
                "INSERT INTO user (userName, password, active, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, 1, now(), 'test', now(), 'test')"
            );

            ps.setString(1, userName);
            ps.setString(2, password);
            ps.execute();

            ResultSet rs = DatabaseQuery.select("SELECT LAST_INSERT_ID() FROM user");
            id = rs.getInt(1);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return id;
    }

    public static boolean update(int userId, String userName, String password) {
        boolean hasUpdated = false;

        try {
            PreparedStatement ps = DatabaseQuery
                    .prepare("UPDATE user SET userName = ?, password = ?, lastUpdate = now() WHERE userId = ?");

            ps.setString(1, userName);
            ps.setString(2, password);
            ps.setInt(3, userId);
            ps.execute();
            hasUpdated = ps.getUpdateCount() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return hasUpdated;
    }

    public static boolean delete(int userId) {
        boolean hasDeleted = false;

        try {
            PreparedStatement ps = DatabaseQuery.prepare("DELETE FROM user WHERE userId = ?");

            ps.setInt(1, userId);
            ps.execute();
            hasDeleted = ps.getUpdateCount() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return hasDeleted;
    }

}
