package db.ecommerce.utils;

public enum PRODUCTTYPE {
    ALIMENTARE, VESTIARIO;

    public static PRODUCTTYPE convert(String s) {
        if (s.toUpperCase().equals(ALIMENTARE.name())) {
            return ALIMENTARE;
        } else {
            return VESTIARIO;
        }
    }

}
