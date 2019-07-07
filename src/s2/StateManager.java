/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s2;
/**
 *
 * @author neal
 */
public class StateManager {
    private static Customer activeCustomer = null;
    private static Appointment activeAppointment = null;

    /**
     * @return the activeCustomer
     */
    public static Customer getActiveCustomer() {
        return activeCustomer;
    }

    /**
     * @param activeCustomer the activeCustomer to set
     */
    public static void setActiveCustomer(Customer activeCustomer) {
        StateManager.activeCustomer = activeCustomer;
    }

    /**
     * @return the activeAppointment
     */
    public static Appointment getActiveAppointment() {
        return activeAppointment;
    }

    /**
     * @param activeAppointment the activeAppointment to set
     */
    public static void setActiveAppointment(Appointment activeAppointment) {
        StateManager.activeAppointment = activeAppointment;
    }
}
