package db.ecommerce.model;

import java.util.Date;

public interface Producer {
    
    /**
     * @return the fiscal code of Producer
     */
    String getCodFis();

    /**
     * @return the Producer's name
     */
    String getName();

    /**
     * @return the Producer's surname
     */
    String getSurname();

    /**
     * @return the Producer's date of birth
     */
    Date getDateBirth();

    /**
     * @return the Producer's VAT number (Partita IVA)
     */
    String getPIVA();

}
