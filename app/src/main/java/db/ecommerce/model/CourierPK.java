package db.ecommerce.model;

import java.util.Date;
import java.util.Objects;

/**
 * This is a Courier with a Cod_corriere memorized
 * */
public class CourierPK extends CourierImpl {
    
    private final int Cod_corriere;

    public CourierPK(int Cod_corriere, String codFis, String name, String surname, Date birthday, String codDriv, String drivNat) {
        super(codFis, name, surname, birthday, codDriv, drivNat);
        this.Cod_corriere=Cod_corriere;
    }
    
    public CourierPK(int Cod_corriere, Courier c) {
        super(c.getCodFis(),c.getName(),c.getSurname(),c.getDateBirth(),c.getCodDrivingLic(),c.getDrivingNat());
        this.Cod_corriere=Cod_corriere;
    }
    

    /**
     * @return the cod_corriere
     */
    public int getCod_corriere() {
        return Cod_corriere;
    }
    
    public static Courier convertToCourier(final CourierPK c) {
        return new CourierImpl(c.getCodFis(), c.getName(), c.getSurname(), c.getDateBirth(), c.getCodDrivingLic(), c.getDrivingNat());
    }

    @Override
    public String toString() {
        return "CourierPK [Cod_corriere=" + Cod_corriere + super.toString()+"]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(Cod_corriere);
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
        CourierPK other = (CourierPK) obj;
        return Cod_corriere == other.Cod_corriere&&convertToCourier(this).equals(convertToCourier(other));
    }
    
    

    
    

}
