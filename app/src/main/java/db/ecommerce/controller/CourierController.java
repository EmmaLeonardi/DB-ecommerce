package db.ecommerce.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import db.ecommerce.model.Courier;
import db.ecommerce.model.CourierImpl;
import db.ecommerce.model.CourierPK;
import db.ecommerce.model.tables.CourierTable;
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

public class CourierController {

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_save;

    @FXML
    private Button btn_change;

    @FXML
    private Button btn_new;

    @FXML
    private ListView<String> lstvw_courier;

    @FXML
    private TextField txt_cf;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_surname;

    @FXML
    private DatePicker dtpkr_birthday;

    @FXML
    private TextField txt_license_number;

    @FXML
    private TextField txt_licence_country;

    @FXML
    private Label lbl_cod_corriere;

    private CourierTable curTbl;

    private List<CourierPK> showCourier;

    private final static String NOCOURIER = "--";

    @FXML
    public void back(final Event event) {
        if (btn_save.isDisabled() == false) {
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
        if (lbl_cod_corriere.getText().equals(NOCOURIER)) {
            // E' un corriere nuovo
            if (txt_cf.getText() != "" && txt_name.getText() != "" && txt_surname.getText() != ""
                    && dtpkr_birthday.getValue() != null && txt_licence_country.getText() != ""
                    && txt_license_number.getText() != "") {
                Courier c = new CourierImpl(txt_cf.getText(), txt_name.getText(), txt_surname.getText(),
                        Date.from(dtpkr_birthday.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        txt_license_number.getText(), txt_licence_country.getText());
                var tmp = curTbl.save(c);
                if (tmp.isPresent()) {
                    var alert = new Alert(AlertType.INFORMATION,
                            "Hai salvato correttamente il nuovo corriere! Ora può usare l'applicazione con identificatore "
                                    + tmp.get().getCod_corriere() + " e password " + tmp.get().getName());
                    alert.show();
                    txt_name.setText("");
                    txt_surname.setText("");
                    dtpkr_birthday.setValue(null);
                    txt_cf.setText("");
                    txt_name.setText("");
                    txt_surname.setText("");
                    txt_licence_country.setText("");
                    txt_license_number.setText("");
                    dtpkr_birthday.setValue(null);
                    lbl_cod_corriere.setText(NOCOURIER);
                    lstvw_courier.setDisable(false);
                    lstvw_courier.getSelectionModel().select(-1);
                    this.showCourier = curTbl.findAll();
                    lstvw_courier.setItems(FXCollections.observableList(buildCourier(showCourier)));
                    setDisabledAll(true);
                    this.btn_save.setDisable(true);
                    this.btn_change.setDisable(true);
                    this.btn_new.setDisable(false);
                } else {
                    throw new SQLException("Courier wasn't saved");
                }
            } else {
                var alert = new Alert(AlertType.ERROR, "Tutti i campi sono obbligatori");
                alert.show();
            }
        } else {
            // E' un corriere vecchio
            if (txt_cf.getText() != "" && txt_name.getText() != "" && txt_surname.getText() != ""
                    && dtpkr_birthday.getValue() != null && txt_licence_country.getText() != ""
                    && txt_license_number.getText() != "") {
                int id = Integer.parseInt(lbl_cod_corriere.getText());
                CourierPK c = new CourierPK(id, txt_cf.getText(), txt_name.getText(), txt_surname.getText(),
                        Date.from(dtpkr_birthday.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        txt_license_number.getText(), txt_licence_country.getText());
                var app = curTbl.update(c);
                if (app) {
                    var alert = new Alert(AlertType.INFORMATION, "Hai aggiornato correttamente il corriere!");
                    alert.show();
                    txt_name.setText("");
                    txt_surname.setText("");
                    dtpkr_birthday.setValue(null);
                    txt_cf.setText("");
                    txt_name.setText("");
                    txt_surname.setText("");
                    txt_licence_country.setText("");
                    txt_license_number.setText("");
                    dtpkr_birthday.setValue(null);
                    lbl_cod_corriere.setText(NOCOURIER);
                    lstvw_courier.setDisable(false);
                    lstvw_courier.getSelectionModel().select(-1);
                    this.showCourier = curTbl.findAll();
                    lstvw_courier.setItems(FXCollections.observableList(buildCourier(showCourier)));
                    setDisabledAll(true);
                    this.btn_save.setDisable(true);
                    this.btn_change.setDisable(true);
                    this.btn_new.setDisable(false);
                } else {
                    throw new SQLException("Courier wasn't updated");
                }
            } else {
                var alert = new Alert(AlertType.ERROR, "Tutti i campi sono obbligatori");
                alert.show();
            }
        }

    }

    @FXML
    public void newCourier(final Event event) {
        setDisabledAll(false);
        lbl_cod_corriere.setText(NOCOURIER);
        btn_change.setDisable(true);
        btn_save.setDisable(false);
        btn_new.setDisable(true);
        txt_cf.setText("");
        txt_name.setText("");
        txt_surname.setText("");
        txt_licence_country.setText("");
        txt_license_number.setText("");
        dtpkr_birthday.setValue(null);

    }

    @FXML
    public void change(final Event event) {
        setDisabledAll(false);
        btn_change.setDisable(true);
        btn_save.setDisable(false);
        btn_new.setDisable(true);
        lstvw_courier.setDisable(true);
    }

    public void initialize() {
        ConnectionProvider c = new ConnectionProviderImpl(Credentials.getUsername(), Credentials.getPassword(),
                Credentials.getDbname());
        this.curTbl = new CourierTable(c.getMySQLConnection());
        Platform.runLater(() -> {
            this.showCourier = curTbl.findAll();
            lstvw_courier.setItems(FXCollections.observableList(buildCourier(showCourier)));
            this.setDisabledAll(true);
            this.btn_save.setDisable(true);
            this.btn_change.setDisable(true);
            txt_cf.setText("");
            txt_name.setText("");
            txt_surname.setText("");
            txt_licence_country.setText("");
            txt_license_number.setText("");
            dtpkr_birthday.setValue(null);
            lbl_cod_corriere.setText(NOCOURIER);

            lstvw_courier.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (lstvw_courier.getSelectionModel().getSelectedIndex() != -1) {
                        var item = showCourier.get(lstvw_courier.getSelectionModel().getSelectedIndex());
                        setDisabledAll(true);
                        lbl_cod_corriere.setText(item.getCod_corriere() + "");
                        txt_cf.setText(item.getCodFis());
                        txt_name.setText(item.getName());
                        txt_surname.setText(item.getSurname());
                        dtpkr_birthday
                                .setValue(item.getDateBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        txt_licence_country.setText(item.getDrivingNat());
                        txt_license_number.setText(item.getCodDrivingLic());
                        btn_change.setDisable(false);

                    } else {
                        setDisabledAll(true);
                        btn_save.setDisable(true);
                        btn_change.setDisable(true);
                        txt_cf.setText("");
                        txt_name.setText("");
                        txt_surname.setText("");
                        dtpkr_birthday.setValue(null);
                        txt_licence_country.setText("");
                        txt_license_number.setText("");
                        lbl_cod_corriere.setText(NOCOURIER);
                    }
                }
            });
        });
    }

    private List<String> buildCourier(List<CourierPK> all) {
        List<String> list = new ArrayList<>();
        for (var elem : all) {
            String s = "" + elem.getCod_corriere() + " " + elem.getName() + " " + elem.getSurname();
            list.add(s);
        }
        return list;
    }

    /** The same thing as giving b to all the TextFields and the DatePicker */
    private void setDisabledAll(boolean b) {
        this.txt_cf.setDisable(b);
        this.txt_name.setDisable(b);
        this.txt_surname.setDisable(b);
        this.dtpkr_birthday.setDisable(b);
        this.txt_licence_country.setDisable(b);
        this.txt_license_number.setDisable(b);
    }

}
