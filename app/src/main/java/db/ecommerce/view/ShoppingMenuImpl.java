package db.ecommerce.view;

import java.io.IOException;

import db.ecommerce.controller.ShoppingController;
import db.ecommerce.model.ClientPK;
import db.ecommerce.utils.LoadFXML;
import db.ecommerce.utils.LoadFXMLImpl;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ShoppingMenuImpl {

    private final static String F = "/ShoppingMenu.fxml";
    private final HBox root;
    private ShoppingController sc;

    public ShoppingMenuImpl(Stage s, ClientPK c) throws IOException {
        LoadFXML loader = new LoadFXMLImpl();
        var a = loader.load(F);
        if (a instanceof HBox) {
            this.root = (HBox) a;
        } else {
            var alert = new Alert(AlertType.ERROR, "Something went wrong loading the ShoppingMenu");
            alert.show();
            throw new IOException("The loaded menu wasn't a HBox as expected");
        }
        Scene scene = new Scene(root);
        try {
            var tmp = loader.getController();
            if (tmp instanceof ShoppingController) {
                sc = (ShoppingController) tmp;
                sc.setClient(c);
            } else {
                var alert = new Alert(AlertType.ERROR, "Something went wrong loading the ShoppingMenu");
                alert.show();
                throw new IOException("The controller wasn't a Shopping Controller as expected");
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        s.setScene(scene);
        s.show();
    }
}
