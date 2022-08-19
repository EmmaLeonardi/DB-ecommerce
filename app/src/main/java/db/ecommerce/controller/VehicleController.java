package db.ecommerce.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.ecommerce.model.Vehicle;
import db.ecommerce.model.VehicleImpl;
import db.ecommerce.model.tables.VehicleTable;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class VehicleController {

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
    private ListView<String> lstvw_vehicles;

    @FXML
    private TextField txt_license;

    @FXML
    private TextField txt_country;

    @FXML
    private TextField txt_brand;

    @FXML
    private TextField txt_type;

    private VehicleTable vhcTbl;

    private List<Vehicle> showVehicle;

    private final static String NOVEHICLE = "--";

    @FXML
    public void back(final Event event) {
        if (btn_save.isDisabled() == false || txt_license.getText() != "" || txt_country.getText() != ""
                || txt_brand.getText() != "" || txt_type.getText() != "") {
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
        if (vhcTbl.findByPrimaryKey(txt_license.getText()).isEmpty()) {
            // E' un veicolo nuovo
            if (txt_license.getText() != "" && txt_country.getText() != "" && txt_brand.getText() != ""
                    && txt_type.getText() != "") {
                Vehicle v = new VehicleImpl(txt_license.getText(), txt_country.getText(), txt_brand.getText(),
                        txt_type.getText());
                var tmp = vhcTbl.save(v);
                if (tmp.isPresent()) {
                    var alert = new Alert(AlertType.INFORMATION, "Hai salvato correttamente il nuovo veicolo!");
                    alert.show();
                    txt_license.setText(NOVEHICLE);
                    txt_country.setText("");
                    txt_brand.setText("");
                    txt_type.setText("");
                    lstvw_vehicles.setDisable(false);
                    lstvw_vehicles.getSelectionModel().select(-1);
                    this.showVehicle = vhcTbl.findAll();
                    lstvw_vehicles.setItems(FXCollections.observableList(buildVehicles(showVehicle)));
                    setDisabledAll(true);
                    this.btn_save.setDisable(true);
                    this.btn_change.setDisable(true);
                    this.btn_delete.setDisable(true);
                    this.btn_new.setDisable(false);
                } else {
                    throw new SQLException("Vehicle wasn't saved");
                }
            } else {
                var alert = new Alert(AlertType.ERROR, "Tutti i campi sono obbligatori");
                alert.show();
            }
        } else {
            // E' un cliente vecchio
            if (txt_license.getText() != "" && txt_country.getText() != "" && txt_brand.getText() != ""
                    && txt_type.getText() != "") {
                Vehicle v = new VehicleImpl(txt_license.getText(), txt_country.getText(), txt_brand.getText(),
                        txt_type.getText());
                var app = vhcTbl.update(v);
                if (app) {
                    var alert = new Alert(AlertType.INFORMATION, "Hai aggiornato correttamente il veicolo!");
                    alert.show();
                    txt_license.setText(NOVEHICLE);
                    txt_country.setText("");
                    txt_brand.setText("");
                    txt_type.setText("");
                    lstvw_vehicles.setDisable(false);
                    lstvw_vehicles.getSelectionModel().select(-1);
                    this.showVehicle = vhcTbl.findAll();
                    lstvw_vehicles.setItems(FXCollections.observableList(buildVehicles(showVehicle)));
                    setDisabledAll(true);
                    this.btn_save.setDisable(true);
                    this.btn_change.setDisable(true);
                    this.btn_delete.setDisable(true);
                    this.btn_new.setDisable(false);
                } else {
                    throw new SQLException("Vehicle wasn't updated");
                }
            } else {
                var alert = new Alert(AlertType.ERROR, "Tutti i campi sono obbligatori");
                alert.show();
            }
        }
    }

    @FXML
    public void newVehicle(final Event event) {
        setDisabledAll(false);
        txt_license.setText(NOVEHICLE);
        txt_license.setDisable(false);
        btn_change.setDisable(true);
        btn_save.setDisable(false);
        btn_new.setDisable(true);
        btn_delete.setDisable(true);
        lstvw_vehicles.setDisable(true);
        txt_type.setText("");
        txt_country.setText("");
        txt_brand.setText("");

    }

    @FXML
    public void change(final Event event) {
        setDisabledAll(false);
        btn_change.setDisable(true);
        btn_save.setDisable(false);
        btn_new.setDisable(true);
        btn_delete.setDisable(true);
        lstvw_vehicles.setDisable(true);
        txt_license.setDisable(true);
    }

    @FXML
    public void delete(final Event event) {
        System.out.println("Elimina");
    }

    public void initialize() {
        ConnectionProvider c = new ConnectionProviderImpl(Credentials.getUsername(), Credentials.getPassword(),
                Credentials.getDbname());
        this.vhcTbl = new VehicleTable(c.getMySQLConnection());
        Platform.runLater(() -> {
            this.showVehicle = vhcTbl.findAll();
            lstvw_vehicles.setItems(FXCollections.observableList(buildVehicles(showVehicle)));
            this.setDisabledAll(true);
            this.btn_save.setDisable(true);
            this.btn_change.setDisable(true);
            this.btn_delete.setDisable(true);
            txt_license.setText(NOVEHICLE);
            txt_country.setText("");
            txt_brand.setText("");
            txt_type.setText("");

            lstvw_vehicles.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (lstvw_vehicles.getSelectionModel().getSelectedIndex() != -1) {
                        var item = showVehicle.get(lstvw_vehicles.getSelectionModel().getSelectedIndex());
                        setDisabledAll(true);
                        txt_license.setText(item.getTarga());
                        txt_country.setText(item.getStato());
                        txt_brand.setText(item.getMarca());
                        txt_type.setText(item.getTipo_veicolo());
                        btn_change.setDisable(false);
                        btn_delete.setDisable(false);

                    } else {
                        setDisabledAll(true);
                        btn_save.setDisable(true);
                        btn_change.setDisable(true);
                        btn_delete.setDisable(true);
                        txt_license.setText(NOVEHICLE);
                        txt_country.setText("");
                        txt_brand.setText("");
                        txt_type.setText("");
                    }
                }
            });
        });
    }

    private List<String> buildVehicles(List<Vehicle> all) {
        List<String> list = new ArrayList<>();
        for (var elem : all) {
            String s = "" + elem.getTarga() + " " + elem.getMarca();
            list.add(s);
        }
        return list;
    }

    /** The same thing as giving b to all the TextFields and the DatePicker */
    private void setDisabledAll(boolean b) {
        this.txt_license.setDisable(b);
        this.txt_country.setDisable(b);
        this.txt_brand.setDisable(b);
        this.txt_type.setDisable(b);
    }

}
