package db.ecommerce.model;

import java.util.Date;

public interface Courier {
    
    /**
     * @return the fiscal code of Courier
     */
    String getCodFis();

    /**
     * @return the Courier's name
     */
    String getName();

    /**
     * @return the Courier's surname
     */
    String getSurname();

    /**
     * @return the Courier's date of birth
     */
    Date getDateBirth();

    /**
     * @return the Courier's driving licence code
     */
    String getCodDrivingLic();
    
    /**
     * @return the Courier's driving licence nationality
     */
    String getDrivingNat();

}
