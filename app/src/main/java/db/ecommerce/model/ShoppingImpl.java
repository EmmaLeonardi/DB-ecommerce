package db.ecommerce.model;

import java.util.Objects;

public class ShoppingImpl implements Shopping {

    private final int Cod_cliente;

    private final float price;

    /**
     * @param cod_spesa
     * @param cod_cliente
     * @param price
     */
    public ShoppingImpl(int cod_cliente, float price) {
        Cod_cliente = cod_cliente;
        this.price = price;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCodCliente() {
        return Cod_cliente;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ShoppingImpl [Cod_cliente=" + Cod_cliente + ", price=" + price + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(Cod_cliente, price);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ShoppingImpl other = (ShoppingImpl) obj;
        return Cod_cliente == other.Cod_cliente && Float.floatToIntBits(price) == Float.floatToIntBits(other.price);
    }

}
