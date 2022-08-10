package db.ecommerce.model;

import java.util.Date;
import java.util.Objects;

public class CourierImpl implements Courier {
    
    private final String codFis;
    private final String name;
    private final String surname;
    private final Date birthday;
    private final String codDriv;
    private final String drivNat;
    
    /**
     * @param codFis
     * @param name
     * @param surname
     * @param birthday
     * @param codDriv
     * @param drivNat
     */
    public CourierImpl(String codFis, String name, String surname, Date birthday, String codDriv, String drivNat) {
        this.codFis = codFis;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.codDriv = codDriv;
        this.drivNat = drivNat;
    }

    @Override
    public String getCodFis() {
        return codFis;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public Date getDateBirth() {
        return birthday;
    }

    @Override
    public String getCodDrivingLic() {
        return codDriv;
    }

    @Override
    public String getDrivingNat() {
        return drivNat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthday, codDriv, codFis, drivNat, name, surname);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CourierImpl other = (CourierImpl) obj;
        return Objects.equals(birthday, other.birthday) && Objects.equals(codDriv, other.codDriv)
                && Objects.equals(codFis, other.codFis) && Objects.equals(drivNat, other.drivNat)
                && Objects.equals(name, other.name) && Objects.equals(surname, other.surname);
    }

    @Override
    public String toString() {
        return "CourierImpl [codFis=" + codFis + ", name=" + name + ", surname=" + surname + ", birthday=" + birthday
                + ", codDriv=" + codDriv + ", drivNat=" + drivNat + "]";
    }

    
    
}
