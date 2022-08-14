package db.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import db.ecommerce.model.AddressPK;
import db.ecommerce.model.ClientPK;
import db.ecommerce.model.SoldProductPK;
import db.ecommerce.model.tables.AddressTable;
import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import db.ecommerce.utils.TYPEDELIVERY;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

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

    private List<AddressPK> allAddresses;

    public void setClient(ClientPK c) {
        this.user = c;

    }

    public void setShopping(List<SoldProductPK> l) {
        this.list = new ArrayList<>(l);
    }

    @FXML
    public void new_address(final Event event) {
        System.out.println("Nuovo indirizzo");
    }

    @FXML
    public void end(final Event event) {
        System.out.println("Fine+popup per dire numero spesa");
        //Faccio tutte query
        //Torniamo alla schermata iniziale
    }

    /**
     * This method is called at the start and inizializes the list view
     */
    public void initialize() {
        ConnectionProvider c = new ConnectionProviderImpl(Credentials.getUsername(), Credentials.getPassword(),
                Credentials.getDbname());
        this.adsTbl = new AddressTable(c.getMySQLConnection());
        Platform.runLater(() -> {
            this.allAddresses = adsTbl.allAddressOfClient(user);
            lstvw_address.setItems(FXCollections.observableList(buildAddress(allAddresses)));
            cmb_delivery_type.setItems(
                    FXCollections.observableList(List.of(TYPEDELIVERY.STANDARD.name(), TYPEDELIVERY.PREMIUM.name())));
            cmb_delivery_type.getSelectionModel().selectFirst();
            double price = list.stream().map(p -> p.getPrice()).reduce(Double::sum).orElse(0.0);
            lbl_price.setText(price + "€");
            lbl_cc.setText(user.getBankInfo());
            lbl_total_price.setText(price + TYPEDELIVERY.STANDARD.getPrice() + "€");

            cmb_delivery_type.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
                if (newValue == TYPEDELIVERY.STANDARD.name()) {
                    lbl_total_price.setText((price + TYPEDELIVERY.STANDARD.getPrice()) + "€");
                } else {
                    lbl_total_price.setText((price + TYPEDELIVERY.PREMIUM.getPrice()) + "€");
                }
            });

            lstvw_address.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    lbl_chosen_address.setText(newValue);

                }

            });
        });

    }

    private List<String> buildAddress(List<AddressPK> l) {
        List<String> list = new ArrayList<>();
        for (final AddressPK elem : l) {
            String s = elem.getRoad() + " " + elem.getnCiv() + " ";
            s = s + elem.getCity() + " (" + elem.getRegion() + ") " + elem.getCity();
            list.add(s);
        }
        return list;
    }

}
