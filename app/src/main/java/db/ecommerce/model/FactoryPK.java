package db.ecommerce.model;

import java.util.Objects;

public class FactoryPK extends FactoryImpl {

    private final int codFabbrica;

    public FactoryPK(String via, int nCiv, String citta, int codFabbrica) {
        super(via, nCiv, citta);
        this.codFabbrica = codFabbrica;
    }

    public FactoryPK(Factory f, int codFabbrica) {
        super(f.getVia(), f.getnCiv(), f.getCitta());
        this.codFabbrica = codFabbrica;
    }

    /**
     * @return the codFabbrica
     */
    public int getCodFabbrica() {
        return codFabbrica;
    }

    public static Factory convertToFactory(final FactoryPK f) {
        return new FactoryImpl(f.getVia(), f.getnCiv(), f.getCitta());
    }

    @Override
    public String toString() {
        return "FactoryPK [codFabbrica=" + codFabbrica + super.toString() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(codFabbrica);
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
        FactoryPK other = (FactoryPK) obj;
        return codFabbrica == other.codFabbrica && convertToFactory(this).equals(convertToFactory(other));
    }

}
