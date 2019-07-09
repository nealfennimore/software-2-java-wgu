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

/**
 *
 * @author neal
 */
public class ScheduleValidator {
    public static boolean isWithinBusinessHours(LocalDateTime start, LocalDateTime end) {
        int startHour = start.getHour();
        int endHour = end.getHour();

        return startHour >= 7 && endHour < 16;        
    }

    public static boolean hasOverlappingAppointments(LocalDateTime start, LocalDateTime end, int appointmentId) {
        try {
            ResultSet rs = DBAppointment.getBetweenDateRange(Timestamp.valueOf(start), Timestamp.valueOf(end));
            int size = 0;
            int selectedAppointmentId = 0;
            if (rs != null) {
              rs.last();
              size = rs.getRow();
              rs.first();
              selectedAppointmentId = rs.getInt("appointmentId");
            }
            boolean isUpdatingCurrentAppointment = (size == 1 && selectedAppointmentId == appointmentId);
            if( isUpdatingCurrentAppointment ){
                return false;
            }
            return rs.first();
        } catch (SQLException e) {
            return false;
        }
    }
}
