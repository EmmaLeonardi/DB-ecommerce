package db.ecommerce.controller;

import java.io.IOException;
import java.util.List;

import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import db.ecommerce.view.AdminMenuImpl;
import db.ecommerce.view.ClientMenuImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ClientController {

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_update_or_new;

    @FXML
    private Button btn_delete;

    @FXML
    private ListView<String> lstvw_clients;

    @FXML
    private TextField txt_cf;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_surname;

    @FXML
    private DatePicker dtpkr_birthday;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_phone_number;

    @FXML
    private TextField txt_cc;

    @FXML
    private TextField txt_address_street;

    @FXML
    private TextField txt_address_number;

    @FXML
    private TextField txt_address_city;

    @FXML
    public void back(final Event event) {
        if (txt_cf.getText() != "" || txt_name.getText() != "" || txt_surname.getText() != ""
                || dtpkr_birthday.getValue() != null || txt_email.getText() != "" || txt_phone_number.getText() != ""
                || txt_cc.getText() != "" || txt_address_city.getText() != "" || txt_address_street.getText() != ""
                || txt_address_number.getText() != "") {

            System.out.println("CF" + txt_cf.getText() + "Nome " + txt_name.getText() + "Cognome "
                    + txt_surname.getText() + "Data " + dtpkr_birthday.getValue() + "Email " + txt_email.getText()
                    + "Tel " + txt_phone_number.getText() + "CC" + txt_cc.getText() + "Citta"
                    + txt_address_city.getText() + "Via" + txt_address_street.getText() + "N"
                    + txt_address_number.getText());
            var alert = new Alert(AlertType.WARNING,
                    "Stai per tornare al menu principale, ma perderai i dati inseriti. Continuare?", ButtonType.NO,
                    ButtonType.YES);
            alert.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
                Stage s = (Stage) btn_back.getScene().getWindow();
                try {
                    new AdminMenuImpl(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } else {
            Stage s = (Stage) btn_back.getScene().getWindow();
            try {
                new AdminMenuImpl(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void save(final Event event) {
        System.out.println("Salva");
    }

    @FXML
    public void delete(final Event event) {
        System.out.println("Elimina");
    }

    public void initialize() {
        ConnectionProvider c = new ConnectionProviderImpl(Credentials.getUsername(), Credentials.getPassword(),
                Credentials.getDbname());
        Platform.runLater(() -> {
            lstvw_clients.setItems(FXCollections.observableList(List.of("a", "b", "c")));
        });
    }

}
