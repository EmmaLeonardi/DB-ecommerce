package db.ecommerce.model;

import java.util.Objects;

public class AddressImpl implements Address {

    private final String road;
    private final int nCiv;
    private final String city;
    private final int CAP;
    private final String region;
    private final String state;

    /**
     * @param road
     * @param nCiv
     * @param city
     * @param CAP
     * @param region
     * @param state
     */
    public AddressImpl(String road, int nCiv, String city, int CAP, String region, String state) {
        super();
        this.road = road;
        this.nCiv = nCiv;
        this.city = city;
        this.CAP = CAP;
        this.region = region;
        this.state = state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRoad() {
        return road;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getnCiv() {
        return nCiv;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCity() {
        return city;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCAP() {
        return CAP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRegion() {
        return region;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getState() {
        return state;
    }

    @Override
    public String toString() {
        return "AddressImpl [road=" + road + ", nCiv=" + nCiv + ", city=" + city + ", CAP=" + CAP + ", region=" + region
                + ", state=" + state + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(CAP, city, nCiv, region, road, state);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AddressImpl other = (AddressImpl) obj;
        return CAP == other.CAP && Objects.equals(city, other.city) && nCiv == other.nCiv
                && Objects.equals(region, other.region) && Objects.equals(road, other.road)
                && Objects.equals(state, other.state);
    }

}
