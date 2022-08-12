package db.ecommerce.controller;

import java.io.IOException;

import db.ecommerce.model.ClientPK;
import db.ecommerce.model.tables.AddressTable;
import db.ecommerce.model.tables.DeliveryTable;
import db.ecommerce.model.tables.ShoppingTable;
import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import db.ecommerce.view.ShoppingPendingMenuImpl;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitMenuButton;
import javafx.stage.Stage;

public class ProductsController {

    @FXML
    private ListView<String> lstvw_shopping_cart;

    @FXML
    private ListView<String> lstvw_products;

    @FXML
    private Button btn_back;

    @FXML
    private Button btn_size;

    @FXML
    private Button btn_expiry;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_remove;

    @FXML
    private Button btn_pay;

    @FXML
    private Button btn_details;

    @FXML
    private Label lbl_total;

    @FXML
    private SplitMenuButton mnbtn_size;

    @FXML
    private DatePicker dtpkr_expiry;

    private ClientPK user;

    public void setClient(ClientPK user) {
        this.user = user;
    }

    // TODO on action della selezione (e spegni bottoni consistently)

    // TODO on action

    @FXML
    public void back(final Event event) {
        System.out.println("Indietro");
    }

    @FXML
    public void size_filter(final Event event) {
        System.out.println("Filtro per dimensioni");
    }

    @FXML
    public void expiry_filter(final Event event) {
        System.out.println("Filtro per scadenza");
    }

    @FXML
    public void add(final Event event) {
        System.out.println("Aggiungo");
    }

    @FXML
    public void remove(final Event event) {
        System.out.println("Tolgo");
    }

    @FXML
    public void end_pay(final Event event) {
        System.out.println("Prossima schermata");
    }

    @FXML
    public void details(final Event event) {
        System.out.println("Dettagli su questo prodotto");
    }

    // TODO riempire listview

    /**
     * This method is called at the start and inizializes the list view
     */
    public void initialize() {
        ConnectionProvider c = new ConnectionProviderImpl(Credentials.getUsername(), Credentials.getPassword(),
                Credentials.getDbname());

        /*
         * this.shpTbl = new ShoppingTable(c.getMySQLConnection()); this.dlvTbl = new
         * DeliveryTable(c.getMySQLConnection()); this.addTbl = new
         * AddressTable(c.getMySQLConnection()); Platform.runLater(() -> { var
         * allShopping = shpTbl.allShoppingOfClient(user);
         * lstvw_shopping_list.setItems(FXCollections.observableList(buildShopping(
         * allShopping))); if (dlvTbl.allDeliveryOfClientUnsent(user).isEmpty()) {
         * btn_delivery_info.setDisable(true);
         * btn_delivery_info.setText("Non hai consegne in attesa"); } });
         * 
         */
    }

}
