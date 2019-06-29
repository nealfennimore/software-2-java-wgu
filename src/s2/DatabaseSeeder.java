/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

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
            int customerId = Customer.create("Neal", addressId);
            long d = System.currentTimeMillis();
            Appointment.create(customerId, 1, "Appointment 1", "Description", "", "", "", "", new Date(d), new Date(d));
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
