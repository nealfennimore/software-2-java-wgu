package s2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseQuery {

    private static int mutate( String query ) {
        int rs = 0;

        try {
            Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement();
            rs = stmt.executeUpdate(query);
            System.out.println(rs);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }

    public static int update(String query) {
        return mutate(query);
    }

    public static int delete(String query) {
        return mutate(query);
    }

    public static int insert(String query) {
        return mutate(query);
    }

    public static ResultSet select( String query ) {
        ResultSet rs = null;

        try {
            Connection conn = Database.getConnection();
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String line = rs.getString(1);
                System.out.println(line);
            }

            rs.first();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }

    public static PreparedStatement prepare( String query ) {
        PreparedStatement ps = null;

        try {
            Connection conn = Database.getConnection();
            ps = conn.prepareStatement(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }   

        return ps;
    }
}