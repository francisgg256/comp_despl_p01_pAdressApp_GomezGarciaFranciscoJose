package es.damdi.francisco.comp_despl_p01_padressapp_gomezgarciafranciscojose.model;

import java.time.LocalDate;

/**
 * The type Person pojo.
 */
public class PersonPOJO {
    /**
     * The First name.
     */
    public String firstName;
    /**
     * The Last name.
     */
    public String lastName;
    /**
     * The Street.
     */
    public String street;
    /**
     * The Postal code.
     */
    public int postalCode;
    /**
     * The City.
     */
    public String city;
    /**
     * The Birthday.
     */
    public LocalDate birthday;

    /**
     * Instantiates a new Person pojo.
     */
    public PersonPOJO() {
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets street.
     *
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets street.
     *
     * @param street the street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Gets postal code.
     *
     * @return the postal code
     */
    public int getPostalCode() {
        return postalCode;
    }

    /**
     * Sets postal code.
     *
     * @param postalCode the postal code
     */
    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets city.
     *
     * @param city the city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets birthday.
     *
     * @return the birthday
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Sets birthday.
     *
     * @param birthday the birthday
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
