package db.ecommerce.model;

import java.util.Objects;

public class FactoryImpl implements Factory {

    private final String via;
    private final int nCiv;
    private final String citta;

    /**
     * @param via
     * @param nCiv
     * @param citta
     */
    public FactoryImpl(String via, int nCiv, String citta) {
        this.via = via;
        this.nCiv = nCiv;
        this.citta = citta;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVia() {
        return via;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getnCiv() {
        return nCiv;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCitta() {
        return citta;
    }

    @Override
    public String toString() {
        return "Factory [via=" + via + ", nCiv=" + nCiv + ", citta=" + citta + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(citta, nCiv, via);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FactoryImpl other = (FactoryImpl) obj;
        return Objects.equals(citta, other.citta) && nCiv == other.nCiv && Objects.equals(via, other.via);
    }

}
