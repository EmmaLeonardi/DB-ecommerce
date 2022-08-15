package db.ecommerce.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class DriveImpl implements Drive {

    private final Date start;
    private final Optional<Date> end;
    private final SimpleDateFormat hStart;
    private final Optional<SimpleDateFormat> hEnd;
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
    public DriveImpl(Date start, Optional<Date> end, SimpleDateFormat hStart, Optional<SimpleDateFormat> hEnd,
            int codCorriere, String targa) {
        super();
        this.start = start;
        this.end = end;
        this.hStart = hStart;
        this.hEnd = hEnd;
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
    public SimpleDateFormat gethStart() {
        return hStart;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<SimpleDateFormat> gethEnd() {
        return hEnd;
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
        return "DriveImpl [start=" + start + ", end=" + end + ", hStart=" + hStart + ", hEnd=" + hEnd + ", codCorriere="
                + codCorriere + ", targa=" + targa + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(codCorriere, end, hEnd, hStart, start, targa);
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
        return codCorriere == other.codCorriere && Objects.equals(end, other.end) && Objects.equals(hEnd, other.hEnd)
                && Objects.equals(hStart, other.hStart) && Objects.equals(start, other.start)
                && Objects.equals(targa, other.targa);
    }

}
