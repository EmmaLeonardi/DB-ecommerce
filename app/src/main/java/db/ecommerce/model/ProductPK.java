package db.ecommerce.model;

import java.util.Objects;

public class ProductPK extends ProductImpl {

    private final int cod_prodotto;

    public ProductPK(String materiale, String descrizione, int cod_produttore, int cod_fabbrica, int cod_prodotto) {
        super(materiale, descrizione, cod_produttore, cod_fabbrica);
        this.cod_prodotto = cod_prodotto;
    }

    public ProductPK(Product p, int cod_prodotto) {
        super(p.getMateriale(), p.getDescrizione(), p.getCod_produttore(), p.getCod_fabbrica());
        this.cod_prodotto = cod_prodotto;
    }

    /**
     * @return the cod_prodotto
     */
    public int getCod_prodotto() {
        return cod_prodotto;
    }

    public static Product convertToProduct(final ProductPK p) {
        return new ProductImpl(p.getMateriale(), p.getDescrizione(), p.getCod_produttore(), p.getCod_fabbrica());
    }

    @Override
    public String toString() {
        return "ProductPK [cod_prodotto=" + cod_prodotto + super.toString() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(cod_prodotto);
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
        ProductPK other = (ProductPK) obj;
        return cod_prodotto == other.cod_prodotto && convertToProduct(this).equals(convertToProduct(other));
    }

}
