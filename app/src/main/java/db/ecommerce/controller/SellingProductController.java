package db.ecommerce.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import db.ecommerce.model.ProducerPK;
import db.ecommerce.model.ProductPK;
import db.ecommerce.model.SoldProduct;
import db.ecommerce.model.SoldProductImpl;
import db.ecommerce.model.SoldProductPK;
import db.ecommerce.model.tables.ProductTable;
import db.ecommerce.model.tables.SoldProductTable;
import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import db.ecommerce.utils.DateConverter;
import db.ecommerce.utils.PRODUCTTYPE;
import db.ecommerce.view.ProducerChangesMenuImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SellingProductController {

    private ProductPK product;
    private ProducerPK producer;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_save;

    @FXML
    private ListView<String> lsvw_selling_products;

    @FXML
    private DatePicker dtpk_start_date;

    @FXML
    private DatePicker dtpk_end_date;

    @FXML
    private DatePicker dtpk_expiry_date;

    @FXML
    private ComboBox<String> cmb_type;

    @FXML
    private TextField txt_price;

    @FXML
    private TextField txt_size;
    private SoldProductTable slpTbl;
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
    public void save(final Event event) throws SQLException {

        if (txt_price != null && dtpk_start_date.getValue() != null && cmb_type.getValue() != null) {
            try {
                double price = Double.parseDouble(txt_price.getText());
                Date start = Date.from(dtpk_start_date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date end = dtpk_end_date.getValue() == null ? null
                        : Date.from(dtpk_end_date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date expiry = dtpk_expiry_date.getValue() == null ? null
                        : Date.from(dtpk_expiry_date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                SoldProduct sp = new SoldProductImpl(price, DateConverter.dateToSqlDate(start),
                        Optional.ofNullable(DateConverter.dateToSqlDate(end)), PRODUCTTYPE.convert(cmb_type.getValue()),
                        Optional.ofNullable(expiry), Optional.ofNullable(txt_size.getText()),
                        product.getCod_prodotto());
                var tmp = slpTbl.save(sp);
                if (tmp.isPresent()) {
                    var alert = new Alert(AlertType.INFORMATION,
                            "Hai messo in vendita il prodotto con codice " + tmp.get().getCodSoldProduct());
                    alert.show();
                    Stage s = (Stage) btn_save.getScene().getWindow();
                    try {
                        new ProducerChangesMenuImpl(s, producer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    throw new SQLException("SoldProduct wasn't saved");
                }

            } catch (NumberFormatException e) {
                var alert = new Alert(AlertType.ERROR, "Il prezzo deve essere un numero");
                alert.show();
            }
        } else {
            var alert = new Alert(AlertType.ERROR, "Compila tutti i campi necessari, come prezzo e inizio vendita");
            alert.show();
        }
    }

    public void initialize() {
        ConnectionProvider c = new ConnectionProviderImpl(Credentials.getUsername(), Credentials.getPassword(),
                Credentials.getDbname());
        this.slpTbl = new SoldProductTable(c.getMySQLConnection());
        new ProductTable(c.getMySQLConnection());
        Platform.runLater(() -> {
            this.shownSoldProduct = slpTbl.allSoldProductsOfProduct(product);
            lsvw_selling_products.setItems(FXCollections.observableList(buildSoldProducts(shownSoldProduct)));
            cmb_type.setItems(
                    FXCollections.observableList(List.of(PRODUCTTYPE.ALIMENTARE.name(), PRODUCTTYPE.VESTIARIO.name())));
            if (!shownSoldProduct.isEmpty()) {
                cmb_type.setDisable(true);
                if (shownSoldProduct.stream().findFirst().get().getType() == PRODUCTTYPE.ALIMENTARE) {
                    txt_size.setDisable(true);
                    cmb_type.getSelectionModel().select(PRODUCTTYPE.ALIMENTARE.name());
                } else {
                    dtpk_expiry_date.setDisable(true);
                    cmb_type.getSelectionModel().select(PRODUCTTYPE.VESTIARIO.name());
                }
            } else {
                cmb_type.setDisable(false);
            }
        });
    }

    private List<String> buildSoldProducts(List<SoldProductPK> list) {
        List<String> l = new ArrayList<>();
        for (var elem : list) {
            String s = "Prodotto ";
            s = s + "Tipo " + elem.getType().name() + " ";
            s = s + "Prezzo " + elem.getPrice() + " ";
            if (elem.getType() == PRODUCTTYPE.ALIMENTARE) {
                s = s + "Scadenza " + elem.getExpiration().get() + " ";
            } else {
                s = s + "Taglia " + elem.getSize().get() + " ";
            }
            l.add(s);
        }
        return l;
    }

}
