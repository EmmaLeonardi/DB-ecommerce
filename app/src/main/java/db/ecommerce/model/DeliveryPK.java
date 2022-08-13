package db.ecommerce.model;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import db.ecommerce.utils.TYPEDELIVERY;

public class DeliveryPK extends DeliveryImpl {

    private final int codConsegna;

    public DeliveryPK(Optional<Integer> cod_spesa, double priceDelivery, Optional<Date> date, TYPEDELIVERY type,
            int codIndirizzo, Optional<Integer> codCorriere, Optional<String> targa, int codConsegna) {
        super(cod_spesa, priceDelivery, date, type, codIndirizzo, codCorriere, targa);
        this.codConsegna = codConsegna;
    }

    public DeliveryPK(Delivery d, int codConsegna) {
        super(d.getCod_spesa(), d.getPriceDelivery(), d.getDate(), d.getType(), d.getCodIndirizzo(), d.getCodCorriere(),
                d.getTarga());
        this.codConsegna = codConsegna;
    }

    /**
     * @return the cod_Consegna
     */
    public int getCod_Consegna() {
        return codConsegna;
    }

    public static Delivery convertToDelivery(final DeliveryPK d) {
        return new DeliveryImpl(d.getCod_spesa(), d.getPriceDelivery(), d.getDate(), d.getType(), d.getCodIndirizzo(),
                d.getCodCorriere(), d.getTarga());
    }

    @Override
    public String toString() {
        return "DeliveryPK [codConsegna=" + codConsegna + super.toString() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(codConsegna);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        DeliveryPK other = (DeliveryPK) obj;
        return codConsegna == other.codConsegna && convertToDelivery(this).equals(convertToDelivery(other));
    }

}
