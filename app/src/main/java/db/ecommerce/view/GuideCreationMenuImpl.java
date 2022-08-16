package db.ecommerce.view;

import java.io.IOException;

import db.ecommerce.controller.GuideCreationController;
import db.ecommerce.model.CourierPK;
import db.ecommerce.utils.LoadFXML;
import db.ecommerce.utils.LoadFXMLImpl;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GuideCreationMenuImpl {
    
    private final static String F = "/GuideCreationMenu.fxml";
    private final HBox root;
    private GuideCreationController sc;

    public GuideCreationMenuImpl(Stage s, CourierPK courier) throws IOException {
        LoadFXML loader = new LoadFXMLImpl();
        var a = loader.load(F);
        if (a instanceof HBox) {
            this.root = (HBox) a;
        } else {
            var alert = new Alert(AlertType.ERROR, "Something went wrong loading the GuideCreationMenu");
            alert.show();
            throw new IOException("The loaded menu wasn't a HBox as expected");
        }
        Scene scene = new Scene(root);
        try {
            var tmp = loader.getController();
            if (tmp instanceof GuideCreationController) {
                sc = (GuideCreationController) tmp;
                sc.setCourier(courier);
            } else {
                var alert = new Alert(AlertType.ERROR, "Something went wrong loading the GuideCreationMenu");
                alert.show();
                throw new IOException("The controller wasn't a GuideCreationController as expected");
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        s.setScene(scene);
        s.show();
    }

}
