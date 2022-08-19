package db.ecommerce.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import db.ecommerce.model.Producer;
import db.ecommerce.model.ProducerImpl;
import db.ecommerce.model.ProducerPK;
import db.ecommerce.model.tables.ProducerTable;
import db.ecommerce.utils.ConnectionProvider;
import db.ecommerce.utils.ConnectionProviderImpl;
import db.ecommerce.utils.Credentials;
import db.ecommerce.view.AdminMenuImpl;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ProducerController {
    @FXML
    private Button btn_back;

    @FXML
    private Button btn_save;

    @FXML
    private Button btn_change;

    @FXML
    private Button btn_new;

    @FXML
    private Button btn_delete;

    @FXML
    private ListView<String> lstvw_producers;

    @FXML
    private TextField txt_cf;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_surname;

    @FXML
    private DatePicker dtpkr_birthday;

    @FXML
    private TextField txt_IVA;

    @FXML
    private Label lbl_cod_produttore;

    private ProducerTable prdTbl;

    private List<ProducerPK> showProducer;

    private final static String NOPRODUCER = "--";

    @FXML
    public void back(final Event event) {
        if (btn_save.isDisabled() == false || txt_cf.getText() != "" || txt_name.getText() != ""
                || txt_surname.getText() != "" || dtpkr_birthday.getValue() != null || txt_IVA.getText() != "") {
            var alert = new Alert(AlertType.WARNING,
                    "Stai per tornare al menu principale, ma perderai i dati inseriti. Continuare?", ButtonType.NO,
                    ButtonType.YES);
            alert.showAndWait().filter(response -> response == ButtonType.YES).ifPresent(response -> {
                Stage s = (Stage) btn_back.getScene().getWindow();
                try {
                    new AdminMenuImpl(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } else {
            Stage s = (Stage) btn_back.getScene().getWindow();
            try {
                new AdminMenuImpl(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void save(final Event event) throws SQLException {
        if (lbl_cod_produttore.getText().equals(NOPRODUCER)) {
            // E' un produttore nuovo
            if (txt_cf.getText() != "" && txt_name.getText() != "" && txt_surname.getText() != ""
                    && dtpkr_birthday.getValue() != null && txt_IVA.getText() != "") {
                Producer p = new ProducerImpl(txt_cf.getText(), txt_name.getText(), txt_surname.getText(),
                        Date.from(dtpkr_birthday.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        txt_IVA.getText());
                var tmp = prdTbl.save(p);
                if (tmp.isPresent()) {
                    var alert = new Alert(AlertType.INFORMATION,
                            "Hai salvato correttamente il nuovo produttore! Ora può usare l'applicazione con identificatore "
                                    + tmp.get().getCod_produttore() + " e password " + tmp.get().getName());
                    alert.show();
                    txt_name.setText("");
                    txt_surname.setText("");
                    dtpkr_birthday.setValue(null);
                    txt_cf.setText("");
                    txt_name.setText("");
                    txt_surname.setText("");
                    txt_IVA.setText("");
                    dtpkr_birthday.setValue(null);
                    lbl_cod_produttore.setText(NOPRODUCER);
                    lstvw_producers.setDisable(false);
                    lstvw_producers.getSelectionModel().select(-1);
                    this.showProducer = prdTbl.findAll();
                    lstvw_producers.setItems(FXCollections.observableList(buildProducer(showProducer)));
                    setDisabledAll(true);
                    this.btn_save.setDisable(true);
                    this.btn_change.setDisable(true);
                    this.btn_delete.setDisable(true);
                    this.btn_new.setDisable(false);
                } else {
                    throw new SQLException("Producer wasn't saved");
                }
            } else {
                var alert = new Alert(AlertType.ERROR, "Tutti i campi sono obbligatori");
                alert.show();
            }
        } else {
            // E' un produttore vecchio
            if (txt_cf.getText() != "" && txt_name.getText() != "" && txt_surname.getText() != ""
                    && dtpkr_birthday.getValue() != null && txt_IVA.getText() != "") {
                int id = Integer.parseInt(lbl_cod_produttore.getText());
                ProducerPK c = new ProducerPK(id, txt_cf.getText(), txt_name.getText(), txt_surname.getText(),
                        Date.from(dtpkr_birthday.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        txt_IVA.getText());
                var app = prdTbl.update(c);
                if (app) {
                    var alert = new Alert(AlertType.INFORMATION, "Hai aggiornato correttamente il produttore!");
                    alert.show();
                    txt_name.setText("");
                    txt_surname.setText("");
                    dtpkr_birthday.setValue(null);
                    txt_cf.setText("");
                    txt_name.setText("");
                    txt_surname.setText("");
                    txt_IVA.setText("");
                    dtpkr_birthday.setValue(null);
                    lbl_cod_produttore.setText(NOPRODUCER);
                    lstvw_producers.setDisable(false);
                    lstvw_producers.getSelectionModel().select(-1);
                    this.showProducer = prdTbl.findAll();
                    lstvw_producers.setItems(FXCollections.observableList(buildProducer(showProducer)));
                    setDisabledAll(true);
                    this.btn_save.setDisable(true);
                    this.btn_change.setDisable(true);
                    this.btn_delete.setDisable(true);
                    this.btn_new.setDisable(false);
                } else {
                    throw new SQLException("Producer wasn't updated");
                }
            } else {
                var alert = new Alert(AlertType.ERROR, "Tutti i campi sono obbligatori");
                alert.show();
            }
        }

    }

    @FXML
    public void newProducer(final Event event) {
        setDisabledAll(false);
        lbl_cod_produttore.setText(NOPRODUCER);
        btn_change.setDisable(true);
        btn_save.setDisable(false);
        btn_new.setDisable(true);
        btn_delete.setDisable(true);
        txt_cf.setText("");
        txt_name.setText("");
        txt_surname.setText("");
        txt_IVA.setText("");
        dtpkr_birthday.setValue(null);

    }

    @FXML
    public void change(final Event event) {
        setDisabledAll(false);
        btn_change.setDisable(true);
        btn_save.setDisable(false);
        btn_new.setDisable(true);
        btn_delete.setDisable(true);
        lstvw_producers.setDisable(true);
    }

    @FXML
    public void delete(final Event event) {
        System.out.println("Elimina");
    }

    public void initialize() {
        ConnectionProvider c = new ConnectionProviderImpl(Credentials.getUsername(), Credentials.getPassword(),
                Credentials.getDbname());
        this.prdTbl = new ProducerTable(c.getMySQLConnection());
        Platform.runLater(() -> {
            this.showProducer = prdTbl.findAll();
            lstvw_producers.setItems(FXCollections.observableList(buildProducer(showProducer)));
            this.setDisabledAll(true);
            this.btn_save.setDisable(true);
            this.btn_change.setDisable(true);
            this.btn_delete.setDisable(true);
            txt_cf.setText("");
            txt_name.setText("");
            txt_surname.setText("");
            txt_IVA.setText("");
            dtpkr_birthday.setValue(null);
            lbl_cod_produttore.setText(NOPRODUCER);

            lstvw_producers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (lstvw_producers.getSelectionModel().getSelectedIndex() != -1) {
                        var item = showProducer.get(lstvw_producers.getSelectionModel().getSelectedIndex());
                        setDisabledAll(true);
                        lbl_cod_produttore.setText(item.getCod_produttore() + "");
                        txt_cf.setText(item.getCodFis());
                        txt_name.setText(item.getName());
                        txt_surname.setText(item.getSurname());
                        dtpkr_birthday
                                .setValue(item.getDateBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        txt_IVA.setText(item.getPIVA());
                        btn_change.setDisable(false);
                        btn_delete.setDisable(false);

                    } else {
                        setDisabledAll(true);
                        btn_save.setDisable(true);
                        btn_change.setDisable(true);
                        btn_delete.setDisable(true);
                        txt_cf.setText("");
                        txt_name.setText("");
                        txt_surname.setText("");
                        dtpkr_birthday.setValue(null);
                        txt_IVA.setText("");
                        lbl_cod_produttore.setText(NOPRODUCER);
                    }
                }
            });
        });
    }

    private List<String> buildProducer(List<ProducerPK> all) {
        List<String> list = new ArrayList<>();
        for (var elem : all) {
            String s = "" + elem.getCod_produttore() + " " + elem.getName() + " " + elem.getSurname();
            list.add(s);
        }
        return list;
    }

    /** The same thing as giving b to all the TextFields and the DatePicker */
    private void setDisabledAll(boolean b) {
        this.txt_cf.setDisable(b);
        this.txt_name.setDisable(b);
        this.txt_surname.setDisable(b);
        this.dtpkr_birthday.setDisable(b);
        this.txt_IVA.setDisable(b);
    }

}
