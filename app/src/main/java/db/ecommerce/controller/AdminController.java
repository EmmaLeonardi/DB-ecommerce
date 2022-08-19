package db.ecommerce.controller;

import java.io.IOException;

import db.ecommerce.view.ClientMenuImpl;
import db.ecommerce.view.CourierMenuImpl;
import db.ecommerce.view.FactoryMenuImpl;
import db.ecommerce.view.ProducerMenuImpl;
import db.ecommerce.view.VehicleMenuImpl;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminController {

    @FXML
    private Button btn_customers;

    @FXML
    private Button btn_producers;

    @FXML
    private Button btn_couriers;

    @FXML
    private Button btn_vehicle;

    @FXML
    private Button btn_factory_management;

    @FXML
    private Button btn_factory;

    @FXML
    public void new_client(final Event event) {
        Stage s = (Stage) btn_customers.getScene().getWindow();
        try {
            new ClientMenuImpl(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void new_producer(final Event event) {
        Stage s = (Stage) btn_producers.getScene().getWindow();
        try {
            new ProducerMenuImpl(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void new_courier(final Event event) {
        Stage s = (Stage) btn_couriers.getScene().getWindow();
        try {
            new CourierMenuImpl(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void new_factory(final Event event) {
        Stage s = (Stage) btn_factory.getScene().getWindow();
        try {
            new FactoryMenuImpl(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void new_vehicle(final Event event) {
        Stage s = (Stage) btn_vehicle.getScene().getWindow();
        try {
            new VehicleMenuImpl(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void new_factory_management(final Event event) {
        System.out.println("Nuova gestione fabbriche");
    }

}
