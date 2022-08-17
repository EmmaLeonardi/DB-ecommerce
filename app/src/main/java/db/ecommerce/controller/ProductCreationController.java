package db.ecommerce.controller;

import java.io.IOException;
import java.util.List;

import db.ecommerce.model.ProducerPK;
import db.ecommerce.model.tables.ProductTable;
import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import db.ecommerce.view.ProducerChangesMenuImpl;
import db.ecommerce.view.SellingProductMenuImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ProductCreationController {

    @FXML
    private Button btn_save;

    @FXML
    private Button btn_back;

    @FXML
    private ListView<String> lsvw_factory;

    @FXML
    private TextField txt_material;

    @FXML
    private TextArea txt_description;

    private ProducerPK producer;

    private ProductTable prdTbl;

    public void setProducer(ProducerPK producer) {
        this.producer = producer;
    }

    public void back(final Event event) {
        Stage s = (Stage) btn_back.getScene().getWindow();
        try {
            new ProducerChangesMenuImpl(s, producer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(final Event event) {
        System.out.println("Salva");
    }

    public void initialize() {
        ConnectionProvider c = new ConnectionProviderImpl(Credentials.getUsername(), Credentials.getPassword(),
                Credentials.getDbname());
        this.prdTbl = new ProductTable(c.getMySQLConnection());
        Platform.runLater(() -> {
            lsvw_factory.setItems(FXCollections.observableList(List.of("a", "b", "c")));
        });
    }

}
