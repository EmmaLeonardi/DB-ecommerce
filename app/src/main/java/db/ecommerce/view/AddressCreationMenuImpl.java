package db.ecommerce.view;

import java.io.IOException;
import java.util.List;

import db.ecommerce.controller.AddressCreationController;
import db.ecommerce.model.ClientPK;
import db.ecommerce.model.SoldProductPK;
import db.ecommerce.utils.LoadFXML;
import db.ecommerce.utils.LoadFXMLImpl;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddressCreationMenuImpl {

    private final static String F = "/AddressCreationMenu.fxml";
    private final VBox root;
    private AddressCreationController ac;

    public AddressCreationMenuImpl(Stage s, ClientPK user, List<SoldProductPK> list) throws IOException {
        LoadFXML loader = new LoadFXMLImpl();
        var a = loader.load(F);
        if (a instanceof VBox) {
            this.root = (VBox) a;
        } else {
            var alert = new Alert(AlertType.ERROR, "Something went wrong loading the AddressCreationMenu");
            alert.show();
            throw new IOException("The loaded menu wasn't a VBox as expected");
        }
        Scene scene = new Scene(root);
        try {
            var tmp = loader.getController();
            if (tmp instanceof AddressCreationController) {
                ac = (AddressCreationController) tmp;
                ac.setClient(user);
                ac.setShopping(list);
            } else {
                var alert = new Alert(AlertType.ERROR, "Something went wrong loading the AddressCreationMenu");
                alert.show();
                throw new IOException("The controller wasn't a AddressCreation Controller as expected");
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        s.setScene(scene);
        s.show();
    }

}
