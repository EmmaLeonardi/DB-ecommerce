package db.ecommerce.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import db.ecommerce.model.Address;
import db.ecommerce.model.AddressImpl;
import db.ecommerce.model.ClientPK;
import db.ecommerce.model.SoldProductPK;
import db.ecommerce.view.AddressSelectionMenuImpl;
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
                Stage s = (Stage) btn_undo.getScene().getWindow();
                try {
                    new AddressSelectionMenuImpl(s, user, Collections.unmodifiableList(list), List.of(a));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (NumberFormatException e) {
                var alert = new Alert(AlertType.ERROR,
                        "Riempi tutti i campi, ricorda che numero civico e CAP devono essere solo numerici");
                alert.show();
            }
        }
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
     * This method is called at the start
     */
    public void initialize() {
    }

}
