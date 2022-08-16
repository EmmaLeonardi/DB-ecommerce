package db.ecommerce.view;

import java.io.IOException;

import db.ecommerce.controller.DeliveryDetailsController;
import db.ecommerce.model.CourierPK;
import db.ecommerce.model.DeliveryPK;
import db.ecommerce.utils.LoadFXML;
import db.ecommerce.utils.LoadFXMLImpl;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DeliveryDetailsMenuImpl {
    
    private final static String F = "/DeliveryDetailsMenu.fxml";
    private final VBox root;
    private DeliveryDetailsController sc;

    public DeliveryDetailsMenuImpl(Stage s, CourierPK courier, DeliveryPK delivery) throws IOException {
        LoadFXML loader = new LoadFXMLImpl();
        var a = loader.load(F);
        if (a instanceof VBox) {
            this.root = (VBox) a;
        } else {
            var alert = new Alert(AlertType.ERROR, "Something went wrong loading the DeliveryDetailsMenu");
            alert.show();
            throw new IOException("The loaded menu wasn't a VBox as expected");
        }
        Scene scene = new Scene(root);
        try {
            var tmp = loader.getController();
            if (tmp instanceof DeliveryDetailsController) {
                sc = (DeliveryDetailsController) tmp;
                sc.setCourier(courier);
                sc.setDelivery(delivery);
            } else {
                var alert = new Alert(AlertType.ERROR, "Something went wrong loading the DeliveryDetailsMenu");
                alert.show();
                throw new IOException("The controller wasn't a DeliveryDetailsController as expected");
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        s.setScene(scene);
        s.show();
    }

}
