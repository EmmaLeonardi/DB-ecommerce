package db.ecommerce.utils;

public enum TYPEDELIVERY {

    STANDARD(1.0), PREMIUM(2.5);

    private final double price;

    public static TYPEDELIVERY convert(String s) {
        if (s.toUpperCase().equals(PREMIUM.name())) {
            return PREMIUM;
        } else {
            return STANDARD;
        }
    }

    private TYPEDELIVERY(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

}
