package db.ecommerce.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.ecommerce.model.Factory;
import db.ecommerce.model.FactoryImpl;
import db.ecommerce.model.FactoryPK;
import db.ecommerce.model.tables.FactoryTable;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class FactoryController {

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_save;

    @FXML
    private Button btn_change;

    @FXML
    private Button btn_new;

    @FXML
    private Label lbl_cod_fabbrica;

    @FXML
    private ListView<String> lstvw_factory;

    @FXML
    private TextField txt_address_street;

    @FXML
    private TextField txt_address_city;

    @FXML
    private TextField txt_address_number;

    private FactoryTable fctTbl;

    private List<FactoryPK> showFactory;

    private final static String NOFACTORY = "--";

    @FXML
    public void back(final Event event) {
        if (btn_save.isDisabled() == false ) {
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
        if (lbl_cod_fabbrica.getText().equals(NOFACTORY)) {
            // E' un cliente nuovo
            if (txt_address_city.getText() != "" && txt_address_number.getText() != ""
                    && txt_address_street.getText() != "") {
                try {
                    var civ = Integer.parseInt(txt_address_number.getText());
                    Factory f = new FactoryImpl(txt_address_street.getText(), civ, txt_address_city.getText());
                    var tmp = fctTbl.save(f);
                    if (tmp.isPresent()) {
                        var alert = new Alert(AlertType.INFORMATION, "Hai salvato correttamente la fabbrica!");
                        alert.show();
                        lbl_cod_fabbrica.setText(NOFACTORY);
                        txt_address_city.setText("");
                        txt_address_number.setText("");
                        txt_address_street.setText("");
                        lstvw_factory.setDisable(false);
                        lstvw_factory.getSelectionModel().select(-1);
                        this.showFactory = fctTbl.findAll();
                        lstvw_factory.setItems(FXCollections.observableList(buildFactory(showFactory)));
                        setDisabledAll(true);
                        this.btn_save.setDisable(true);
                        this.btn_change.setDisable(true);
                        this.btn_new.setDisable(false);
                    } else {
                        throw new SQLException("Factory wasn't saved");
                    }
                } catch (NumberFormatException e) {
                    var alert = new Alert(AlertType.ERROR, "Il numero civico deve essere numerico");
                    alert.show();
                }
            } else {
                var alert = new Alert(AlertType.ERROR, "Tutti i campi sono obbligatori");
                alert.show();
            }
        } else {
            // E' un cliente vecchio
            if (txt_address_city.getText() != "" && txt_address_street.getText() != ""
                    && txt_address_number.getText() != "") {
                try {
                    var civ = Integer.parseInt(txt_address_number.getText());
                    var id = Integer.parseInt(lbl_cod_fabbrica.getText());
                    FactoryPK c = new FactoryPK(txt_address_street.getText(), civ, txt_address_city.getText(), id);
                    var app = fctTbl.update(c);
                    if (app) {
                        var alert = new Alert(AlertType.INFORMATION, "Hai aggiornato correttamente la fabbrica!");
                        alert.show();
                        lbl_cod_fabbrica.setText(NOFACTORY);
                        txt_address_city.setText("");
                        txt_address_number.setText("");
                        txt_address_street.setText("");
                        lstvw_factory.setDisable(false);
                        lstvw_factory.getSelectionModel().select(-1);
                        this.showFactory = fctTbl.findAll();
                        lstvw_factory.setItems(FXCollections.observableList(buildFactory(showFactory)));
                        setDisabledAll(true);
                        this.btn_save.setDisable(true);
                        this.btn_change.setDisable(true);
                        this.btn_new.setDisable(false);
                    } else {
                        throw new SQLException("Factory wasn't updated");
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
    public void newFactory(final Event event) {
        setDisabledAll(false);
        lbl_cod_fabbrica.setText(NOFACTORY);
        btn_change.setDisable(true);
        btn_save.setDisable(false);
        btn_new.setDisable(true);
        lstvw_factory.setDisable(true);
        txt_address_city.setText("");
        txt_address_number.setText("");
        txt_address_street.setText("");

    }

    @FXML
    public void change(final Event event) {
        setDisabledAll(false);
        btn_change.setDisable(true);
        btn_save.setDisable(false);
        btn_new.setDisable(true);
        lstvw_factory.setDisable(true);
    }

    public void initialize() {
        ConnectionProvider c = new ConnectionProviderImpl(Credentials.getUsername(), Credentials.getPassword(),
                Credentials.getDbname());
        this.fctTbl = new FactoryTable(c.getMySQLConnection());
        Platform.runLater(() -> {
            this.showFactory = fctTbl.findAll();
            lstvw_factory.setItems(FXCollections.observableList(buildFactory(showFactory)));
            this.setDisabledAll(true);
            this.btn_save.setDisable(true);
            this.btn_change.setDisable(true);
            lbl_cod_fabbrica.setText(NOFACTORY);
            txt_address_city.setText("");
            txt_address_number.setText("");
            txt_address_street.setText("");

            lstvw_factory.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (lstvw_factory.getSelectionModel().getSelectedIndex() != -1) {
                        var item = showFactory.get(lstvw_factory.getSelectionModel().getSelectedIndex());
                        setDisabledAll(true);
                        txt_address_city.setText(item.getCitta());
                        txt_address_number.setText(item.getnCiv() + "");
                        txt_address_street.setText(item.getVia());
                        lbl_cod_fabbrica.setText(item.getCodFabbrica() + "");
                        btn_change.setDisable(false);

                    } else {
                        setDisabledAll(true);
                        btn_save.setDisable(true);
                        btn_change.setDisable(true);
                        txt_address_city.setText("");
                        txt_address_number.setText("");
                        txt_address_street.setText("");
                        lbl_cod_fabbrica.setText(NOFACTORY);
                    }
                }
            });
        });
    }

    private List<String> buildFactory(List<FactoryPK> all) {
        List<String> list = new ArrayList<>();
        for (var elem : all) {
            String s = "" + elem.getCodFabbrica() + " " + elem.getVia() + " " + elem.getCitta();
            list.add(s);
        }
        return list;
    }

    /** The same thing as giving b to all the TextFields and the DatePicker */
    private void setDisabledAll(boolean b) {
        this.txt_address_city.setDisable(b);
        this.txt_address_number.setDisable(b);
        this.txt_address_street.setDisable(b);
    }

}
