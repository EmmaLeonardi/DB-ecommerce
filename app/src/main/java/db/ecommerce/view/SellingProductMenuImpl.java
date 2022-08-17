package db.ecommerce.view;

import java.io.IOException;

import db.ecommerce.controller.SellingProductController;
import db.ecommerce.model.ProducerPK;
import db.ecommerce.model.ProductPK;
import db.ecommerce.utils.LoadFXML;
import db.ecommerce.utils.LoadFXMLImpl;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SellingProductMenuImpl {

    private final static String F = "/SellingProductMenu.fxml";
    private final HBox root;
    private SellingProductController sc;

    public SellingProductMenuImpl(Stage s, ProducerPK producer, ProductPK product) throws IOException {
        LoadFXML loader = new LoadFXMLImpl();
        var a = loader.load(F);
        if (a instanceof HBox) {
            this.root = (HBox) a;
        } else {
            var alert = new Alert(AlertType.ERROR, "Something went wrong loading the SellingProductMenu");
            alert.show();
            throw new IOException("The loaded menu wasn't a HBox as expected");
        }
        Scene scene = new Scene(root);
        try {
            var tmp = loader.getController();
            if (tmp instanceof SellingProductController) {
                sc = (SellingProductController) tmp;
                sc.setProducer(producer);
                sc.setProduct(product);
            } else {
                var alert = new Alert(AlertType.ERROR, "Something went wrong loading the SellingProductMenu");
                alert.show();
                throw new IOException("The controller wasn't a Selling Product Controller as expected");
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        s.setScene(scene);
        s.show();
    }

}
