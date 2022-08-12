package db.ecommerce.view;

import java.io.IOException;

import db.ecommerce.controller.ProductsController;
import db.ecommerce.model.ClientPK;
import db.ecommerce.utils.LoadFXML;
import db.ecommerce.utils.LoadFXMLImpl;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ProductsMenuImpl {

    private final static String F = "/ProductsMenu.fxml";
    private final HBox root;
    private ProductsController sc;

    public ProductsMenuImpl(Stage s, ClientPK user) throws IOException {
        LoadFXML loader = new LoadFXMLImpl();
        var a = loader.load(F);
        if (a instanceof HBox) {
            this.root = (HBox) a;
        } else {
            var alert = new Alert(AlertType.ERROR, "Something went wrong loading the ProductsMenu");
            alert.show();
            throw new IOException("The loaded menu wasn't a HBox as expected");
        }
        Scene scene = new Scene(root);
        try {
            var tmp = loader.getController();
            if (tmp instanceof ProductsController) {
                sc = (ProductsController) tmp;
                sc.setClient(user);
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
