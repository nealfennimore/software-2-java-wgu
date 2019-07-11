/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s2;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author neal
 */
public class Utils {
    public static boolean isEmptyString(String str) {
        return str == null || str.isEmpty();
    }

    public static Timestamp toTimestamp(LocalDateTime date) {
        return Timestamp.valueOf(date);
    }

    public static LocalDateTime toLocalDateTime(Timestamp date) {
        return date.toLocalDateTime();
    }
}
