package s2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    private static Connection conn = null;
    private static String DBUSER = "U04Jvq";
    private static String DBPASS = "53688257899";
    private static String DB_URL = "jdbc:mysql://52.206.157.109/U04Jvq";
    private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    public static Connection getConnection() {
        return conn;
    }

    public static Connection setup() {
        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, DBUSER, DBPASS);
            System.out.println("Connected to database.");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return conn;
    }
}