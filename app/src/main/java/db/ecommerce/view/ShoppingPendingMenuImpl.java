package db.ecommerce.view;

import java.io.IOException;

import db.ecommerce.controller.ShoppingPendingController;
import db.ecommerce.model.ClientPK;
import db.ecommerce.utils.LoadFXML;
import db.ecommerce.utils.LoadFXMLImpl;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ShoppingPendingMenuImpl {

    private final static String F = "/ShoppingPendingMenu.fxml";
    private final VBox root;
    private ShoppingPendingController sc;

    public ShoppingPendingMenuImpl(Stage s, ClientPK user) throws IOException {
        LoadFXML loader = new LoadFXMLImpl();
        var a = loader.load(F);
        if (a instanceof VBox) {
            this.root = (VBox) a;
        } else {
            var alert = new Alert(AlertType.ERROR, "Something went wrong loading the ShoppingPendingMenu");
            alert.show();
            throw new IOException("The loaded menu wasn't a VBox as expected");
        }
        Scene scene = new Scene(root);
        try {
            var tmp = loader.getController();
            if (tmp instanceof ShoppingPendingController) {
                sc = (ShoppingPendingController) tmp;
                sc.setClient(user);
            } else {
                var alert = new Alert(AlertType.ERROR, "Something went wrong loading the ShoppingPendingMenu");
                alert.show();
                throw new IOException("The controller wasn't a Shopping Pending Controller as expected");
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        s.setScene(scene);
        s.show();
    }

}
