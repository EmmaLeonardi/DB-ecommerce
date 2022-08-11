package db.ecommerce.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import db.ecommerce.model.ClientPK;
import db.ecommerce.model.ShoppingPK;
import db.ecommerce.model.tables.ClientTable;
import db.ecommerce.model.tables.ShoppingTable;
import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;

public class ShoppingController {

    @FXML
    private Button btn_new_shopping;

    @FXML
    private Button btn_delivery_info;

    @FXML
    private ListView<String> lstvw_shopping_list;

    private ClientPK user;
    private int cod_user;

    public void setClient(int cod_user) {
        this.cod_user = cod_user;
    }

    @FXML
    public void newShopping(final Event event) {
        System.out.println("Cambio scena, vado in nuova spesa");
    }

    @FXML
    public void getDetails(final Event event) {
        System.out.println("Cambio scena, vado in spese da consegnare");
    }

    /**
     * This method is called at the start and inizializes the list view
     */
    public void initialize() {
        ConnectionProvider c = new ConnectionProviderImpl(Credentials.getUsername(), Credentials.getPassword(),
                Credentials.getDbname());
        ShoppingTable shpTbl = new ShoppingTable(c.getMySQLConnection());
        ClientTable cltTbl = new ClientTable(c.getMySQLConnection());
        Platform.runLater(() -> {
            var tmp = cltTbl.findByPrimaryKey(cod_user);
            if (tmp.isEmpty()) {
                var alert = new Alert(AlertType.ERROR, "Something went wrong loading the User");
                alert.show();
                try {
                    throw new IOException("The user wasn't present in the database");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                this.user = tmp.get();
                var allShopping = shpTbl.allShoppingOfClient(user);
                lstvw_shopping_list.setItems(FXCollections.observableList(buildShopping(allShopping)));

            }
        });
    }

    private List<String> buildShopping(List<ShoppingPK> l) {
        List<String> list = new ArrayList<>();
        for (final ShoppingPK elem : l) {
            list.add(elem.getPrice() + "Delivery:");
            // + dettagli consegna (via query)

        }

        return list;
    }
}
