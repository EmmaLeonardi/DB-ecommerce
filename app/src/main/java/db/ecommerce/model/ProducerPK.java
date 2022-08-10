package db.ecommerce.model;

import java.util.Date;
import java.util.Objects;

/**
 * This is a Courier with a Cod_produttore memorized
 */
public class ProducerPK extends ProducerImpl {
    private final int Cod_produttore;

    /**
     * @param codFis
     * @param name
     * @param surname
     * @param birthday
     * @param piva
     */
    public ProducerPK(int Cod_produttore, String codFis, String name, String surname, Date birthday, String piva) {
        super(codFis, name, surname, birthday, piva);
        this.Cod_produttore = Cod_produttore;
    }

    public ProducerPK(int Cod_produttore, Producer p) {
        super(p.getCodFis(), p.getName(), p.getSurname(), p.getDateBirth(), p.getPIVA());
        this.Cod_produttore = Cod_produttore;
    }

    /**
     * @return the cod_produttore
     */
    public int getCod_produttore() {
        return Cod_produttore;
    }

    public static Producer convertToProducer(final ProducerPK p) {
        return new ProducerImpl(p.getCodFis(), p.getName(), p.getSurname(), p.getDateBirth(), p.getPIVA());
    }

    @Override
    public String toString() {
        return "ProducerPK [Cod_produttore=" + Cod_produttore + super.toString() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(Cod_produttore);
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
        ProducerPK other = (ProducerPK) obj;
        return Cod_produttore == other.Cod_produttore && convertToProducer(this).equals(convertToProducer(other));
    }

}
