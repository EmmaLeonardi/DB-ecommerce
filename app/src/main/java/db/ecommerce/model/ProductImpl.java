package db.ecommerce.model;

import java.util.Objects;

public class ProductImpl implements Product {

    private final String materiale;
    private final String descrizione;
    private final int Cod_produttore;
    private final int Cod_fabbrica;

    /**
     * @param materiale
     * @param descrizione
     * @param cod_produttore
     * @param cod_fabbrica
     */
    public ProductImpl(String materiale, String descrizione, int cod_produttore, int cod_fabbrica) {
        this.materiale = materiale;
        this.descrizione = descrizione;
        Cod_produttore = cod_produttore;
        Cod_fabbrica = cod_fabbrica;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMateriale() {
        return materiale;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCod_produttore() {
        return Cod_produttore;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCod_fabbrica() {
        return Cod_fabbrica;
    }

    @Override
    public String toString() {
        return "Product [materiale=" + materiale + ", descrizione=" + descrizione + ", Cod_produttore=" + Cod_produttore
                + ", Cod_fabbrica=" + Cod_fabbrica + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(Cod_fabbrica, Cod_produttore, descrizione, materiale);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        return Cod_fabbrica == other.getCod_fabbrica() && Cod_produttore == other.getCod_produttore()
                && Objects.equals(descrizione, other.getDescrizione())
                && Objects.equals(materiale, other.getMateriale());
    }

}
