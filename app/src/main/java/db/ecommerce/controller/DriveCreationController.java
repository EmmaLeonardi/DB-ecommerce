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
        txt_end_h.setDisable(true);
        txt_end_m.setDisable(true);
        dtpk_end.setDisable(true);

    }

}
