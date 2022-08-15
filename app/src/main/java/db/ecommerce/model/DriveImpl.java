package db.ecommerce.model;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class DriveImpl implements Drive {

    private final Date start;
    private final Optional<Date> end;
    private final int codCorriere;
    private final String targa;

    /**
     * @param start
     * @param end
     * @param hStart
     * @param hEnd
     * @param codCorriere
     * @param targa
     */
    public DriveImpl(Date start, Optional<Date> end, int codCorriere, String targa) {
        this.start = start;
        this.end = end;
        this.codCorriere = codCorriere;
        this.targa = targa;
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
    public int getCodCorriere() {
        return codCorriere;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTarga() {
        return targa;
    }

    @Override
    public String toString() {
        return "DriveImpl [start=" + start + ", end=" + end + ", codCorriere=" + codCorriere + ", targa=" + targa + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(codCorriere, end, start, targa);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DriveImpl other = (DriveImpl) obj;
        return codCorriere == other.codCorriere && Objects.equals(end, other.end) && Objects.equals(start, other.start)
                && Objects.equals(targa, other.targa);
    }

}
