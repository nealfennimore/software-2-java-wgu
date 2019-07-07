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
public class Customer {

    private int customerId;
    private String customerName;
    private int addressId;
    private int active;
    private Address address;

    public Customer() throws SQLException {
        this(0, "Customer", 0, 1);
    }

    public Customer(int customerId, String customerName, int addressId, int active) throws SQLException {
        this.customerId = customerId;
        this.customerName = customerName;
        this.addressId = addressId;
        this.active = active;

        ResultSet rs = DBAddress.get(addressId);
        Address address = new Address(
            addressId,
            rs.getString("address"),
            rs.getString("address2"),
            rs.getInt("cityId"),
            rs.getString("postalCode"),
            rs.getString("phone")
        );
        this.setAddress(address);
    }

    /**
     * @return the customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the addressId
     */
    public int getAddressId() {
        return addressId;
    }

    /**
     * @param addressId the addressId to set
     * @throws SQLException
     */
    public void setAddressId(int addressId) throws SQLException {
        this.addressId = addressId;;
    }

    /**
     * @return the active
     */
    public int getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(int active) {
        this.active = active;
    }

    /**
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }
}
