package db.ecommerce.controller;

import java.io.IOException;

import db.ecommerce.model.CourierPK;
import db.ecommerce.model.DeliveryPK;
import db.ecommerce.model.tables.AddressTable;
import db.ecommerce.model.tables.DeliveryTable;
import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import db.ecommerce.view.DeliveryMenuImpl;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DeliveryDetailsController {

    @FXML
    private Button btn_back;

    @FXML
    private Label lbl_shopping;

    @FXML
    private Label lbl_date;

    @FXML
    private Label lbl_type_delivery;

    @FXML
    private Label lbl_customer;

    @FXML
    private Label lbl_address;

    private CourierPK courier;

    private DeliveryPK delivery;

    private DeliveryTable dlvTbl;

    private AddressTable adsTbl;

    public void setCourier(CourierPK courier) {
        this.courier = courier;
    }

    public void setDelivery(DeliveryPK delivery) {
        this.delivery = delivery;
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

        Platform.runLater(() -> {
            lbl_date.setText(delivery.getDate().isPresent() ? delivery.getDate().get().toString() : "--");
            lbl_shopping.setText(delivery.getCod_spesa().isPresent() ? delivery.getCod_spesa().get().toString() : "--");
            lbl_type_delivery.setText(delivery.getType().name());
            var a = adsTbl.findByPrimaryKey(delivery.getCodIndirizzo());
            if (a.isEmpty()) {
                lbl_address.setText("--");
            } else {
                var A = a.get();
                lbl_address.setText("" + A.getRoad() + " " + A.getnCiv() + " " + A.getCity() + " (" + A.getRegion()
                        + ") " + A.getCity());
            }
            var u = dlvTbl.getClient(delivery);
            if (u.isPresent()) {
                lbl_customer.setText("" + u.get().getName() + " " + u.get().getSurname() + " ("
                        + u.get().getCod_cliente() + ") " + u.get().getEmail() + " " + u.get().getPhoneNumber());
            } else {
                lbl_customer.setText("--");
            }

        });

    }

}
