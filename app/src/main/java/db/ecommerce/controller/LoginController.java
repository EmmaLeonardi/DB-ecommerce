package db.ecommerce.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import db.ecommerce.model.ClientPK;
import db.ecommerce.model.CourierPK;
import db.ecommerce.model.ProducerPK;
import db.ecommerce.model.tables.ClientTable;
import db.ecommerce.model.tables.CourierTable;
import db.ecommerce.model.tables.ProducerTable;
import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import db.ecommerce.utils.ROLE;
import db.ecommerce.view.DeliveryMenuImpl;
import db.ecommerce.view.ShoppingMenuImpl;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

/**
 * This is the controller of the login page
 */
public class LoginController {

    @FXML
    private PasswordField txt_psw;

    @FXML
    private TextField txt_username;

    @FXML
    private ChoiceBox<ROLE> cbx_role;

    @FXML
    private Button btn_login;

    private ClientTable clientTbl;

    private ProducerTable producerTbl;

    private CourierTable courierTbl;

    private final static String ADMIN_CRED = "admin";

    /**
     * This event is called by a button press and returns to the caller if the user
     * can be logged in, after some database calls to authenticate the user
     * 
     * @throws IOException
     * 
     * @returns the role the user can be logged into, returns NESSUNO if the login
     *          failed
     */
    @FXML
    public void login(final Event event) throws IOException {
        if (cbx_role.getValue() != null) {

            switch (cbx_role.getValue()) {
            case ADMIN:
                if (txt_psw.getText().equals(ADMIN_CRED) && txt_username.getText().equals(ADMIN_CRED)) {
                    System.out.println("ROLE.ADMIN");

                } else {
                    var alert = new Alert(AlertType.ERROR, "Wrong credentials, retry");
                    alert.show();
                }
                break;
            case CLIENTE:
                Optional<ClientPK> client = this.clientTbl.findByPrimaryKey(Integer.parseInt(txt_username.getText()));
                if (client.isPresent() && client.get().getName().equals(txt_psw.getText())) {
                    Stage s = (Stage) btn_login.getScene().getWindow();
                    new ShoppingMenuImpl(s, client.get());
                } else {
                    var alert = new Alert(AlertType.ERROR, "Wrong credentials, retry");
                    alert.show();
                }
                break;
            case CORRIERE:
                Optional<CourierPK> courier = this.courierTbl
                        .findByPrimaryKey(Integer.parseInt(txt_username.getText()));
                if (courier.isPresent() && courier.get().getName().equals(txt_psw.getText())) {
                    Stage s = (Stage) btn_login.getScene().getWindow();
                    new DeliveryMenuImpl(s, courier.get());
                } else {
                    var alert = new Alert(AlertType.ERROR, "Wrong credentials, retry");
                    alert.show();
                }
                break;
            case PRODUTTORE:
                Optional<ProducerPK> producer = this.producerTbl
                        .findByPrimaryKey(Integer.parseInt(txt_username.getText()));
                if (producer.isPresent() && producer.get().getName().equals(txt_psw.getText())) {
                    System.out.println("ROLE.PRODUTTORE");
                } else {
                    var alert = new Alert(AlertType.ERROR, "Wrong credentials, retry");
                    alert.show();
                }
                break;
            default:
                var alert = new Alert(AlertType.ERROR, "Please select a role, retry");
                alert.show();
                break;

            }
        } else {
            var alert = new Alert(AlertType.ERROR, "Please select a role, retry");
            alert.show();
        }

    }

    /**
     * This method is called at the start and inizializes the choice box
     */
    public void initialize() {
        cbx_role.setItems(FXCollections
                .observableArrayList(List.of(ROLE.NESSUNO, ROLE.CLIENTE, ROLE.CORRIERE, ROLE.PRODUTTORE, ROLE.ADMIN)));
        cbx_role.getSelectionModel().selectFirst();

        ConnectionProvider c = new ConnectionProviderImpl(Credentials.getUsername(), Credentials.getPassword(),
                Credentials.getDbname());
        this.clientTbl = new ClientTable(c.getMySQLConnection());
        this.courierTbl = new CourierTable(c.getMySQLConnection());
        this.producerTbl = new ProducerTable(c.getMySQLConnection());
    }

}
