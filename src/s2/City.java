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
public class City {

    private int cityId;
    private String city;
    private int countryId;
    private Country country;

    public City() throws SQLException {
        this(0, "City", 0);
    }

    public City(int cityId, String city, int countryId) throws SQLException {
        this.cityId = cityId;
        this.city = city;
        this.countryId = countryId;

        ResultSet rs = DBCountry.get(countryId);
        Country country = new Country(
            countryId,
            rs.getString("country")
        );
        this.setCountry(country);
    }

    /**
     * @return the cityId
     */
    public int getCityId() {
        return cityId;
    }

    /**
     * @param cityId the cityId to set
     */
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the countryId
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * @param countryId the countryId to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * @return the country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(Country country) {
        this.country = country;
    }
}
