package db.ecommerce.model;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import db.ecommerce.utils.TYPEDELIVERY;

public class DeliveryImpl implements Delivery {

    private final Optional<Integer> codSpesa;

    private final double priceDelivery;

    private final Optional<Date> date;

    private final TYPEDELIVERY type;

    private final int codIndirizzo;

    private final Optional<Integer> codCorriere;

    private final Optional<String> targa;

    /**
     * @param cod_Consegna
     * @param cod_spesa
     * @param priceDelivery
     * @param date
     * @param type
     * @param codIndirizzo
     * @param codCorriere
     * @param targa
     */
    public DeliveryImpl(Optional<Integer> cod_spesa, double priceDelivery, Optional<Date> date, TYPEDELIVERY type,
            int codIndirizzo, Optional<Integer> codCorriere, Optional<String> targa) {
        this.codSpesa = cod_spesa;
        this.priceDelivery = priceDelivery;
        this.date = date;
        this.type = type;
        this.codIndirizzo = codIndirizzo;
        this.codCorriere = codCorriere;
        this.targa = targa;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Integer> getCod_spesa() {
        return codSpesa;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPriceDelivery() {
        return priceDelivery;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Date> getDate() {
        return date;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TYPEDELIVERY getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCodIndirizzo() {
        return codIndirizzo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Integer> getCodCorriere() {
        return codCorriere;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<String> getTarga() {
        return targa;
    }

    @Override
    public String toString() {
        return "DeliveryImpl [cod_spesa=" + codSpesa + ", priceDelivery=" + priceDelivery + ", date=" + date + ", type="
                + type + ", codIndirizzo=" + codIndirizzo + ", codCorriere=" + codCorriere + ", targa=" + targa + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(codCorriere, codIndirizzo, codSpesa, date, priceDelivery, targa, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DeliveryImpl other = (DeliveryImpl) obj;
        return Objects.equals(codCorriere, other.codCorriere) && codIndirizzo == other.codIndirizzo
                && Objects.equals(codSpesa, other.codSpesa) && Objects.equals(date, other.date)
                && Double.doubleToLongBits(priceDelivery) == Double.doubleToLongBits(other.priceDelivery)
                && Objects.equals(targa, other.targa) && type == other.type;
    }

   

}
