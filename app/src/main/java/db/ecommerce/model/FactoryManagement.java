package db.ecommerce.model;

import java.util.Date;
import java.util.Optional;

public interface FactoryManagement {

    /**
     * @return the codFabbrica
     */
    int getCodFabbrica();

    /**
     * @return the start
     */
    Date getStart();

    /**
     * @return the end
     */
    Optional<Date> getEnd();

    /**
     * @return the codProduttore
     */
    int getCodProduttore();

}