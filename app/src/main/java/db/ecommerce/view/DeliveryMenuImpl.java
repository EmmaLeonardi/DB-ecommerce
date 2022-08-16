package db.ecommerce.view;

import java.io.IOException;

import db.ecommerce.controller.DeliveryController;
import db.ecommerce.model.CourierPK;
import db.ecommerce.utils.LoadFXML;
import db.ecommerce.utils.LoadFXMLImpl;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DeliveryMenuImpl {

    private final static String F = "/DeliveryMenu.fxml";
    private final VBox root;
    private DeliveryController sc;

    public DeliveryMenuImpl(Stage s, CourierPK courier) throws IOException {
        LoadFXML loader = new LoadFXMLImpl();
        var a = loader.load(F);
        if (a instanceof VBox) {
            this.root = (VBox) a;
        } else {
            var alert = new Alert(AlertType.ERROR, "Something went wrong loading the DeliveryMenu");
            alert.show();
            throw new IOException("The loaded menu wasn't a HBox as expected");
        }
        Scene scene = new Scene(root);
        try {
            var tmp = loader.getController();
            if (tmp instanceof DeliveryController) {
                sc = (DeliveryController) tmp;
                sc.setCourier(courier);
            } else {
                var alert = new Alert(AlertType.ERROR, "Something went wrong loading the ProductsMenu");
                alert.show();
                throw new IOException("The controller wasn't a ProductsController as expected");
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        s.setScene(scene);
        s.show();
    }

}
