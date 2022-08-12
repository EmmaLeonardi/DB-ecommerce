package db.ecommerce.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import db.ecommerce.model.ClientPK;
import db.ecommerce.model.SoldProductPK;
import db.ecommerce.model.tables.ProductTable;
import db.ecommerce.model.tables.SoldProductTable;
import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import db.ecommerce.utils.PRODUCTTYPE;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitMenuButton;

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

    private ProductTable prdTbl;

    private SoldProductTable slpTbl;

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
        this.slpTbl = new SoldProductTable(c.getMySQLConnection());
        this.prdTbl = new ProductTable(c.getMySQLConnection());

        Platform.runLater(() -> {
            List<SoldProductPK> allProduct = slpTbl.findAll().stream().filter(p -> {
                if (p.getEnd().isEmpty() || (p.getEnd().isPresent() && p.getEnd().get().compareTo(new Date()) > 0)) {
                    return true;
                } else {
                    return false;
                }
            }).collect(Collectors.toList());
            this.lstvw_products.setItems(FXCollections.observableList(this.buildProduct(allProduct)));
        });

    }

    private List<String> buildProduct(List<SoldProductPK> l) {
        List<String> list = new ArrayList<>();
        for (final SoldProductPK elem : l) {
            var p = prdTbl.findByPrimaryKey(elem.getCodProduct());
            String s = "Prodotto ";
            s = s + "Tipo " + elem.getType().name() + " ";
            s = s + "Prezzo " + elem.getPrice() + " ";
            if (elem.getType() == PRODUCTTYPE.ALIMENTARE) {
                s = s + "Scadenza " + elem.getExpiration().get() + " ";
            } else {
                s = s + "Taglia " + elem.getSize().get() + " ";
            }
            if (p.isPresent()) {
                s = s + "Materiale " + p.get().getMateriale() + " ";
                s = s + "Descrizione: " + p.get().getDescrizione() + " ";
            }
            list.add(s);
        }
        return list;
    }

}
