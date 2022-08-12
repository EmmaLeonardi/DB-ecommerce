package db.ecommerce.model;

public interface Address {

    /**
     * @return the road
     */
    String getRoad();

    /**
     * @return the nCiv
     */
    int getnCiv();

    /**
     * @return the city
     */
    String getCity();

    /**
     * @return the CAP
     */
    int getCAP();

    /**
     * @return the region
     */
    String getRegion();

    /**
     * @return the state
     */
    String getState();

}