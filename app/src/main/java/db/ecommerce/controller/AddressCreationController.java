package db.ecommerce.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import db.ecommerce.model.Address;
import db.ecommerce.model.AddressImpl;
import db.ecommerce.model.ClientPK;
import db.ecommerce.model.SoldProductPK;
import db.ecommerce.model.tables.AddressTable;
import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import db.ecommerce.utils.TYPEDELIVERY;
import db.ecommerce.view.AddressCreationMenuImpl;
import db.ecommerce.view.AddressSelectionMenuImpl;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddressCreationController {

    private ClientPK user;

    private List<SoldProductPK> list;

    @FXML
    private TextField txt_via;

    @FXML
    private TextField txt_n_civ;

    @FXML
    private TextField txt_citta;
    @FXML
    private TextField txt_cap;
    @FXML
    private TextField txt_provincia;
    @FXML
    private TextField txt_paese;

    @FXML
    private Button btn_save;

    @FXML
    private Button btn_undo;

    private AddressTable adsTbl;

    public void setShopping(List<SoldProductPK> list) {
        this.list = list;
    }

    public void setClient(ClientPK user) {
        this.user = user;
    }

    @FXML
    public void save(final Event event) {
        if (txt_via.getText() != null && txt_n_civ.getText() != null && txt_citta.getText() != null
                && txt_cap.getText() != null && txt_provincia.getText() != null && txt_paese.getText() != null) {
            try {
                Address a = new AddressImpl(txt_via.getText(), Integer.valueOf(txt_n_civ.getText()),
                        txt_citta.getText(), Integer.valueOf(txt_cap.getText()), txt_provincia.getText(),
                        txt_paese.getText());
                adsTbl.save(a);
                System.out.println(adsTbl.getLastAddressSaved() + "");
            } catch (NumberFormatException e) {
                var alert = new Alert(AlertType.ERROR, "I campi numero civico e CAP devono essere solo numerici");
                alert.show();
            }
        }
        System.out.println("salva");
    }

    @FXML
    public void back(final Event event) {
        Stage s = (Stage) btn_undo.getScene().getWindow();
        try {
            new AddressSelectionMenuImpl(s, user, Collections.unmodifiableList(list));
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
        this.adsTbl = new AddressTable(c.getMySQLConnection());
    }

}
