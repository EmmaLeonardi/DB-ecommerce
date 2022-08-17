package db.ecommerce.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import db.ecommerce.model.ProducerPK;
import db.ecommerce.model.ProductPK;
import db.ecommerce.model.SoldProductPK;
import db.ecommerce.model.tables.ProductTable;
import db.ecommerce.model.tables.SoldProductTable;
import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import db.ecommerce.utils.PRODUCTTYPE;
import db.ecommerce.view.ProducerChangesMenuImpl;
import db.ecommerce.view.ShoppingMenuImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SellingProductController {

    private ProductPK product;
    private ProducerPK producer;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_save;

    @FXML
    private ListView<String> ltvw_selling_products;

    @FXML
    private DatePicker dtpk_start_date;

    @FXML
    private DatePicker dtpk_end_date;

    @FXML
    private DatePicker dtpk_expiry_date;

    @FXML
    private ComboBox<PRODUCTTYPE> cmb_type;

    @FXML
    private TextField txt_price;

    @FXML
    private TextField txt_size;
    private SoldProductTable slpTbl;
    private ProductTable prdTbl;
    private List<SoldProductPK> shownSoldProduct;

    public void setProducer(ProducerPK producer) {
        this.producer = producer;
    }

    public void setProduct(ProductPK product) {
        this.product = product;
    }

    @FXML
    public void back(final Event event) {
        Stage s = (Stage) btn_back.getScene().getWindow();
        try {
            new ProducerChangesMenuImpl(s, producer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void save(final Event event) {
        System.out.println("Salva");
    }

    public void initialize() {
        ConnectionProvider c = new ConnectionProviderImpl(Credentials.getUsername(), Credentials.getPassword(),
                Credentials.getDbname());
        this.slpTbl = new SoldProductTable(c.getMySQLConnection());
        this.prdTbl = new ProductTable(c.getMySQLConnection());
        // btn_save.setDisable(true);
        Platform.runLater(() -> {
            this.shownSoldProduct = slpTbl.allSoldProductsOfProduct(product);
            ltvw_selling_products.setItems(FXCollections.observableList(buildSoldProducts(shownSoldProduct)));
        });
    }

    private List<String> buildSoldProducts(List<SoldProductPK> list) {
        List<String> l = new ArrayList<>();
        return null;
    }

}
