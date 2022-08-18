package db.ecommerce.view;

import java.io.IOException;

import db.ecommerce.utils.LoadFXML;
import db.ecommerce.utils.LoadFXMLImpl;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminMenuImpl {

    private final static String F = "/AdminMenu.fxml";
    private final VBox root;

    public AdminMenuImpl(Stage s) throws IOException {
        LoadFXML loader = new LoadFXMLImpl();
        var a = loader.load(F);
        if (a instanceof VBox) {
            this.root = (VBox) a;
        } else {
            var alert = new Alert(AlertType.ERROR, "Something went wrong loading the AdminMenu");
            alert.show();
            throw new IOException("The loaded menu wasn't a VBox as expected");
        }
        Scene scene = new Scene(root);

        s.setScene(scene);
        s.show();
    }

}
