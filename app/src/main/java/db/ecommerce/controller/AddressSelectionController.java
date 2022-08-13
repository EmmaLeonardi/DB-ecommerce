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
import db.ecommerce.utils.TYPEDELIVERY;
import javafx.application.Platform;
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
    }

    /**
     * This method is called at the start and inizializes the list view
     */
    public void initialize() {
        ConnectionProvider c = new ConnectionProviderImpl(Credentials.getUsername(), Credentials.getPassword(),
                Credentials.getDbname());
//        this.slpTbl = new SoldProductTable(c.getMySQLConnection());
//        this.prdTbl = new ProductTable(c.getMySQLConnection());
//
        Platform.runLater(() -> {
//            List<SoldProductPK> allProduct = slpTbl.findAll().stream().filter(p -> {
//                if (p.getEnd().isEmpty() || (p.getEnd().isPresent() && p.getEnd().get().compareTo(new Date()) > 0)) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }).collect(Collectors.toList());
//            this.lstvw_products.setItems(FXCollections.observableList(this.buildProduct(allProduct)));
//            this.fullList = new ArrayList<>(allProduct);
//            this.filteredListShop = new ArrayList<>(allProduct);
//            this.cartList = new ArrayList<>();
//
//            var sizes = allProduct.stream().map(p -> p.getSize()).distinct().filter(s -> s.isPresent())
//                    .map(s -> s.get()).collect(Collectors.toList());
//
//            this.cbx_size.setItems(FXCollections.observableList(sizes));
            System.out.println(list.toString());
        });
        //Non so perchè tira eccezione, sistema
        /*cmb_delivery_type.setItems(
                FXCollections.observableList(List.of(TYPEDELIVERY.STANDARD.name(), TYPEDELIVERY.PREMIUM.name())));*/

    }

}
