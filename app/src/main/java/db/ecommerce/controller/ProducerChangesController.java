package db.ecommerce.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import db.ecommerce.model.ProducerPK;
import db.ecommerce.model.ProductPK;
import db.ecommerce.model.tables.ProductTable;
import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import db.ecommerce.view.SellingProductMenuImpl;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ProducerChangesController {

    @FXML
    private Button btn_change;

    @FXML
    private Button btn_new;

    @FXML
    private ListView<String> ltvw_products;

    private ProducerPK producer;

    private ProductTable prdTbl;

    private List<ProductPK> showProducts;

    public void setProducer(ProducerPK p) {
        this.producer = p;

    }

    public void change(final Event event) {
        if (ltvw_products.getSelectionModel().getSelectedIndex() != -1 && !showProducts.isEmpty()) {
            Stage s = (Stage) btn_change.getScene().getWindow();
            try {
                new SellingProductMenuImpl(s, producer,
                        showProducts.get(ltvw_products.getSelectionModel().getSelectedIndex()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void new_product(final Event event) {
        System.out.println("New");
    }

    /***
     * This method is called at the start and inizializes the list view
     */

    public void initialize() {
        ConnectionProvider c = new ConnectionProviderImpl(Credentials.getUsername(), Credentials.getPassword(),
                Credentials.getDbname());
        this.prdTbl = new ProductTable(c.getMySQLConnection());
        btn_change.setDisable(true);
        Platform.runLater(() -> {
            this.showProducts = prdTbl.allProductsOfProducer(producer);
            ltvw_products.setItems(FXCollections.observableList(buildProduct(showProducts)));
            ltvw_products.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (ltvw_products.getSelectionModel().getSelectedIndex() != -1) {
                        btn_change.setDisable(false);
                    } else {
                        btn_change.setDisable(true);
                    }
                }
            });
        });

    }

    private List<String> buildProduct(List<ProductPK> l) {
        List<String> list = new ArrayList<>();
        for (var elem : l) {
            String s = "" + elem.getCod_prodotto() + " " + elem.getDescrizione() + " " + elem.getMateriale()+" Fabbrica N° "+elem.getCod_fabbrica();
            list.add(s);
        }
        return list;
    }

}
