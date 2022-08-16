package db.ecommerce.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import db.ecommerce.model.CourierPK;
import db.ecommerce.model.DeliveryPK;
import db.ecommerce.model.tables.DeliveryTable;
import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import db.ecommerce.view.DeliveryDetailsMenuImpl;
import db.ecommerce.view.DeliveryNewMenuImpl;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class DeliveryController {

    @FXML
    private Button btn_details;

    @FXML
    private Button btn_new_delivery;

    @FXML
    private ListView<String> lstvw_delivery_pending;

    private CourierPK courier;

    private DeliveryTable dlvTbl;

    private List<DeliveryPK> allDelivery;

    public void setCourier(CourierPK courierPK) {
        this.courier = courierPK;
    }

    @FXML
    public void details(final Event event) {
        Stage s = (Stage) btn_details.getScene().getWindow();
        try {
            new DeliveryDetailsMenuImpl(s, courier,
                    allDelivery.get(lstvw_delivery_pending.getSelectionModel().getSelectedIndex()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void newDelivery(final Event event) {
        Stage s = (Stage) btn_details.getScene().getWindow();
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
        dlvTbl = new DeliveryTable(c.getMySQLConnection());
        btn_details.setDisable(true);

        Platform.runLater(() -> {
            allDelivery = dlvTbl.allDeliveryOfCourier(courier);

            lstvw_delivery_pending.setItems(FXCollections.observableList(buildDelivery(allDelivery)));
            lstvw_delivery_pending.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (lstvw_delivery_pending.getSelectionModel().getSelectedIndex() == -1) {
                        btn_details.setDisable(true);
                    } else {
                        btn_details.setDisable(false);
                    }
                }
            });
        });

    }

    private List<String> buildDelivery(List<DeliveryPK> list) {
        final List<String> l = new ArrayList<>();
        for (DeliveryPK elem : list) {
            String s = "Data " + (elem.getDate().isEmpty() ? "--" : elem.getDate().get().toString()) + " ";
            s = s + "Targa " + elem.getTarga().orElse("--") + " ";
            s = s + "Codice consegna " + elem.getCod_Consegna() + " ";
            s = s + "Tipo consegna " + elem.getType().name();
            l.add(s);
        }

        return l;
    }

}
