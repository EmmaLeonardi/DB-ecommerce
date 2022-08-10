package db.ecommerce.model;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import db.ecommerce.utils.PRODUCTTYPE;

public class SoldProductPK extends SoldProductImpl {

    private final int codSoldProduct;

    /**
     * @param price
     * @param start
     * @param end
     * @param type
     * @param expiration
     * @param size
     * @param codProduct
     * @param codSoldProduct
     */
    public SoldProductPK(float price, Date start, Optional<Date> end, PRODUCTTYPE type, Optional<Date> expiration,
            Optional<String> size, int codProduct, int codSoldProduct) {
        super(price, start, end, type, expiration, size, codProduct);
        this.codSoldProduct = codSoldProduct;
    }

    /**
     * @param price
     * @param start
     * @param end
     * @param type
     * @param expiration
     * @param size
     * @param codProduct
     * @param codSoldProduct
     */
    public SoldProductPK(SoldProduct p, int codSoldProduct) {
        super(p.getPrice(), p.getStart(), p.getEnd(), p.getType(), p.getExpiration(), p.getSize(), p.getCodProduct());
        this.codSoldProduct = codSoldProduct;
    }

    /**
     * @return the cod_soldProduct
     */
    public int getCodSoldProduct() {
        return codSoldProduct;
    }

    public static SoldProduct convertToSoldProduct(final SoldProductPK p) {
        return new SoldProductImpl(p.getPrice(), p.getStart(), p.getEnd(), p.getType(), p.getExpiration(), p.getSize(),
                p.getCodProduct());
    }

    @Override
    public String toString() {
        return "SoldProductPK [codSoldProduct=" + codSoldProduct + super.toString() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(codSoldProduct);
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
        SoldProductPK other = (SoldProductPK) obj;
        return codSoldProduct == other.codSoldProduct && convertToSoldProduct(this).equals(convertToSoldProduct(other));
    }

}
