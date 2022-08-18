package db.ecommerce.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import db.ecommerce.model.ClientPK;
import db.ecommerce.model.Client;
import db.ecommerce.model.ClientImpl;
import db.ecommerce.model.tables.ClientTable;
import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import db.ecommerce.view.AdminMenuImpl;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ClientController {

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_save;

    @FXML
    private Button btn_change;

    @FXML
    private Button btn_new;

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
    private Label lbl_cod_cliente;

    private ClientTable clnTbl;

    private List<ClientPK> showClients;
    
    private final static String NOCLIENT="--";

    @FXML
    public void back(final Event event) {
        if (btn_save.isDisabled() == false || txt_cf.getText() != "" || txt_name.getText() != ""
                || txt_surname.getText() != "" || dtpkr_birthday.getValue() != null || txt_email.getText() != ""
                || txt_phone_number.getText() != "" || txt_cc.getText() != "" || txt_address_city.getText() != ""
                || txt_address_street.getText() != "" || txt_address_number.getText() != "") {
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
    public void save(final Event event) throws SQLException {
        if (lbl_cod_cliente.getText().equals(NOCLIENT)) {
            // E' un cliente nuovo
            if (txt_cf.getText() != "" && txt_name.getText() != "" && txt_surname.getText() != ""
                    && dtpkr_birthday.getValue() != null && txt_email.getText() != ""
                    && txt_phone_number.getText() != "" && txt_cc.getText() != "" && txt_address_city.getText() != ""
                    && txt_address_street.getText() != "" && txt_address_number.getText() != "") {
                try {
                    var num = Integer.parseInt(txt_phone_number.getText());
                    var civ = Integer.parseInt(txt_address_number.getText());
                    Client c = new ClientImpl(txt_cf.getText(), txt_name.getText(), txt_surname.getText(),
                            Date.from(dtpkr_birthday.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                            txt_email.getText(), num, txt_address_street.getText(), civ, txt_address_city.getText(),
                            txt_cc.getText());
                    var tmp = clnTbl.save(c);
                    if (tmp.isPresent()) {
                        var alert = new Alert(AlertType.INFORMATION,
                                "Hai salvato correttamente il nuovo cliente! Ora può usare l'applicazione con identificatore "
                                        + tmp.get().getCod_cliente() + " e password " + tmp.get().getName());
                        alert.show();
                        lbl_cod_cliente.setText(NOCLIENT);
                        txt_address_city.setText("");
                        txt_address_number.setText("");
                        txt_address_street.setText("");
                        txt_cf.setText("");
                        txt_name.setText("");
                        txt_surname.setText("");
                        dtpkr_birthday.setValue(null);
                        txt_email.setText("");
                        txt_phone_number.setText("");
                        txt_cc.setText("");
                        lstvw_clients.setDisable(false);
                        lstvw_clients.getSelectionModel().select(-1);
                        this.showClients = clnTbl.findAll();
                        lstvw_clients.setItems(FXCollections.observableList(buildClient(showClients)));
                        setDisabledAll(true);
                        this.btn_save.setDisable(true);
                        this.btn_change.setDisable(true);
                        this.btn_delete.setDisable(true);
                        this.btn_new.setDisable(false);
                    } else {
                        throw new SQLException("Client wasn't saved");
                    }
                } catch (NumberFormatException e) {
                    var alert = new Alert(AlertType.ERROR, "Il campo telefono e numero civico devono essere numerici");
                    alert.show();
                }
            } else {
                var alert = new Alert(AlertType.ERROR, "Tutti i campi sono obbligatori");
                alert.show();
            }
        } else {
            // E' un cliente vecchio
            if (txt_cf.getText() != "" && txt_name.getText() != "" && txt_surname.getText() != ""
                    && dtpkr_birthday.getValue() != null && txt_email.getText() != ""
                    && txt_phone_number.getText() != "" && txt_cc.getText() != "" && txt_address_city.getText() != ""
                    && txt_address_street.getText() != "" && txt_address_number.getText() != "") {
                try {
                    var num = Integer.parseInt(txt_phone_number.getText());
                    var civ = Integer.parseInt(txt_address_number.getText());
                    var id = Integer.parseInt(lbl_cod_cliente.getText());
                    ClientPK c = new ClientPK(id, txt_cf.getText(), txt_name.getText(), txt_surname.getText(),
                            Date.from(dtpkr_birthday.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                            txt_email.getText(), num, txt_address_street.getText(), civ, txt_address_city.getText(),
                            txt_cc.getText());
                    var app = clnTbl.update(c);
                    if (app) {
                        var alert = new Alert(AlertType.INFORMATION, "Hai aggiornato correttamente il cliente!");
                        alert.show();
                        lbl_cod_cliente.setText(NOCLIENT);
                        txt_address_city.setText("");
                        txt_address_number.setText("");
                        txt_address_street.setText("");
                        txt_cf.setText("");
                        txt_name.setText("");
                        txt_surname.setText("");
                        dtpkr_birthday.setValue(null);
                        txt_email.setText("");
                        txt_phone_number.setText("");
                        txt_cc.setText("");
                        lstvw_clients.setDisable(false);
                        lstvw_clients.getSelectionModel().select(-1);
                        this.showClients = clnTbl.findAll();
                        lstvw_clients.setItems(FXCollections.observableList(buildClient(showClients)));
                        setDisabledAll(true);
                        this.btn_save.setDisable(true);
                        this.btn_change.setDisable(true);
                        this.btn_delete.setDisable(true);
                        this.btn_new.setDisable(false);
                    } else {
                        throw new SQLException("Client wasn't updated");
                    }
                } catch (NumberFormatException e) {
                    var alert = new Alert(AlertType.ERROR, "Il campo telefono e numero civico devono essere numerici");
                    alert.show();
                }
            } else {
                var alert = new Alert(AlertType.ERROR, "Tutti i campi sono obbligatori");
                alert.show();
            }
        }
    }

    @FXML
    public void newClient(final Event event) {
        setDisabledAll(false);
        lbl_cod_cliente.setText(NOCLIENT);
        btn_change.setDisable(true);
        btn_save.setDisable(false);
        btn_new.setDisable(true);
        btn_delete.setDisable(true);
        lstvw_clients.setDisable(true);
        txt_address_city.setText("");
        txt_address_number.setText("");
        txt_address_street.setText("");
        txt_cf.setText("");
        txt_name.setText("");
        txt_surname.setText("");
        dtpkr_birthday.setValue(null);
        txt_email.setText("");
        txt_phone_number.setText("");
        txt_cc.setText("");

    }

    @FXML
    public void change(final Event event) {
        setDisabledAll(false);
        btn_change.setDisable(true);
        btn_save.setDisable(false);
        btn_new.setDisable(true);
        btn_delete.setDisable(true);
        lstvw_clients.setDisable(true);
    }

    @FXML
    public void delete(final Event event) {
        System.out.println("Elimina");
    }

    public void initialize() {
        ConnectionProvider c = new ConnectionProviderImpl(Credentials.getUsername(), Credentials.getPassword(),
                Credentials.getDbname());
        this.clnTbl = new ClientTable(c.getMySQLConnection());
        Platform.runLater(() -> {
            this.showClients = clnTbl.findAll();
            lstvw_clients.setItems(FXCollections.observableList(buildClient(showClients)));
            this.setDisabledAll(true);
            this.btn_save.setDisable(true);
            this.btn_change.setDisable(true);
            this.btn_delete.setDisable(true);
            txt_address_city.setText("");
            txt_address_number.setText("");
            txt_address_street.setText("");
            txt_cf.setText("");
            txt_name.setText("");
            txt_surname.setText("");
            dtpkr_birthday.setValue(null);
            txt_email.setText("");
            txt_phone_number.setText("");
            txt_cc.setText("");
            lbl_cod_cliente.setText(NOCLIENT);

            lstvw_clients.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (lstvw_clients.getSelectionModel().getSelectedIndex() != -1) {
                        var item = showClients.get(lstvw_clients.getSelectionModel().getSelectedIndex());
                        setDisabledAll(true);
                        lbl_cod_cliente.setText(item.getCod_cliente() + "");
                        txt_address_city.setText(item.getResCity());
                        txt_address_number.setText(item.getResNumber() + "");
                        txt_address_street.setText(item.getResStreet());
                        txt_cf.setText(item.getCodFis());
                        txt_name.setText(item.getName());
                        txt_surname.setText(item.getSurname());
                        dtpkr_birthday
                                .setValue(item.getDateBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        txt_email.setText(item.getEmail());
                        txt_phone_number.setText(item.getPhoneNumber() + "");
                        txt_cc.setText(item.getBankInfo());
                        btn_change.setDisable(false);
                        btn_delete.setDisable(false);

                    } else {
                        setDisabledAll(true);
                        btn_save.setDisable(true);
                        btn_change.setDisable(true);
                        btn_delete.setDisable(true);
                        txt_address_city.setText("");
                        txt_address_number.setText("");
                        txt_address_street.setText("");
                        txt_cf.setText("");
                        txt_name.setText("");
                        txt_surname.setText("");
                        dtpkr_birthday.setValue(null);
                        txt_email.setText("");
                        txt_phone_number.setText("");
                        txt_cc.setText("");
                        lbl_cod_cliente.setText(NOCLIENT);
                    }
                }
            });
        });
    }

    private List<String> buildClient(List<ClientPK> all) {
        List<String> list = new ArrayList<>();
        for (var elem : all) {
            String s = "" + elem.getCod_cliente() + " " + elem.getName() + " " + elem.getSurname();
            list.add(s);
        }
        return list;
    }

    /** The same thing as giving b to all the TextFields and the DatePicker */
    private void setDisabledAll(boolean b) {
        this.txt_address_city.setDisable(b);
        this.txt_address_number.setDisable(b);
        this.txt_address_street.setDisable(b);
        this.txt_cf.setDisable(b);
        this.txt_name.setDisable(b);
        this.txt_surname.setDisable(b);
        this.dtpkr_birthday.setDisable(b);
        this.txt_email.setDisable(b);
        this.txt_phone_number.setDisable(b);
        this.txt_cc.setDisable(b);
    }
}
