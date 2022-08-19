package db.ecommerce.view;

import java.io.IOException;

import db.ecommerce.utils.LoadFXML;
import db.ecommerce.utils.LoadFXMLImpl;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FactoryManagementMenuImpl {

    private final static String F = "/FactoryManagementMenu.fxml";
    private final HBox root;

    public FactoryManagementMenuImpl(Stage s) throws IOException {
        LoadFXML loader = new LoadFXMLImpl();
        var a = loader.load(F);
        if (a instanceof HBox) {
            this.root = (HBox) a;
        } else {
            var alert = new Alert(AlertType.ERROR, "Something went wrong loading the FactoryManagementMenu");
            alert.show();
            throw new IOException("The loaded menu wasn't a HBox as expected");
        }
        Scene scene = new Scene(root);

        s.setScene(scene);
        s.show();
    }

}
