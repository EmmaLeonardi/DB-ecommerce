package db.ecommerce.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import db.ecommerce.model.Address;
import db.ecommerce.model.AddressPK;
import db.ecommerce.model.ClientPK;
import db.ecommerce.model.ContainsImpl;
import db.ecommerce.model.Delivery;
import db.ecommerce.model.DeliveryImpl;
import db.ecommerce.model.DeliveryPK;
import db.ecommerce.model.Shopping;
import db.ecommerce.model.ShoppingImpl;
import db.ecommerce.model.ShoppingPK;
import db.ecommerce.model.SoldProductPK;
import db.ecommerce.model.tables.AddressTable;
import db.ecommerce.model.tables.ContainsTable;
import db.ecommerce.model.tables.DeliveryTable;
import db.ecommerce.model.tables.ShoppingTable;
import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import db.ecommerce.utils.TYPEDELIVERY;
import db.ecommerce.view.AddressCreationMenuImpl;
import db.ecommerce.view.ShoppingMenuImpl;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddressSelectionController {

    @FXML
    private Label lbl_chosen_address;
    @FXML
    private Button btn_new_address;
    @FXML
    private Label lbl_cc;
    @FXML
    private ComboBox<String> cmb_delivery_type;
    @FXML
    private Label lbl_price;
    @FXML
    private Label lbl_total_price;
    @FXML
    private ListView<String> lstvw_address;
    @FXML
    private Button btn_end;

    private ClientPK user;
    private List<SoldProductPK> list;
    private AddressTable adsTbl;
    private double finPrice;

    private List<AddressPK> oldAddresses;
    private List<Address> newAddresses = new ArrayList<>();
    private List<Address> shownAddress;
    private DeliveryTable dlvTbl;
    private ShoppingTable shpTbl;
    private ContainsTable cntTbl;

    public void setClient(ClientPK c) {
        this.user = c;

    }

    public void setShopping(List<SoldProductPK> l) {
        this.list = new ArrayList<>(l);
    }

    public void addAddress(List<Address> listAddr) {
        this.newAddresses = new ArrayList<Address>(listAddr);

    }

    @FXML
    public void new_address(final Event event) {
        Stage s = (Stage) btn_new_address.getScene().getWindow();
        try {
            new AddressCreationMenuImpl(s, user, Collections.unmodifiableList(list));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void end(final Event event) throws IOException, SQLException {

        int index = lstvw_address.getSelectionModel().getSelectedIndex();
        if (index != -1 && !shownAddress.isEmpty()) {
            AddressPK a = null;
            // C'è almeno un indirizzo
            if (oldAddresses.size() == shownAddress.size()) {
                // E' un elemento vecchio
                a = oldAddresses.get(index);
            } else if (newAddresses.size() == shownAddress.size()) {
                // E un indirizzo nuovo
                int newIndex = index - oldAddresses.size();
                var tmp = newAddresses.get(newIndex);
                var app = adsTbl.save(tmp);
                if (app.isPresent()) {
                    a = app.get();
                } else {
                    throw new SQLException("Address wasn't saved");
                }
            } else {
                // C'è mix
                if (index >= 0 && index <= oldAddresses.size() - 1) {
                    a = oldAddresses.get(index);
                } else if (index >= oldAddresses.size() && index <= shownAddress.size() - 1) {
                    // E' indirizzo nuovo, faccio traslazione
                    int newIndex = index - oldAddresses.size();
                    var tmp = newAddresses.get(newIndex);
                    var app = adsTbl.save(tmp);
                    if (app.isPresent()) {
                        a = app.get();
                    } else {
                        throw new SQLException("Address wasn't saved");
                    }
                }
            }
            if (a == null) {
                throw new IllegalStateException("No address was saved");
            } else {
                // Creo spesa
                ShoppingPK S;
                Shopping s = new ShoppingImpl(user.getCod_cliente(), finPrice);
                var app = shpTbl.save(s);
                if (app.isPresent()) {
                    S = app.get();
                } else {
                    throw new SQLException("Shopping wasn't saved");
                }

                // Creo contenuto
                for (SoldProductPK p : list) {
                    var c = new ContainsImpl(p.getCodProduct(), S.getCodSpesa());
                    var z = cntTbl.save(c);
                    if (z.isEmpty()) {
                        throw new SQLException("Contains wasn't saved");
                    }
                }

                TYPEDELIVERY t = TYPEDELIVERY.convert(cmb_delivery_type.getSelectionModel().getSelectedItem());
                // Creo consegna
                DeliveryPK D;
                Delivery d = new DeliveryImpl(Optional.of(S.getCodSpesa()), t.getPrice(), Optional.empty(), t,
                        a.getCodAddress(), Optional.empty(), Optional.empty());
                var tmp = dlvTbl.saveClient(d);
                if (tmp.isPresent()) {
                    D = tmp.get();
                    var alert = new Alert(AlertType.INFORMATION,
                            "Hai compleato correttamente il pagamento! Il codice della tua consegna è "
                                    + D.getCod_Consegna());
                    alert.show();

                    Stage stage = (Stage) btn_end.getScene().getWindow();
                    try {
                        new ShoppingMenuImpl(stage, user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    throw new SQLException("Delivery wasn't saved");
                }
            }

        } else {
            var alert = new Alert(AlertType.ERROR, "Seleziona un indirizzo o creane uno nuovo");
            alert.show();
        }

    }

    /**
     * This method is called at the start and inizializes the list view
     */
    public void initialize() {
        ConnectionProvider c = new ConnectionProviderImpl(Credentials.getUsername(), Credentials.getPassword(),
                Credentials.getDbname());
        this.adsTbl = new AddressTable(c.getMySQLConnection());
        this.dlvTbl = new DeliveryTable(c.getMySQLConnection());
        this.shpTbl = new ShoppingTable(c.getMySQLConnection());
        this.cntTbl = new ContainsTable(c.getMySQLConnection());
        Platform.runLater(() -> {
            // ListView
            this.oldAddresses = adsTbl.allAddressOfClient(user);
            this.shownAddress = new ArrayList<Address>(oldAddresses);
            if (!newAddresses.isEmpty()) {
                this.shownAddress.addAll(newAddresses);
            }
            lstvw_address.setItems(FXCollections.observableList(buildAddress(shownAddress)));
            lstvw_address.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    lbl_chosen_address.setText(newValue);
                }
            });
            // Labels
            double price = list.stream().map(p -> p.getPrice()).reduce(Double::sum).orElse(0.0);
            lbl_price.setText(price + "€");
            lbl_cc.setText(user.getBankInfo());
            lbl_total_price.setText(price + TYPEDELIVERY.STANDARD.getPrice() + "€");
            finPrice = price + TYPEDELIVERY.STANDARD.getPrice();
            // ComboBox
            cmb_delivery_type.setItems(
                    FXCollections.observableList(List.of(TYPEDELIVERY.STANDARD.name(), TYPEDELIVERY.PREMIUM.name())));
            cmb_delivery_type.getSelectionModel().selectFirst();
            cmb_delivery_type.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
                if (newValue == TYPEDELIVERY.STANDARD.name()) {
                    finPrice = price + TYPEDELIVERY.STANDARD.getPrice();
                    lbl_total_price.setText(finPrice + "€");
                } else {
                    finPrice = price + TYPEDELIVERY.PREMIUM.getPrice();

                }
                lbl_total_price.setText(finPrice + "€");
            });
        });
    }

    // Rimuovi i duplicati
    private List<String> buildAddress(List<Address> l) {
        List<String> list = new ArrayList<>();
        for (final Address elem : l) {
            list.add(addressString(elem));
        }
        return list;
    }

    private String addressString(Address elem) {
        String s = elem.getRoad() + " " + elem.getnCiv() + " ";
        s = s + elem.getCity() + " (" + elem.getRegion() + ") " + elem.getCity();
        return s;
    }

}
