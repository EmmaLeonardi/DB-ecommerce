package db.ecommerce.model;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import db.ecommerce.utils.PRODUCTTYPE;

public class SoldProductImpl implements SoldProduct {

    private final double price;
    private final Date start;
    private final Optional<Date> end;
    private final PRODUCTTYPE type;
    private final Optional<Date> expiration;
    private final Optional<String> size;
    private final int codProduct;

    /**
     * @param price
     * @param start
     * @param end
     * @param type
     * @param expiration
     * @param size
     * @param codProduct
     */
    public SoldProductImpl(double price, Date start, Optional<Date> end, PRODUCTTYPE type, Optional<Date> expiration,
            Optional<String> size, int codProduct) {
        this.price = price;
        this.start = start;
        this.end = end;
        this.type = type;
        this.expiration = expiration;
        this.size = size;
        this.codProduct = codProduct;
    }

    /**
     * @param price
     * @param start
     * @param end
     * @param type
     * @param expiration
     * @param codProduct
     */
    public SoldProductImpl(double price, Date start, Optional<Date> end, Date expiration, int codProduct) {
        this.price = price;
        this.start = start;
        this.end = end;
        this.type = PRODUCTTYPE.ALIMENTARE;
        this.expiration = Optional.of(Objects.requireNonNull(expiration));
        this.size = Optional.empty();
        this.codProduct = codProduct;
    }

    /**
     * @param price
     * @param start
     * @param end
     * @param type
     * @param size
     * @param codProduct
     */
    public SoldProductImpl(double price, Date start, Optional<Date> end, String size, int codProduct) {
        this.price = price;
        this.start = start;
        this.end = end;
        this.type = PRODUCTTYPE.VESTIARIO;
        this.expiration = Optional.empty();
        this.size = Optional.of(Objects.requireNonNull(size));
        this.codProduct = codProduct;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPrice() {
        return price;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getStart() {
        return start;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Date> getEnd() {
        return end;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PRODUCTTYPE getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Date> getExpiration() {
        return expiration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<String> getSize() {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCodProduct() {
        return codProduct;
    }

    @Override
    public String toString() {
        return "SoldProductImpl [price=" + price + ", start=" + start + ", end=" + end + ", type=" + type
                + ", expiration=" + expiration + ", size=" + size + ", codProduct=" + codProduct + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(codProduct, end, expiration, price, size, start, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SoldProductImpl other = (SoldProductImpl) obj;
        return codProduct == other.codProduct && Objects.equals(end, other.end)
                && Objects.equals(expiration, other.expiration)
                && Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
                && Objects.equals(size, other.size) && Objects.equals(start, other.start) && type == other.type;
    }

    

}
