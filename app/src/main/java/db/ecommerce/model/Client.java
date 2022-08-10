package db.ecommerce.model;

import java.util.Date;

/** This is the inteface of a generic Client */
public interface Client {

    /**
     * @return the fiscal code of Client
     */
    String getCodFis();

    /**
     * @return the Client's name
     */
    String getName();

    /**
     * @return the Client's surname
     */
    String getSurname();

    /**
     * @return the Client's date of birth
     */
    Date getDateBirth();

    /**
     * @return the Client's email
     */
    String getEmail();

    /**
     * @return the Client's phoneNumber
     */
    int getPhoneNumber();

    /**
     * @return the Client's street of residence
     */
    String getResStreet();

    /**
     * @return the Client's number of residence
     */
    int getResNumber();

    /**
     * @return the Client's city of residence
     */
    String getResCity();

    /**
     * @return the Client's bank info
     */
    String getBankInfo();
}
