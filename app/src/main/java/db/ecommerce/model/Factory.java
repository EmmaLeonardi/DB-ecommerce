package db.ecommerce.model;

public interface Factory {

    /**
     * @return the via
     */
    String getVia();

    /**
     * @return the nCiv
     */
    int getnCiv();

    /**
     * @return the citta
     */
    String getCitta();

    int hashCode();

}