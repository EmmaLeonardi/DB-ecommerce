package db.ecommerce.model;

public interface Product {

    /**
     * @return the materiale
     */
    String getMateriale();

    /**
     * @return the descrizione
     */
    String getDescrizione();

    /**
     * @return the cod_produttore
     */
    int getCod_produttore();

    /**
     * @return the cod_fabbrica
     */
    int getCod_fabbrica();

}