package db.ecommerce.utils;

public enum PRODUCTTYPE {
    ALIMENTARE, VESTIARIO;

    public PRODUCTTYPE convert(String s) {
        if (s.toUpperCase().equals(ALIMENTARE.name())) {
            return ALIMENTARE;
        } else {
            return VESTIARIO;
        }
    }

}
