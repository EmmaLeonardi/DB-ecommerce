package db.ecommerce.utils;

public enum TYPEDELIVERY {

    STANDARD, PREMIUM;

    public static TYPEDELIVERY convert(String s) {
        if (s.toUpperCase().equals(PREMIUM.name())) {
            return PREMIUM;
        } else {
            return STANDARD;
        }
    }

}
