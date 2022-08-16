package db.ecommerce.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import db.ecommerce.model.CourierPK;
import db.ecommerce.model.DeliveryPK;
import db.ecommerce.model.DrivePK;
import db.ecommerce.model.tables.AddressTable;
import db.ecommerce.model.tables.DeliveryTable;
import db.ecommerce.model.tables.DriveTable;
import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import db.ecommerce.view.DeliveryMenuImpl;
import db.ecommerce.view.DriveCreationMenuImpl;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class DeliveryNewController {

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_new_guide;

    @FXML
    private Button btn_save;

    @FXML
    private ListView<String> lstvw_delivery;

    @FXML
    private ListView<String> lstvw_guides;

    @FXML
    private Label lbl_type_delivery;

    @FXML
    private Label lbl_shopping;

    @FXML
    private Label lbl_address;

    @FXML
    private Label lbl_customer;

    @FXML
    private Label lbl_contacts;

    @FXML
    private DatePicker dtpk_delivery_date;

    private CourierPK courier;

    private DeliveryTable dlvTbl;

    private List<DeliveryPK> unDelivered;

    private AddressTable adsTbl;

    private DriveTable drvTbl;

    private List<DrivePK> showDrives;

    public void setCourier(CourierPK courier) {
        this.courier = courier;
    }

    @FXML
    public void save(final Event event) throws SQLException {
        if (lstvw_delivery.getSelectionModel().getSelectedIndex() != -1
                && lstvw_guides.getSelectionModel().getSelectedIndex() != -1 && dtpk_delivery_date.getValue() != null) {
            DrivePK selectedDrive = showDrives.get(lstvw_guides.getSelectionModel().getSelectedIndex());
            DeliveryPK selectedDelivery = unDelivered.get(lstvw_delivery.getSelectionModel().getSelectedIndex());

            DeliveryPK d = new DeliveryPK(selectedDelivery.getCod_spesa(), selectedDelivery.getPriceDelivery(),
                    Optional.of(
                            Date.from(dtpk_delivery_date.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())),
                    selectedDelivery.getType(), selectedDelivery.getCodIndirizzo(),
                    Optional.of(courier.getCod_corriere()), Optional.of(selectedDrive.getTarga()),
                    selectedDelivery.getCod_Consegna());
            if (dlvTbl.update(d) == true) {
                var alert = new Alert(AlertType.INFORMATION,
                        "Hai inserito correttamente la consegna! Ha codice " + selectedDelivery.getCod_Consegna());
                alert.show();
                Stage s = (Stage) btn_save.getScene().getWindow();
                try {
                    new DeliveryMenuImpl(s, courier);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                throw new SQLException("Delivery wasn't updated");
            }
        }
    }

    @FXML
    public void new_guide(final Event event) {
        Stage s = (Stage) btn_new_guide.getScene().getWindow();
        try {
            new DriveCreationMenuImpl(s, courier);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void back(final Event event) {
        Stage s = (Stage) btn_back.getScene().getWindow();
        try {
            new DeliveryMenuImpl(s, courier);
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
        dlvTbl = new DeliveryTable(c.getMySQLConnection());
        adsTbl = new AddressTable(c.getMySQLConnection());
        drvTbl = new DriveTable(c.getMySQLConnection());
        btn_save.setDisable(true);
        // Lo accendo quando ho tutto selezionato

        Platform.runLater(() -> {
            // List view
            this.unDelivered = dlvTbl.allDeliveriesPending();
            lstvw_delivery.setItems(FXCollections.observableList(buildDelivery(unDelivered)));
            lstvw_delivery.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (lstvw_delivery.getSelectionModel().getSelectedIndex() != -1) {
                        if (lstvw_guides.getSelectionModel().getSelectedIndex() != -1) {
                            btn_save.setDisable(false);
                        }
                        var selected = unDelivered.get(lstvw_delivery.getSelectionModel().getSelectedIndex());
                        lbl_address.setText(selected.getCodIndirizzo() + "");
                        var cli = dlvTbl.getClient(selected);
                        if (cli.isPresent()) {
                            lbl_customer.setText(cli.get().getName() + " " + cli.get().getSurname());
                            lbl_contacts.setText(cli.get().getPhoneNumber() + " " + cli.get().getEmail());
                        } else {
                            lbl_customer.setText("--");
                            lbl_contacts.setText("--");
                        }
                        lbl_shopping.setText(selected.getCod_spesa().get() + "");
                        lbl_type_delivery.setText(selected.getType().name());
                    } else {
                        btn_save.setDisable(true);
                    }

                }
            });

            this.showDrives = drvTbl.allDriveOfCourier(courier);
            lstvw_guides.setItems(FXCollections.observableList(buildDrives(showDrives)));
            lstvw_guides.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (lstvw_guides.getSelectionModel().getSelectedIndex() != -1) {
                        if (lstvw_delivery.getSelectionModel().getSelectedIndex() != -1) {
                            btn_save.setDisable(false);
                        } else {
                            btn_save.setDisable(true);
                        }
                        dtpk_delivery_date.setDisable(false);
                    } else {
                        dtpk_delivery_date.setDisable(true);
                    }
                }
            });

            // Datepicker
            dtpk_delivery_date.setDisable(true);
            dtpk_delivery_date.valueProperty().addListener((ov, oldValue, newValue) -> {
                DrivePK selected = showDrives.get(lstvw_guides.getSelectionModel().getSelectedIndex());
                LocalDate min = selected.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate max = selected.getEnd().isEmpty() ? null
                        : selected.getEnd().get().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                if (max != null) {
                    if (newValue.isBefore(min) || newValue.isAfter(max)) {
                        dtpk_delivery_date.setValue(min);
                        var alert = new Alert(AlertType.INFORMATION,
                                "Scegli una data corretta. Deve essere tra " + min + " e " + max);
                        alert.show();
                    }

                } else {
                    if (newValue.isBefore(min)) {
                        dtpk_delivery_date.setValue(min);
                        var alert = new Alert(AlertType.INFORMATION,
                                "Scegli una data corretta. Deve essere dopo " + min);
                        alert.show();

                    }
                }
            });
        });

    }

    private List<String> buildDelivery(List<DeliveryPK> list) {
        final List<String> l = new ArrayList<>();
        for (DeliveryPK elem : list) {
            var i = adsTbl.findByPrimaryKey(elem.getCodIndirizzo());
            String s = "Spesa N° " + (elem.getCod_spesa().isPresent() ? elem.getCod_spesa().get().toString() : "--")
                    + " ";
            s = s + "Tipo consegna " + elem.getType().name() + " ";
            if (i.isPresent()) {
                s = s + "Indirizzo " + i.get().getRoad() + " " + i.get().getnCiv() + " " + i.get().getCity() + " ("
                        + i.get().getRegion() + ") " + i.get().getCity();
            }
            l.add(s);
        }

        return l;
    }

    private List<String> buildDrives(List<DrivePK> list) {
        final List<String> l = new ArrayList<>();
        for (DrivePK elem : list) {
            String s = "Guida N° " + elem.getCodDrive() + " ";
            s = s + "Inizio " + elem.getStart() + " ";
            if (elem.getEnd().isPresent()) {
                s = s + "Fine " + elem.getEnd().get() + " ";
            }
            s = s + "Targa veicolo " + elem.getTarga();
            l.add(s);
        }

        return l;
    }

}
