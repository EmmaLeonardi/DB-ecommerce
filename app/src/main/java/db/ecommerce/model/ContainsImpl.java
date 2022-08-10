package db.ecommerce.model;

import java.util.Objects;

public class ContainsImpl implements Contains {

    private final int Cod_prodotto;

    private final int Cod_spesa;

    /**
     * @param cod_prodotto
     * @param cod_spesa
     */
    public ContainsImpl(int cod_prodotto, int cod_spesa) {
        super();
        Cod_prodotto = cod_prodotto;
        Cod_spesa = cod_spesa;
    }

    /** {@inheritDoc} */
    @Override
    public int getCodSpesa() {
        return this.Cod_spesa;
    }

    /** {@inheritDoc} */
    @Override
    public int getCodProdotto() {
        return this.Cod_prodotto;
    }

    @Override
    public String toString() {
        return "ContainsImpl [Cod_prodotto=" + Cod_prodotto + ", Cod_spesa=" + Cod_spesa + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(Cod_prodotto, Cod_spesa);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ContainsImpl other = (ContainsImpl) obj;
        return Cod_prodotto == other.Cod_prodotto && Cod_spesa == other.Cod_spesa;
    }
    
    

}
