package db.ecommerce.model;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class FactoryManagementImpl implements FactoryManagement {

    private final int codFabbrica;
    private final Date start;
    private final Optional<Date> end;
    private final int codProduttore;

    /**
     * @param codFabbrica
     * @param start
     * @param end
     * @param codProduttore
     */
    public FactoryManagementImpl(int codFabbrica, Date start, Optional<Date> end, int codProduttore) {
        super();
        this.codFabbrica = codFabbrica;
        this.start = start;
        this.end = end;
        this.codProduttore = codProduttore;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCodFabbrica() {
        return codFabbrica;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getStart() {
        return start;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Date> getEnd() {
        return end;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCodProduttore() {
        return codProduttore;
    }

    @Override
    public String toString() {
        return "FactoryManagementImpl [codFabbrica=" + codFabbrica + ", start=" + start + ", end=" + end
                + ", codProduttore=" + codProduttore + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(codFabbrica, codProduttore, end, start);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FactoryManagementImpl other = (FactoryManagementImpl) obj;
        return codFabbrica == other.codFabbrica && codProduttore == other.codProduttore
                && Objects.equals(end, other.end) && Objects.equals(start, other.start);
    }

}
