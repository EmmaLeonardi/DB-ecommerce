package db.ecommerce.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import db.ecommerce.model.CourierPK;
import db.ecommerce.model.Drive;
import db.ecommerce.model.DriveImpl;
import db.ecommerce.model.DrivePK;
import db.ecommerce.model.Vehicle;
import db.ecommerce.model.tables.DriveTable;
import db.ecommerce.model.tables.VehicleTable;
import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import db.ecommerce.view.DeliveryNewMenuImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import db.ecommerce.utils.ConvertTime;

public class DriveCreationController {

    @FXML
    private DatePicker dtpk_start;

    @FXML
    private DatePicker dtpk_end;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_save;

    @FXML
    private Button btn_find_vehicle;

    @FXML
    private CheckBox chb_end_guide;

    @FXML
    private ListView<String> lstv_vehicle;

    @FXML
    private TextField txt_start_h;

    @FXML
    private TextField txt_start_m;

    @FXML
    private TextField txt_end_h;

    @FXML
    private TextField txt_end_m;

    private CourierPK courier;

    private DriveTable drvTbl;

    private VehicleTable vhcTbl;

    private Date min;

    private Date max;

    private List<Vehicle> showVehicle;

    public void setCourier(CourierPK courier) {
        this.courier = courier;
    }

    @FXML
    public void change_end(final Event event) {
        if (chb_end_guide.isSelected()) {
            txt_end_h.setDisable(false);
            txt_end_m.setDisable(false);
            dtpk_end.setDisable(false);
        } else {
            txt_end_h.setDisable(true);
            txt_end_h.setText(null);
            txt_end_m.setDisable(true);
            txt_end_m.setText(null);
            dtpk_end.setDisable(true);
            dtpk_end.setValue(null);
        }
    }

    @FXML
    public void save(final Event event) throws SQLException {
        if (min != null && lstv_vehicle.getSelectionModel().getSelectedIndex() != -1) {
            Drive d = new DriveImpl(min, Optional.ofNullable(max), courier.getCod_corriere(),
                    showVehicle.get(lstv_vehicle.getSelectionModel().getSelectedIndex()).getTarga());
            var saved = drvTbl.save(d);
            if (saved.isEmpty()) {
                throw new SQLException("Guide wasn't saved");
            } else {
                var alert = new Alert(AlertType.INFORMATION,
                        "Hai salvato la guida con codice " + saved.get().getCodDrive());
                alert.show();
                Stage s = (Stage) btn_back.getScene().getWindow();
                try {
                    new DeliveryNewMenuImpl(s, courier);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @FXML
    public void find_vehicle(final Event event) {
        if (chb_end_guide.isSelected()) {
            // Hai un valore finale
            if (dtpk_end.getValue() != null && dtpk_start != null && txt_end_h.getText() != null
                    && txt_end_m.getText() != null && txt_start_h.getText() != null && txt_start_m.getText() != null) {
                this.min = ConvertTime.convertIntoTime(
                        Date.from(dtpk_start.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        Double.parseDouble(txt_start_h.getText()) + 0.01 * Double.parseDouble(txt_start_m.getText()));
                this.max = ConvertTime.convertIntoTime(
                        Date.from(dtpk_end.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        Double.parseDouble(txt_end_h.getText()) + 0.01 * Double.parseDouble(txt_end_m.getText()));
                if (max.after(min) == false) {
                    var alert = new Alert(AlertType.ERROR, "Inserisci le date nell'ordine corretto");
                    alert.show();
                } else {
                    this.showVehicle = this.allVeihicleFreeInTime(min, max);
                    lstv_vehicle.setItems(FXCollections.observableList(buildVehicle(showVehicle)));
                }
            } else {
                var alert = new Alert(AlertType.ERROR,
                        "Inserisci valori per date di inizio e fine, orario di inizio e fine");
                alert.show();
            }

        } else {
            // Hai solo un valore iniziale
            if (dtpk_start != null && txt_start_h.getText() != null && txt_start_m.getText() != null) {
                this.min = ConvertTime.convertIntoTime(
                        Date.from(dtpk_start.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        Double.parseDouble(txt_start_h.getText()) + 0.01 * Double.parseDouble(txt_start_m.getText()));
                this.max = null;
                this.showVehicle = this.allVeihicleFreeInTime(min);
                lstv_vehicle.setItems(FXCollections.observableList(buildVehicle(showVehicle)));
            } else {
                var alert = new Alert(AlertType.ERROR, "Inserisci valori per data e orario di inizio");
                alert.show();
            }
        }
    }

    @FXML
    public void back(final Event event) {
        Stage s = (Stage) btn_back.getScene().getWindow();
        try {
            new DeliveryNewMenuImpl(s, courier);
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
        drvTbl = new DriveTable(c.getMySQLConnection());
        vhcTbl = new VehicleTable(c.getMySQLConnection());
        txt_end_h.setDisable(true);
        txt_end_m.setDisable(true);
        dtpk_end.setDisable(true);
        Platform.runLater(() -> {
            lstv_vehicle.setItems(FXCollections.observableList(List.of()));
        });

    }

    /**
     * @param the date min, the date max. Returns a list of all vehicles free
     *            between min and max
     */
    private List<Vehicle> allVeihicleFreeInTime(Date min, Date max) {
        List<Vehicle> all = vhcTbl.findAll();
        List<Vehicle> returnedList = new ArrayList<>();
        for (var elem : all) {
            List<DrivePK> allDrive = drvTbl.findDrivesOfVehicle(elem);
            boolean driveLegal = true;
            int i = 0;
            while (driveLegal == true && i < allDrive.size()) {
                var drive = allDrive.get(i);
                if (drive.getEnd().isPresent() && drive.getEnd().get().before(min) || drive.getStart().after(max)) {
                    driveLegal = true;
                    i++;
                } else {
                    driveLegal = false;
                }
            }
            if (driveLegal == true) {
                returnedList.add(elem);
            }
        }
        return returnedList;
    }

    /**
     * @param the date min. Returns a list of all vehicles free after min
     */
    private List<Vehicle> allVeihicleFreeInTime(Date min) {
        List<Vehicle> all = vhcTbl.findAll();
        List<Vehicle> returnedList = new ArrayList<>();
        for (var elem : all) {
            List<DrivePK> allDrive = drvTbl.findDrivesOfVehicle(elem);
            boolean driveLegal = true;
            int i = 0;
            while (driveLegal == true && i < allDrive.size()) {
                var drive = allDrive.get(i);
                if (drive.getEnd().isPresent() && drive.getEnd().get().before(min)) {
                    driveLegal = true;
                    i++;
                } else {
                    driveLegal = false;
                }
            }
            if (driveLegal == true) {
                returnedList.add(elem);
            }
        }
        return returnedList;
    }

    private List<String> buildVehicle(List<Vehicle> l) {
        List<String> list = new ArrayList<>();
        for (Vehicle elem : l) {
            String s = "" + elem.getTipo_veicolo() + " " + elem.getMarca() + " " + elem.getTarga()
                    + " Immatricolato in:" + elem.getStato();
            list.add(s);
        }
        return list;
    }
}
