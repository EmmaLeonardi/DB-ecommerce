package db.ecommerce.model;

import java.util.Date;
import java.util.Optional;

import db.ecommerce.utils.PRODUCTTYPE;

public interface SoldProduct {

    /**
     * @return the price
     */
    float getPrice();

    /**
     * @return the start
     */
    Date getStart();

    /**
     * @return the end
     */
    Optional<Date> getEnd();

    /**
     * @return the type
     */
    PRODUCTTYPE getType();

    /**
     * @return the expiration
     */
    Optional<Date> getExpiration();

    /**
     * @return the size
     */
    Optional<String> getSize();

    /**
     * @return the codProduct
     */
    int getCodProduct();

}