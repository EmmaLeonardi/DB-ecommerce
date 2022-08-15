package db.ecommerce.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public interface Drive {

    /**
     * @return the start
     */
    Date getStart();

    /**
     * @return the end
     */
    Optional<Date> getEnd();

    /**
     * @return the hStart
     */
    SimpleDateFormat gethStart();

    /**
     * @return the hEnd
     */
    Optional<SimpleDateFormat> gethEnd();

    /**
     * @return the codCorriere
     */
    int getCodCorriere();

    /**
     * @return the targa
     */
    String getTarga();

}