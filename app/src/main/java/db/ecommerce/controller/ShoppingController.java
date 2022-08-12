package db.ecommerce.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import db.ecommerce.model.AddressPK;
import db.ecommerce.model.ClientPK;
import db.ecommerce.model.DeliveryPK;
import db.ecommerce.model.ShoppingPK;
import db.ecommerce.model.tables.AddressTable;
import db.ecommerce.model.tables.DeliveryTable;
import db.ecommerce.model.tables.ShoppingTable;
import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import db.ecommerce.view.ShoppingPendingMenuImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ShoppingController {

    @FXML
    private Button btn_new_shopping;

    @FXML
    private Button btn_delivery_info;

    @FXML
    private ListView<String> lstvw_shopping_list;

    private ClientPK user;

    private ShoppingTable shpTbl;

    private DeliveryTable dlvTbl;

    private AddressTable addTbl;

    public void setClient(ClientPK user) {
        this.user = user;
    }

    @FXML
    public void newShopping(final Event event) {
        System.out.println("Cambio scena, vado in nuova spesa");
    }

    @FXML
    public void getDetails(final Event event) {
        Stage s = (Stage) btn_delivery_info.getScene().getWindow();
        try {
            new ShoppingPendingMenuImpl(s, this.user);
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
        this.shpTbl = new ShoppingTable(c.getMySQLConnection());
        this.dlvTbl = new DeliveryTable(c.getMySQLConnection());
        this.addTbl = new AddressTable(c.getMySQLConnection());
        Platform.runLater(() -> {
            var allShopping = shpTbl.allShoppingOfClient(user);
            lstvw_shopping_list.setItems(FXCollections.observableList(buildShopping(allShopping)));
            if (dlvTbl.allDeliveryOfClientUnsent(user).isEmpty()) {
                btn_delivery_info.setDisable(true);
                btn_delivery_info.setText("Non hai consegne in attesa");
            }
        });

    }

    private List<String> buildShopping(List<ShoppingPK> l) {
        List<String> list = new ArrayList<>();
        for (final ShoppingPK elem : l) {
            String s = "Prezzo totale €" + elem.getPrice() + " Dati consegna ";
            var d = dlvTbl.deliveryOfShopping(elem);
            if (d.isEmpty()) {
                s = s + "nessuno";
            } else {
                DeliveryPK D = d.get();
                Optional<AddressPK> a = addTbl.findByPrimaryKey(D.getCodIndirizzo());
                s = s + "N° " + D.getCod_Consegna() + " ";
                s = s + "Prezzo consegna €" + D.getPriceDelivery() + " ";
                s = s + "Tipo " + D.getType().name() + " ";
                s = s + "Indirizzo ";
                if (a.isPresent()) {
                    s = s + a.get().getRoad()+" "+a.get().getnCiv()+" "+a.get().getCity()+" ("+a.get().getCity()+") ";
                } else {
                    s=s+"nessuno ";
                }
                s = s + (D.getCodCorriere().isPresent() ? "CONSEGNATA" : "DA CONSEGNARE");
            }
            list.add(s);

        }

        return list;
    }
}
