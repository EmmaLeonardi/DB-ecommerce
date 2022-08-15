package db.ecommerce.controller;

import java.io.IOException;
import java.util.List;
import db.ecommerce.model.ClientPK;
import db.ecommerce.model.SoldProductPK;
import db.ecommerce.view.ProductsMenuImpl;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ProductDetailsController {

    @FXML
    private ListView<String> lstvw_product_prices;

    @FXML
    private Button btn_back;

    @FXML
    private Label lbl_material;

    @FXML
    private Label lbl_descr;

    @FXML
    private Label lbl_producer;

    @FXML
    private Label lbl_factory;

    @FXML
    private Label lbl_median_price;

    private SoldProductPK product;

    private ClientPK user;

    public void setProduct(SoldProductPK p) {
        this.product = p;

    }

    public void setClient(ClientPK user) {
        this.user = user;

    }

    @FXML
    public void back(final Event event) {
        Stage s = (Stage) btn_back.getScene().getWindow();
        try {
            new ProductsMenuImpl(s, user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called at the start and inizializes the list view
     */
    public void initialize() {
        /*
         * ConnectionProvider c = new ConnectionProviderImpl(Credentials.getUsername(),
         * Credentials.getPassword(), Credentials.getDbname()); this.slpTbl = new
         * SoldProductTable(c.getMySQLConnection()); this.prdTbl = new
         * ProductTable(c.getMySQLConnection());
         * 
         * Platform.runLater(() -> { List<SoldProductPK> allProduct =
         * slpTbl.findAll().stream().filter(p -> { if (p.getEnd().isEmpty() ||
         * (p.getEnd().isPresent() && p.getEnd().get().compareTo(new Date()) > 0)) {
         * return true; } else { return false; } }).collect(Collectors.toList());
         * this.lstvw_products.setItems(FXCollections.observableList(this.buildProduct(
         * allProduct))); this.fullList = new ArrayList<>(allProduct);
         * this.filteredListShop = new ArrayList<>(allProduct); this.cartList = new
         * ArrayList<>();
         * 
         * var sizes = allProduct.stream().map(p -> p.getSize()).distinct().filter(s ->
         * s.isPresent()) .map(s -> s.get()).collect(Collectors.toList());
         * 
         * this.cbx_size.setItems(FXCollections.observableList(sizes));
         */

        // });

    }

    private List<String> buildProduct(List<SoldProductPK> l) {
        return null;
        /*
         * List<String> list = new ArrayList<>(); for (final SoldProductPK elem : l) {
         * var p = prdTbl.findByPrimaryKey(elem.getCodProduct()); String s =
         * "Prodotto "; s = s + "Tipo " + elem.getType().name() + " "; s = s + "Prezzo "
         * + elem.getPrice() + " "; if (elem.getType() == PRODUCTTYPE.ALIMENTARE) { s =
         * s + "Scadenza " + elem.getExpiration().get() + " "; } else { s = s +
         * "Taglia " + elem.getSize().get() + " "; } if (p.isPresent()) { s = s +
         * "Materiale " + p.get().getMateriale() + " "; s = s + "Descrizione: " +
         * p.get().getDescrizione() + " "; } list.add(s); } return list;
         */
    }

}
