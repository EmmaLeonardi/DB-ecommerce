package db.ecommerce.controller;

import java.io.IOException;

import db.ecommerce.model.CourierPK;
import db.ecommerce.model.tables.DeliveryTable;
import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import db.ecommerce.view.DeliveryDetailsMenuImpl;
import db.ecommerce.view.DeliveryMenuImpl;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class DeliveryNewController {

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_new_guide;

    @FXML
    private Button btn_save;

    @FXML
    private ListView<String> lstvw_shopping;

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
    private DatePicker dtpk_delivery_date;

    private CourierPK courier;

    public void setCourier(CourierPK courier) {
        this.courier = courier;
    }

    @FXML
    public void save(final Event event) {
        //Query
        Stage s = (Stage) btn_save.getScene().getWindow();
        try {
            new DeliveryMenuImpl(s, courier);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void new_guide(final Event event) {
        System.out.println("Nuova guida");
        // Stage s = (Stage) btn_new_guide.getScene().getWindow();
        /*
         * try { //new DeliveryMenuImpl(s, courier); } catch (IOException e) {
         * e.printStackTrace(); }
         */
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
        /*
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
        });*/

    }

}
