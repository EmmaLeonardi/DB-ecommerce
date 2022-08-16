package db.ecommerce.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import db.ecommerce.model.ClientPK;
import db.ecommerce.model.FactoryPK;
import db.ecommerce.model.ProducerPK;
import db.ecommerce.model.ProductPK;
import db.ecommerce.model.SoldProductPK;
import db.ecommerce.model.tables.FactoryTable;
import db.ecommerce.model.tables.ProducerTable;
import db.ecommerce.model.tables.ProductTable;
import db.ecommerce.model.tables.SoldProductTable;
import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import db.ecommerce.utils.PRODUCTTYPE;
import db.ecommerce.view.ProductsMenuImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
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

    private ProducerPK producer;

    private ProductTable prdTbl;

    private SoldProductTable sprTbl;

    private ProductPK productReference;

    private ProducerTable prcTbl;

    private FactoryTable fctTbl;

    private FactoryPK factory;

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

        ConnectionProvider c = new ConnectionProviderImpl(Credentials.getUsername(), Credentials.getPassword(),
                Credentials.getDbname());
        this.prdTbl = new ProductTable(c.getMySQLConnection());
        this.sprTbl = new SoldProductTable(c.getMySQLConnection());
        this.prcTbl = new ProducerTable(c.getMySQLConnection());
        this.fctTbl = new FactoryTable(c.getMySQLConnection());

        Platform.runLater(() -> {
            var a = this.prdTbl.findByPrimaryKey(product.getCodProduct());
            if (a.isEmpty()) {
                var alert = new Alert(AlertType.ERROR, "Questo prodotto in vendita non ha un prodotto correlato");
                alert.show();
                throw new IllegalStateException("The soldProduct doesn't have a product related");
            } else {
                productReference = a.get();
            }
            var b = this.prcTbl.findByPrimaryKey(productReference.getCod_produttore());
            if (b.isEmpty()) {
                var alert = new Alert(AlertType.ERROR, "Questo prodotto in vendita non ha un produttore correlato");
                alert.show();
                throw new IllegalStateException("The product doesn't have a producer related");
            } else {
                this.producer = b.get();
            }
            var f = this.fctTbl.findByPrimaryKey(productReference.getCod_fabbrica());
            if (f.isEmpty()) {
                var alert = new Alert(AlertType.ERROR, "Questo prodotto in vendita non ha una fabbrica correlato");
                alert.show();
                throw new IllegalStateException("The product doesn't have a factory related");
            } else {
                factory = f.get();
            }
            lbl_descr.setText(productReference.getDescrizione());
            lbl_material.setText(productReference.getMateriale());

            lbl_producer.setText(producer.getName() + " " + producer.getSurname());

            lbl_factory.setText(factory.getVia() + " " + factory.getnCiv() + " " + factory.getCitta());
            var allSoldProduct = sprTbl.allSoldProductsOfProduct(productReference);
            lstvw_product_prices.setItems(FXCollections.observableList(buildProduct(allSoldProduct)));
            double sum = allSoldProduct.stream().map(p -> p.getPrice()).reduce(Double::sum).orElse(0.0);
            if (allSoldProduct.size() == 0) {
                lbl_median_price.setText(0.0 + "€");
            } else {
                lbl_median_price.setText(sum / allSoldProduct.size() + "€");
            }

        });

    }

    private List<String> buildProduct(List<SoldProductPK> l) {
        List<String> list = new ArrayList<>();
        for (SoldProductPK elem : l) {
            String s = "Prezzo " + elem.getPrice() + "€ ";
            s = s + "";
            if (elem.getType() == PRODUCTTYPE.ALIMENTARE) {
                s = s + "Scadenza " + elem.getExpiration().get() + " ";
            } else {
                s = s + "Taglia " + elem.getSize().get() + " ";
            }
            if (elem.getEnd().isPresent()) {
                s = s + "Da " + elem.getStart() + " a " + elem.getEnd().get();
            } else {
                s = s + "Da " + elem.getStart() + " a promozione tutt'ora in corso";
            }
            list.add(s);
        }
        return list;

    }

}
