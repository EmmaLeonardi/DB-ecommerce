package db.ecommerce.controller;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import db.ecommerce.model.ClientPK;
import db.ecommerce.model.SoldProductPK;
import db.ecommerce.model.tables.ProductTable;
import db.ecommerce.model.tables.SoldProductTable;
import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import db.ecommerce.utils.PRODUCTTYPE;
import db.ecommerce.view.AddressSelectionMenuImpl;
import db.ecommerce.view.ProductDetailsMenuImpl;
import db.ecommerce.view.ShoppingMenuImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ProductsController {

    @FXML
    private ListView<String> lstvw_shopping_cart;

    @FXML
    private ListView<String> lstvw_products;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_size;

    @FXML
    private Button btn_expiry;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_remove;

    @FXML
    private Button btn_pay;

    @FXML
    private Button btn_details;

    @FXML
    private Button btn_reset;

    @FXML
    private Label lbl_total;

    @FXML
    private ChoiceBox<String> cbx_size;

    @FXML
    private DatePicker dtpkr_expiry;

    private ClientPK user;

    private ProductTable prdTbl;

    private SoldProductTable slpTbl;

    private List<SoldProductPK> fullList;

    private List<SoldProductPK> filteredListShop;

    private List<SoldProductPK> cartList;

    public void setClient(ClientPK user) {
        this.user = user;
    }

    @FXML
    public void back(final Event event) {
        Stage s = (Stage) btn_back.getScene().getWindow();
        try {
            new ShoppingMenuImpl(s, user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void size_filter(final Event event) {
        if (cbx_size.getSelectionModel().getSelectedItem() != null) {
            String s = cbx_size.getSelectionModel().getSelectedItem();
            filteredListShop = fullList.stream().filter(p -> p.getSize().isPresent() && p.getSize().get().equals(s))
                    .collect(Collectors.toList());
            this.lstvw_products.setItems(FXCollections.observableList(this.buildProduct(filteredListShop)));
            this.btn_expiry.setDisable(true);
            this.dtpkr_expiry.setDisable(true);
        }
    }

    @FXML
    public void expiry_filter(final Event event) {
        if (dtpkr_expiry.getValue() != null) {
            Date d = Date.from(Instant.from(dtpkr_expiry.getValue().atStartOfDay(ZoneId.systemDefault())));
            filteredListShop = fullList.stream()
                    .filter(p -> p.getExpiration().isPresent() && p.getExpiration().get().compareTo(d) > 0)
                    .collect(Collectors.toList());
            this.lstvw_products.setItems(FXCollections.observableList(this.buildProduct(filteredListShop)));
            this.btn_size.setDisable(true);
            this.cbx_size.setDisable(true);
        }
    }

    @FXML
    public void reset(final Event event) {
        this.lstvw_products.setItems(FXCollections.observableList(this.buildProduct(fullList)));
        filteredListShop = new ArrayList<>(fullList);

        this.btn_expiry.setDisable(false);
        this.dtpkr_expiry.setDisable(false);
        this.btn_size.setDisable(false);
        this.cbx_size.setDisable(false);
        this.cbx_size.getSelectionModel().clearAndSelect(-1);
        this.dtpkr_expiry.setValue(null);

    }

    @FXML
    public void add(final Event event) {
        final int i = lstvw_products.getSelectionModel().getSelectedIndex();
        if (i >= 0) {
            cartList.add(filteredListShop.get(i));
            lstvw_shopping_cart.setItems(FXCollections.observableList(this.buildProduct(cartList)));
            lbl_total.setText(cartList.stream().map(p -> p.getPrice()).reduce(Double::sum).orElse(0.0) + "€");
        }
    }

    @FXML
    public void remove(final Event event) {
        final int i = lstvw_shopping_cart.getSelectionModel().getSelectedIndex();
        if (i >= 0) {
            cartList.remove(i);
            lstvw_shopping_cart.setItems(FXCollections.observableList(this.buildProduct(cartList)));
            lbl_total.setText(cartList.stream().map(p -> p.getPrice()).reduce(Double::sum).orElse(0.0) + "€");
        }
    }

    @FXML
    public void end_pay(final Event event) {
        Stage s = (Stage) btn_back.getScene().getWindow();
        try {
            new AddressSelectionMenuImpl(s, user, Collections.unmodifiableList(cartList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void details(final Event event) {
        Stage s = (Stage) btn_details.getScene().getWindow();
        try {
            new ProductDetailsMenuImpl(s, user, filteredListShop.get(lstvw_products.getSelectionModel().getSelectedIndex()));
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
        this.slpTbl = new SoldProductTable(c.getMySQLConnection());
        this.prdTbl = new ProductTable(c.getMySQLConnection());

        Platform.runLater(() -> {
            List<SoldProductPK> allProduct = slpTbl.findAll().stream().filter(p -> {
                if (p.getEnd().isEmpty() || (p.getEnd().isPresent() && p.getEnd().get().compareTo(new Date()) > 0)) {
                    return true;
                } else {
                    return false;
                }
            }).collect(Collectors.toList());
            this.lstvw_products.setItems(FXCollections.observableList(this.buildProduct(allProduct)));
            this.fullList = new ArrayList<>(allProduct);
            this.filteredListShop = new ArrayList<>(allProduct);
            this.cartList = new ArrayList<>();

            var sizes = allProduct.stream().map(p -> p.getSize()).distinct().filter(s -> s.isPresent())
                    .map(s -> s.get()).collect(Collectors.toList());

            this.cbx_size.setItems(FXCollections.observableList(sizes));

        });

    }

    private List<String> buildProduct(List<SoldProductPK> l) {
        List<String> list = new ArrayList<>();
        for (final SoldProductPK elem : l) {
            var p = prdTbl.findByPrimaryKey(elem.getCodProduct());
            String s = "Prodotto ";
            s = s + "Tipo " + elem.getType().name() + " ";
            s = s + "Prezzo " + elem.getPrice() + " ";
            if (elem.getType() == PRODUCTTYPE.ALIMENTARE) {
                s = s + "Scadenza " + elem.getExpiration().get() + " ";
            } else {
                s = s + "Taglia " + elem.getSize().get() + " ";
            }
            if (p.isPresent()) {
                s = s + "Materiale " + p.get().getMateriale() + " ";
                s = s + "Descrizione: " + p.get().getDescrizione() + " ";
            }
            list.add(s);
        }
        return list;
    }

}
