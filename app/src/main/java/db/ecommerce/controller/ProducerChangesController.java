package db.ecommerce.controller;

import java.util.List;

import db.ecommerce.model.ProducerPK;
import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class ProducerChangesController {

    @FXML
    private Button btn_change;

    @FXML
    private Button btn_new;

    @FXML
    private ListView<String> ltvw_products;

    private ProducerPK producer;

    public void setProducer(ProducerPK p) {
        this.producer = p;

    }

    public void change(final Event event) {
        System.out.println("Change");

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
        ltvw_products.setItems(FXCollections.observableList(List.of("a", "b", "c")));

    }

}
