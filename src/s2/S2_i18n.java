/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s2;

import java.util.*;

/**
 *
 * @author neal
 */
public class S2_i18n {

    public static ResourceBundle rb = null;
    public static Locale locale = null;

    public static void setup() {
        locale = Locale.getDefault();
        rb = ResourceBundle.getBundle("s2.language_files.rb", locale);
    }

    public static String getString(String key) {
        return rb.getString(key);
    }
}
