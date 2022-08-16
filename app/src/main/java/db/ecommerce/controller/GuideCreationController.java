package db.ecommerce.controller;

import java.io.IOException;

import db.ecommerce.model.CourierPK;
import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import db.ecommerce.view.DeliveryNewMenuImpl;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GuideCreationController {

    @FXML
    private DatePicker dtpk_start;

    @FXML
    private DatePicker dtpk_end;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_save;

    @FXML
    private CheckBox chb_end_guide;

    @FXML
    private TextField txt_start_h;

    @FXML
    private TextField txt_start_m;

    @FXML
    private TextField txt_end_h;

    @FXML
    private TextField txt_end_m;

    private CourierPK courier;

    public void setCourier(CourierPK courier) {
        this.courier = courier;
    }

    @FXML
    public void change_end(final Event event) {
    }

    @FXML
    public void save(final Event event) {
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
    }

}
