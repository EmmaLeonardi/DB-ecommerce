package db.ecommerce.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.ecommerce.model.FactoryPK;
import db.ecommerce.model.ProducerPK;
import db.ecommerce.model.Product;
import db.ecommerce.model.ProductImpl;
import db.ecommerce.model.tables.FactoryTable;
import db.ecommerce.model.tables.ProductTable;
import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import db.ecommerce.view.ProducerChangesMenuImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
    private FactoryTable fctTbl;

    private List<FactoryPK> showFactory;

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

    public void save(final Event event) throws SQLException {
        if (txt_description.getText() != null && txt_material.getText() != null
                && lsvw_factory.getSelectionModel().getSelectedIndex() != -1) {
            Product p = new ProductImpl(txt_material.getText(), txt_description.getText(), producer.getCod_produttore(),
                    showFactory.get(lsvw_factory.getSelectionModel().getSelectedIndex()).getCodFabbrica());
            var tmp = prdTbl.save(p);
            if (tmp.isEmpty()) {
                throw new SQLException("Address wasn't saved");
            } else {
                var alert = new Alert(AlertType.INFORMATION, "Hai creato un nuovo prodotto con codice "
                        + tmp.get().getCod_prodotto() + " ora ricordati di metterlo in vendita ");
                alert.show();
                Stage s = (Stage) btn_back.getScene().getWindow();
                try {
                    new ProducerChangesMenuImpl(s, producer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            var alert = new Alert(AlertType.ERROR, "Inserisci tutti i dati e seleziona una fabbrica");
            alert.show();
        }
    }

    public void initialize() {
        ConnectionProvider c = new ConnectionProviderImpl(Credentials.getUsername(), Credentials.getPassword(),
                Credentials.getDbname());
        this.fctTbl = new FactoryTable(c.getMySQLConnection());
        this.prdTbl = new ProductTable(c.getMySQLConnection());
        Platform.runLater(() -> {
            this.showFactory = fctTbl.allFactoryOfProducer(producer);
            lsvw_factory.setItems(FXCollections.observableList(buildFactory(showFactory)));
        });
    }

    private List<String> buildFactory(List<FactoryPK> l) {
        List<String> list = new ArrayList<>();
        for (FactoryPK elem : l) {
            String s = elem.getCodFabbrica() + " " + elem.getVia() + " " + elem.getnCiv() + " " + elem.getCitta();
            list.add(s);
        }
        return list;
    }

}
