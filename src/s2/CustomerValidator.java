/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author neal
 */
public class CustomerValidator {
    public static boolean validate(String customerName, String address, String address2, String city, String country, String postalCode,
            String phone) {

        Map<String, String> map = new HashMap<String, String>();

        map.put("customerName", customerName);
        map.put("address", address);
        map.put("address2", address2);
        map.put("city", city);
        map.put("country", country);
        map.put("postalCode", postalCode);
        map.put("phone", phone);


        // Use lambdas in order to filter out unused keys and values
        // therefore allowing reuse of same parameters
        Map<String, String> emptyStringCollection = map.entrySet()
            .stream()
            .filter(m -> ! "address2".equals(m.getKey()) ) // Filter out address2 as it can be empty
            .filter(m -> Utils.isEmptyString(m.getValue()) )
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (!emptyStringCollection.isEmpty()) {
            return false;
        }

        // Use lambdas in order to filter out unused keys and values
        // therefore allowing reuse of same parameters
        Map<String, String> hasAlphaCharacters = map.entrySet()
            .stream()
            .filter( m -> "postalCode".equals(m.getKey()) || "phone".equals(m.getKey()) )
            .filter( m -> m.getValue().matches("[a-zA-Z]+"))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (!hasAlphaCharacters.isEmpty()) {
            return false;
        }

        return true;
    }
}
