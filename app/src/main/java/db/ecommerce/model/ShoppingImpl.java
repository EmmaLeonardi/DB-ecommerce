package db.ecommerce.model;

import java.util.Objects;

public class ShoppingImpl implements Shopping {

    private final int Cod_cliente;

    private final double price;

    /**
     * @param cod_spesa
     * @param cod_cliente
     * @param price
     */
    public ShoppingImpl(int cod_cliente, double price) {
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
    public double getPrice() {
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
        return Cod_cliente == other.Cod_cliente
                && Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price);
    }

}
