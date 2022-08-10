package db.ecommerce.view;

import java.io.IOException;

import db.ecommerce.utils.LoadFXML;
import db.ecommerce.utils.LoadFXMLImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginMenuImpl extends Application {

    private final static String F = "/LoginMenu.fxml";
    private VBox root;

    public LoginMenuImpl() throws IOException {
        /*
         * LoadFXML loader = new LoadFXMLImpl(); var a = loader.load(F);
         */

        /*
         * var l=new FXMLLoader(); l.setLocation(getClass().getResource(F)); var
         * a=l.load();
         */

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        /*
         * FXMLLoader loader = new FXMLLoader();
         * loader.setLocation(getClass().getResource("/LoginMenu.fxml")); var a =
         * loader.load();
         */

        LoadFXML loader = new LoadFXMLImpl();
        var a = loader.load(F);

        if (a instanceof VBox) {
            this.root = (VBox) a;
        } else {
            var alert = new Alert(AlertType.ERROR, "Something went wrong loading the LoginMenu");
            alert.show();
            throw new IOException("The loaded menu wasn't a VBox as expected");
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
