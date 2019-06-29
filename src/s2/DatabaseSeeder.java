/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s2;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author neal
 */
public class DatabaseSeeder {

    public static void seed() throws SQLException {        
        seedUsers();
        seedCustomers();
    }

    public static void seedCustomers() throws SQLException {
        ResultSet rs = DatabaseQuery.select("SELECT COUNT(*) FROM customer");
        int size = rs.getInt(1);

        if (size == 0){
            int countryId = Country.create("USA");
            int cityId = City.create("Trenton", countryId);
            int addressId = Address.create("123 Somewhere", "", cityId, "08638", "6096666666");
            Customer.create("Neal", addressId);
            System.out.println("Seeded customers");
        }
    }

    public static void seedUsers() throws SQLException {        
        ResultSet rs = DatabaseQuery.select("SELECT COUNT(*) FROM user");
        int size = rs.getInt(1);

        if (size == 0) {
            User.create("test", "test");
            System.out.println("Seeded users");
        }
    }


}
