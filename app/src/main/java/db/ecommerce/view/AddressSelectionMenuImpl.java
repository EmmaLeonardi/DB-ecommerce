package db.ecommerce.view;

import java.io.IOException;
import java.util.List;

import db.ecommerce.controller.AddressSelectionController;
import db.ecommerce.model.ClientPK;
import db.ecommerce.model.SoldProductPK;
import db.ecommerce.utils.LoadFXML;
import db.ecommerce.utils.LoadFXMLImpl;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AddressSelectionMenuImpl {

    private final static String F = "/AddressSelectionMenu.fxml";
    private final HBox root;
    private AddressSelectionController ac;

    public AddressSelectionMenuImpl(Stage s, ClientPK c, List<SoldProductPK> l) throws IOException {
        LoadFXML loader = new LoadFXMLImpl();
        var a = loader.load(F);
        if (a instanceof HBox) {
            this.root = (HBox) a;
        } else {
            var alert = new Alert(AlertType.ERROR, "Something went wrong loading the AddressSelectionMenu");
            alert.show();
            throw new IOException("The loaded menu wasn't a HBox as expected");
        }
        Scene scene = new Scene(root);
        try {
            var tmp = loader.getController();
            if (tmp instanceof AddressSelectionController) {
                ac = (AddressSelectionController) tmp;
                ac.setClient(c);
                ac.setShopping(l);
            } else {
                var alert = new Alert(AlertType.ERROR, "Something went wrong loading the AddressSelectionMenu");
                alert.show();
                throw new IOException("The controller wasn't a AddressSelection Controller as expected");
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        s.setScene(scene);
        s.show();
    }


}
