package db.ecommerce.view;

import java.io.IOException;

import db.ecommerce.controller.ProductCreationController;
import db.ecommerce.model.ProducerPK;
import db.ecommerce.utils.LoadFXML;
import db.ecommerce.utils.LoadFXMLImpl;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ProductCreationMenuImpl {
    
    private final static String F = "/ProductCreationMenu.fxml";
    private final HBox root;
    private ProductCreationController sc;

    public ProductCreationMenuImpl(Stage s, ProducerPK producer) throws IOException {
        LoadFXML loader = new LoadFXMLImpl();
        var a = loader.load(F);
        if (a instanceof HBox) {
            this.root = (HBox) a;
        } else {
            var alert = new Alert(AlertType.ERROR, "Something went wrong loading the ProductCreationMenu");
            alert.show();
            throw new IOException("The loaded menu wasn't a HBox as expected");
        }
        Scene scene = new Scene(root);
        try {
            var tmp = loader.getController();
            if (tmp instanceof ProductCreationController) {
                sc = (ProductCreationController) tmp;
                sc.setProducer(producer);
            } else {
                var alert = new Alert(AlertType.ERROR, "Something went wrong loading the ProductCreationMenu");
                alert.show();
                throw new IOException("The controller wasn't a Product Creation Controller as expected");
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        s.setScene(scene);
        s.show();
    }

}
