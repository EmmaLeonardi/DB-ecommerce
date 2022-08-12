package db.ecommerce.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import db.ecommerce.model.AddressPK;
import db.ecommerce.model.ClientPK;
import db.ecommerce.model.DeliveryPK;
import db.ecommerce.model.tables.AddressTable;
import db.ecommerce.model.tables.DeliveryTable;
import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import db.ecommerce.view.ShoppingMenuImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ShoppingPendingController {

    @FXML
    private Button btn_back;

    @FXML
    private ListView<String> lstvw_shopping_pending;

    private ClientPK user;

    private DeliveryTable dlvTbl;

    private AddressTable addTbl;

    public void setClient(ClientPK user) {
        this.user = user;
    }

    @FXML
    public void back(final Event event) {
        Stage s = (Stage) btn_back.getScene().getWindow();
        try {
            new ShoppingMenuImpl(s, user);
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
        this.dlvTbl = new DeliveryTable(c.getMySQLConnection());
        this.addTbl = new AddressTable(c.getMySQLConnection());
        Platform.runLater(() -> {
            var allDelivery = dlvTbl.allDeliveryOfClientUnsent(user);
            if (allDelivery.isEmpty()) {
                var alert = new Alert(AlertType.ERROR, "Something went wrong loading the unsent Deliveries");
                alert.show();
            } else {
                lstvw_shopping_pending.setItems(FXCollections.observableList(buildShopping(allDelivery)));

            }
        });
    }

    private List<String> buildShopping(List<DeliveryPK> l) {
        List<String> list = new ArrayList<>();
        for (final DeliveryPK elem : l) {
            String s = "Consegna N° " + elem.getCod_Consegna() + " ";
            s = s + "Prezzo consegna €" + elem.getPriceDelivery() + " ";
            s = s + "Tipo " + elem.getType().name() + " ";
            s = s + "Indirizzo ";
            Optional<AddressPK> a = addTbl.findByPrimaryKey(elem.getCodIndirizzo());
            if (a.isPresent()) {
                s = s + a.get().getRoad()+" "+a.get().getnCiv()+" "+a.get().getCity()+" ("+a.get().getCity()+") ";
            } else {
                s=s+"nessuno ";
            }
            list.add(s);

        }

        return list;
    }

}
