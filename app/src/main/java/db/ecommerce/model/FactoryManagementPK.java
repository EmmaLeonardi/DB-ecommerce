package db.ecommerce.model;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class FactoryManagementPK extends FactoryManagementImpl {

    private final int codGestione;

    public FactoryManagementPK(int codFabbrica, Date start, Optional<Date> end, int codProduttore, int codGestione) {
        super(codFabbrica, start, end, codProduttore);
        this.codGestione = codGestione;
    }

    public FactoryManagementPK(FactoryManagement f, int codGestione) {
        super(f.getCodFabbrica(), f.getStart(), f.getEnd(), f.getCodProduttore());
        this.codGestione = codGestione;
    }

    /**
     * @return the codGestione
     */
    public int getCodGestione() {
        return codGestione;
    }

    public static FactoryManagement convertToFactoryManagement(final FactoryManagementPK f) {
        return new FactoryManagementImpl(f.getCodFabbrica(), f.getStart(), f.getEnd(), f.getCodProduttore());
    }

    @Override
    public String toString() {
        return "FactoryManagementPK [codGestione=" + codGestione + super.toString() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(codGestione);
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
        FactoryManagementPK other = (FactoryManagementPK) obj;
        return codGestione == other.codGestione
                && convertToFactoryManagement(this).equals(convertToFactoryManagement(other));
    }

}
