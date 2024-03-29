package db.ecommerce.view;

import java.io.IOException;

import db.ecommerce.controller.ProductDetailsController;
import db.ecommerce.model.ClientPK;
import db.ecommerce.model.SoldProductPK;
import db.ecommerce.utils.LoadFXML;
import db.ecommerce.utils.LoadFXMLImpl;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ProductDetailsMenuImpl {

    private final static String F = "/ProductDetailsMenu.fxml";
    private final HBox root;
    private ProductDetailsController sc;

    public ProductDetailsMenuImpl(Stage s, ClientPK user, SoldProductPK p) throws IOException {
        LoadFXML loader = new LoadFXMLImpl();
        var a = loader.load(F);
        if (a instanceof HBox) {
            this.root = (HBox) a;
        } else {
            var alert = new Alert(AlertType.ERROR, "Something went wrong loading the ProductDetailsMenu");
            alert.show();
            throw new IOException("The loaded menu wasn't a HBox as expected");
        }
        Scene scene = new Scene(root);
        try {
            var tmp = loader.getController();
            if (tmp instanceof ProductDetailsController) {
                sc = (ProductDetailsController) tmp;
                sc.setClient(user);
                sc.setProduct(p);
            } else {
                var alert = new Alert(AlertType.ERROR, "Something went wrong loading the ProductDetailsMenu");
                alert.show();
                throw new IOException("The controller wasn't a ProductsDetailsController as expected");
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        s.setScene(scene);
        s.show();
    }

}
