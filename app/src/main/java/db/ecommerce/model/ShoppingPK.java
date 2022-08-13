package db.ecommerce.model;

import java.util.Objects;

public class ShoppingPK extends ShoppingImpl {

    private final int CodSpesa;

    /**
     * @param cod_cliente
     * @param price
     * @param cod_spesa
     */
    public ShoppingPK(int cod_cliente, double price, int cod_spesa) {
        super(cod_cliente, price);
        CodSpesa = cod_spesa;
    }

    public ShoppingPK(Shopping s, int cod_spesa) {
        super(s.getCodCliente(), s.getPrice());
        CodSpesa = cod_spesa;
    }

    /**
     * @return the cod_spesa
     */
    public int getCodSpesa() {
        return CodSpesa;
    }

    public static Shopping convertToShopping(final ShoppingPK s) {
        return new ShoppingImpl(s.getCodCliente(), s.getPrice());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(CodSpesa);
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
        ShoppingPK other = (ShoppingPK) obj;
        return CodSpesa == other.CodSpesa&&convertToShopping(this).equals(convertToShopping(other));
    }

    @Override
    public String toString() {
        return "ShoppingPK [CodSpesa=" + CodSpesa + super.toString()+"]";
    }
    
    

}
