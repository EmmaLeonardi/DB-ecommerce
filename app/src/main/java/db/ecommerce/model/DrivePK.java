package db.ecommerce.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class DrivePK extends DriveImpl {

    private final int codDrive;

    public DrivePK(Date start, Optional<Date> end, SimpleDateFormat hStart, Optional<SimpleDateFormat> hEnd,
            int codCorriere, String targa, int cod_drive) {
        super(start, end, hStart, hEnd, codCorriere, targa);
        this.codDrive = cod_drive;
    }

    public DrivePK(Drive d, int cod_drive) {
        super(d.getStart(), d.getEnd(), d.gethStart(), d.gethEnd(), d.getCodCorriere(), d.getTarga());
        this.codDrive = cod_drive;
    }

    /**
     * @return the codDrive
     */
    public int getCodDrive() {
        return codDrive;
    }

    public static Drive convertToDrive(final DrivePK d) {
        return new DriveImpl(d.getStart(), d.getEnd(), d.gethStart(), d.gethEnd(), d.getCodCorriere(), d.getTarga());
    }

    @Override
    public String toString() {
        return "DrivePK [codDrive=" + codDrive + super.toString() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(codDrive);
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
        DrivePK other = (DrivePK) obj;
        return codDrive == other.codDrive && convertToDrive(this).equals(convertToDrive(other));
    }

}
