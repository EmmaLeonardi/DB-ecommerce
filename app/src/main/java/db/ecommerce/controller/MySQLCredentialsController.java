package db.ecommerce.controller;

import java.io.IOException;
import java.sql.SQLException;

import db.ecommerce.DatabaseCreation;
import db.ecommerce.utils.Credentials;
import db.ecommerce.view.LoginMenuImpl;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class MySQLCredentialsController {

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_username;

    @FXML
    private Button btn_login;

    public final static String CREATIONFILE = "/Ecommerce.ddl";

    public final static String INSERTIONFILE = "/Population-queries.sql";

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
        if (txt_password.getText() != null && txt_username.getText() != null) {
            try {
                var db = new DatabaseCreation(txt_username.getText(), txt_password.getText(), CREATIONFILE,
                        INSERTIONFILE);
                if (db.wasCreated() == true) {
                    var alert = new Alert(AlertType.INFORMATION,
                            "Il database ecommerce è stato creato e riempito con i dati standard");
                    alert.show();
                } else {
                    var alert = new Alert(AlertType.INFORMATION, "Il database ecommerce esisteva già");
                    alert.show();
                }
                new Credentials(txt_username.getText(), txt_password.getText());
                Stage s = (Stage) btn_login.getScene().getWindow();
                try {
                    new LoginMenuImpl(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                var alert = new Alert(AlertType.ERROR, "Ci sono stati problemi con la generazione del database");
                alert.show();
            }

        } else {
            var alert = new Alert(AlertType.ERROR, "Inserisci username e password");
            alert.show();
        }

    }

}
