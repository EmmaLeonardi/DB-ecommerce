package db.ecommerce.model;

import java.util.Objects;

public class AddressPK extends AddressImpl {

    private final int codAddress;

    /**
     * @param road
     * @param nCiv
     * @param city
     * @param CAP
     * @param region
     * @param state
     */
    public AddressPK(String road, int nCiv, String city, int CAP, String region, String state, int codAddress) {
        super(road, nCiv, city, CAP, region, state);
        this.codAddress = codAddress;
    }

    public AddressPK(Address a, int codAddress) {
        super(a.getRoad(), a.getnCiv(), a.getCity(), a.getCAP(), a.getRegion(), a.getState());
        this.codAddress = codAddress;
    }

    /**
     * @return the cod_address
     */
    public int getCodAddress() {
        return codAddress;
    }

    public static Address convertToAddress(final AddressPK a) {
        return new AddressImpl(a.getRoad(), a.getnCiv(), a.getCity(), a.getCAP(), a.getRegion(), a.getState());
    }

    @Override
    public String toString() {
        return "AddressPK [codAddress=" + codAddress + super.toString() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(codAddress);
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
        AddressPK other = (AddressPK) obj;
        return codAddress == other.codAddress && convertToAddress(this).equals(convertToAddress(other));
    }

}
