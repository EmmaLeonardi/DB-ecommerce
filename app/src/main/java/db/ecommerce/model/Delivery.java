package db.ecommerce.model;

import java.util.Date;
import java.util.Optional;

import db.ecommerce.utils.TYPEDELIVERY;

public interface Delivery {

    /**
     * @return the cod_Consegna
     */
    int getCod_Consegna();

    /**
     * @return the cod_spesa
     */
    Optional<Integer> getCod_spesa();

    /**
     * @return the priceDelivery
     */
    float getPriceDelivery();

    /**
     * @return the date
     */
    Optional<Date> getDate();

    /**
     * @return the type of delivery
     */
    TYPEDELIVERY getType();

    /**
     * @return the codIndirizzo
     */
    int getCodIndirizzo();

    /**
     * @return the codCorriere
     */
    Optional<Integer> getCodCorriere();

    /**
     * @return the targa
     */
    Optional<String> getTarga();

}