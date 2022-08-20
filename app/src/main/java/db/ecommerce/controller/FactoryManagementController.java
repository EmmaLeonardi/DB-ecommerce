package db.ecommerce.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import db.ecommerce.model.FactoryManagement;
import db.ecommerce.model.FactoryManagementImpl;
import db.ecommerce.model.FactoryManagementPK;
import db.ecommerce.model.FactoryPK;
import db.ecommerce.model.ProducerPK;
import db.ecommerce.model.tables.FactoryManagementTable;
import db.ecommerce.model.tables.FactoryTable;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class FactoryManagementController {

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
    private Label lbl_cod_gestione_fabbrica;

    @FXML
    private ListView<String> lsvw_factory;

    @FXML
    private ListView<String> lsvw_gestione_fabbriche;

    @FXML
    private ListView<String> lsvw_producers;

    @FXML
    private DatePicker dtpk_start;

    @FXML
    private DatePicker dtpk_end;

    @FXML
    private CheckBox chb_ended;

    private FactoryTable fctTbl;

    private FactoryManagementTable fmgTbl;

    private ProducerTable prdTbl;

    private List<FactoryPK> showFactory;

    private List<FactoryManagementPK> showFactoryManagement;

    private List<ProducerPK> showProducers;

    private final static String NOFACTORYMANAGEMENT = "--";

    @FXML
    public void back(final Event event) {
        if (btn_save.isDisabled() == false || dtpk_start.getValue() == null) {
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
        if (lbl_cod_gestione_fabbrica.getText().equals(NOFACTORYMANAGEMENT)) {
            // E' una gestione fabbrica nuova
            if (dtpk_start.getValue() != null && lsvw_factory.getSelectionModel().getSelectedIndex() != -1
                    && lsvw_producers.getSelectionModel().getSelectedIndex() != -1
                    && (chb_ended.isSelected() == true && dtpk_end.getValue() != null
                            || chb_ended.isSelected() == false)) {
                if (dtpk_end.getValue() != null) {
                    // Ha entrambe le date
                    Date s = Date.from(dtpk_start.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    Date e = Date.from(dtpk_end.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    if (e.before(s)) {
                        var alert = new Alert(AlertType.ERROR, "Le date devono essere nell'ordine corretto");
                        alert.show();
                    } else {
                        FactoryManagement f = new FactoryManagementImpl(
                                showFactory.get(lsvw_factory.getSelectionModel().getSelectedIndex()).getCodFabbrica(),
                                s, Optional.ofNullable(e),
                                showProducers.get(lsvw_producers.getSelectionModel().getSelectedIndex())
                                        .getCod_produttore());
                        var tmp = fmgTbl.save(f);
                        if (tmp.isPresent()) {
                            var alert = new Alert(AlertType.INFORMATION,
                                    "Hai salvato correttamente la gestione fabbrica!");
                            alert.show();
                            lbl_cod_gestione_fabbrica.setText(NOFACTORYMANAGEMENT);
                            dtpk_start.setValue(null);
                            dtpk_end.setValue(null);
                            chb_ended.setSelected(false);
                            lsvw_gestione_fabbriche.setDisable(false);
                            lsvw_gestione_fabbriche.getSelectionModel().select(-1);
                            lsvw_factory.setDisable(true);
                            lsvw_factory.getSelectionModel().select(-1);
                            lsvw_producers.setDisable(true);
                            lsvw_producers.getSelectionModel().select(-1);
                            this.showFactoryManagement = fmgTbl.findAll();
                            lsvw_gestione_fabbriche.setItems(
                                    FXCollections.observableList(buildFactoryManagement(showFactoryManagement)));
                            setDisabledAll(true);
                            this.btn_save.setDisable(true);
                            this.btn_change.setDisable(true);
                            this.btn_delete.setDisable(true);
                            this.btn_new.setDisable(false);
                            this.dtpk_end.setDisable(true);
                        } else {
                            throw new SQLException("Factory management wasn't saved");
                        }
                    }
                } else {
                    // Ha solo una data
                    Date s = Date.from(dtpk_start.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    FactoryManagement f = new FactoryManagementImpl(
                            showFactory.get(lsvw_factory.getSelectionModel().getSelectedIndex()).getCodFabbrica(), s,
                            Optional.ofNullable(null), showProducers
                                    .get(lsvw_producers.getSelectionModel().getSelectedIndex()).getCod_produttore());
                    var tmp = fmgTbl.save(f);
                    if (tmp.isPresent()) {
                        var alert = new Alert(AlertType.INFORMATION, "Hai salvato correttamente la gestione fabbrica!");
                        alert.show();
                        lbl_cod_gestione_fabbrica.setText(NOFACTORYMANAGEMENT);
                        dtpk_start.setValue(null);
                        dtpk_end.setValue(null);
                        chb_ended.setSelected(false);
                        lsvw_gestione_fabbriche.setDisable(false);
                        lsvw_gestione_fabbriche.getSelectionModel().select(-1);
                        lsvw_factory.setDisable(true);
                        lsvw_factory.getSelectionModel().select(-1);
                        lsvw_producers.setDisable(true);
                        lsvw_producers.getSelectionModel().select(-1);
                        this.showFactoryManagement = fmgTbl.findAll();
                        lsvw_gestione_fabbriche
                                .setItems(FXCollections.observableList(buildFactoryManagement(showFactoryManagement)));
                        setDisabledAll(true);
                        this.btn_save.setDisable(true);
                        this.btn_change.setDisable(true);
                        this.btn_delete.setDisable(true);
                        this.btn_new.setDisable(false);
                        this.dtpk_end.setDisable(true);
                    } else {
                        throw new SQLException("Factory management wasn't saved");
                    }

                }
            } else {
                var alert = new Alert(AlertType.ERROR, "Tutti i campi sono obbligatori");
                alert.show();
            }
        } else {
            // E' una gestione fabbrica vecchia
            if (dtpk_start.getValue() != null && lsvw_factory.getSelectionModel().getSelectedIndex() != -1
                    && lsvw_producers.getSelectionModel().getSelectedIndex() != -1
                    && (chb_ended.isSelected() == true && dtpk_end.getValue() != null
                            || chb_ended.isSelected() == false)) {
                var id = Integer.parseInt(lbl_cod_gestione_fabbrica.getText());
                if (dtpk_end.getValue() != null) {
                    // Sto salvando i dati avendo tutti e due le date
                    Date s = Date.from(dtpk_start.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    Date e = Date.from(dtpk_end.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    if (e.before(s)) {
                        var alert = new Alert(AlertType.ERROR, "Le date devono essere nell'ordine corretto");
                        alert.show();
                    } else {
                        FactoryManagementPK f = new FactoryManagementPK(
                                showFactory.get(lsvw_factory.getSelectionModel().getSelectedIndex()).getCodFabbrica(),
                                s, Optional.ofNullable(e), showProducers
                                        .get(lsvw_producers.getSelectionModel().getSelectedIndex()).getCod_produttore(),
                                id);
                        var app = fmgTbl.update(f);
                        if (app) {
                            var alert = new Alert(AlertType.INFORMATION,
                                    "Hai aggiornato correttamente la gestione fabbrica!");
                            alert.show();
                            lbl_cod_gestione_fabbrica.setText(NOFACTORYMANAGEMENT);
                            dtpk_start.setValue(null);
                            dtpk_end.setValue(null);
                            chb_ended.setSelected(false);
                            lsvw_gestione_fabbriche.setDisable(false);
                            lsvw_gestione_fabbriche.getSelectionModel().select(-1);
                            lsvw_factory.setDisable(true);
                            lsvw_factory.getSelectionModel().select(-1);
                            lsvw_producers.setDisable(true);
                            lsvw_producers.getSelectionModel().select(-1);
                            this.showFactoryManagement = fmgTbl.findAll();
                            lsvw_gestione_fabbriche.setItems(
                                    FXCollections.observableList(buildFactoryManagement(showFactoryManagement)));
                            setDisabledAll(true);
                            this.btn_save.setDisable(true);
                            this.btn_change.setDisable(true);
                            this.btn_delete.setDisable(true);
                            this.btn_new.setDisable(false);
                            this.dtpk_end.setDisable(true);
                        }else {
                            throw new SQLException("Factory management wasn't updated");
                        }
                    }
                } else {
                    // Sto salvando i dati con 1 data
                    Date s = Date.from(dtpk_start.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    FactoryManagementPK f = new FactoryManagementPK(
                            showFactory.get(lsvw_factory.getSelectionModel().getSelectedIndex()).getCodFabbrica(), s,
                            Optional.ofNullable(null), showProducers
                                    .get(lsvw_producers.getSelectionModel().getSelectedIndex()).getCod_produttore(),
                            id);
                    var app = fmgTbl.update(f);
                    if (app) {
                        var alert = new Alert(AlertType.INFORMATION,
                                "Hai aggiornato correttamente la gestione fabbrica!");
                        alert.show();
                        lbl_cod_gestione_fabbrica.setText(NOFACTORYMANAGEMENT);
                        dtpk_start.setValue(null);
                        dtpk_end.setValue(null);
                        chb_ended.setSelected(false);
                        lsvw_gestione_fabbriche.setDisable(false);
                        lsvw_gestione_fabbriche.getSelectionModel().select(-1);
                        lsvw_factory.setDisable(true);
                        lsvw_factory.getSelectionModel().select(-1);
                        lsvw_producers.setDisable(true);
                        lsvw_producers.getSelectionModel().select(-1);
                        this.showFactoryManagement = fmgTbl.findAll();
                        lsvw_gestione_fabbriche
                                .setItems(FXCollections.observableList(buildFactoryManagement(showFactoryManagement)));
                        setDisabledAll(true);
                        this.btn_save.setDisable(true);
                        this.btn_change.setDisable(true);
                        this.btn_delete.setDisable(true);
                        this.btn_new.setDisable(false);
                        this.dtpk_end.setDisable(true);
                    } else {
                        throw new SQLException("Factory management wasn't updated");
                    }
                }
            } else {
                var alert = new Alert(AlertType.ERROR, "Tutti i campi sono obbligatori");
                alert.show();
            }
        }
    }

    @FXML
    public void newFactoryManagement(final Event event) {
        setDisabledAll(false);
        lbl_cod_gestione_fabbrica.setText(NOFACTORYMANAGEMENT);
        btn_change.setDisable(true);
        btn_save.setDisable(false);
        btn_new.setDisable(true);
        btn_delete.setDisable(true);
        lsvw_gestione_fabbriche.setDisable(true);
        dtpk_end.setValue(null);
        dtpk_start.setValue(null);
        lsvw_factory.setDisable(false);
        lsvw_producers.setDisable(false);

    }

    @FXML
    public void change(final Event event) {
        setDisabledAll(false);
        btn_change.setDisable(true);
        btn_save.setDisable(false);
        btn_new.setDisable(true);
        btn_delete.setDisable(true);
        lsvw_gestione_fabbriche.setDisable(true);
        lsvw_producers.setDisable(false);
        lsvw_factory.setDisable(false);
    }

    @FXML
    public void delete(final Event event) {
        System.out.println("Elimina");
    }

    @FXML
    public void has_ended(final Event event) {
        if (chb_ended.isSelected()) {
            dtpk_end.setDisable(false);
        } else {
            dtpk_end.setDisable(true);
            dtpk_end.setValue(null);
        }
    }

    public void initialize() {
        ConnectionProvider c = new ConnectionProviderImpl(Credentials.getUsername(), Credentials.getPassword(),
                Credentials.getDbname());
        this.fmgTbl = new FactoryManagementTable(c.getMySQLConnection());
        this.fctTbl = new FactoryTable(c.getMySQLConnection());
        this.prdTbl = new ProducerTable(c.getMySQLConnection());
        Platform.runLater(() -> {
            this.showFactoryManagement = fmgTbl.findAll();
            lsvw_gestione_fabbriche
                    .setItems(FXCollections.observableList(buildFactoryManagement(showFactoryManagement)));

            this.showFactory = fctTbl.findAll();
            lsvw_factory.setItems(FXCollections.observableList(buildFactory(showFactory)));

            this.showProducers = prdTbl.findAll();
            lsvw_producers.setItems(FXCollections.observableList(buildProducer(showProducers)));

            this.setDisabledAll(true);
            this.btn_save.setDisable(true);
            this.btn_change.setDisable(true);
            this.btn_delete.setDisable(true);
            lbl_cod_gestione_fabbrica.setText(NOFACTORYMANAGEMENT);
            chb_ended.setDisable(true);
            dtpk_end.setDisable(true);
            dtpk_end.setValue(null);
            dtpk_start.setValue(null);
            lsvw_factory.setDisable(true);
            lsvw_producers.setDisable(true);
            lsvw_gestione_fabbriche.getSelectionModel().selectedItemProperty()
                    .addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                            if (lsvw_gestione_fabbriche.getSelectionModel().getSelectedIndex() != -1) {
                                var item = showFactoryManagement
                                        .get(lsvw_gestione_fabbriche.getSelectionModel().getSelectedIndex());
                                setDisabledAll(true);
                                lbl_cod_gestione_fabbrica.setText(item.getCodGestione() + "");
                                dtpk_start.setValue(
                                        item.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                                dtpk_end.setValue(item.getEnd().isPresent()
                                        ? item.getEnd().get().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                                        : null);
                                var f = fctTbl.findByPrimaryKey(item.getCodFabbrica());
                                lsvw_factory.getSelectionModel().select(showFactory.indexOf(f.get()));
                                var p = prdTbl.findByPrimaryKey(item.getCodProduttore());
                                lsvw_producers.getSelectionModel().select(showProducers.indexOf(p.get()));
                                btn_change.setDisable(false);
                                btn_delete.setDisable(false);
                                lsvw_factory.setDisable(true);
                                lsvw_producers.setDisable(true);
                                chb_ended.setDisable(true);

                            } else {
                                setDisabledAll(true);
                                btn_save.setDisable(true);
                                btn_change.setDisable(true);
                                btn_delete.setDisable(true);
                                dtpk_start.setValue(null);
                                chb_ended.setDisable(true);
                                lbl_cod_gestione_fabbrica.setText(NOFACTORYMANAGEMENT);
                                lsvw_factory.getSelectionModel().select(-1);
                                lsvw_producers.getSelectionModel().select(-1);
                                lsvw_factory.setDisable(true);
                                lsvw_producers.setDisable(true);
                            }
                        }
                    });
        });
    }

    private List<String> buildFactoryManagement(List<FactoryManagementPK> all) {
        List<String> list = new ArrayList<>();
        for (var elem : all) {
            String s = "" + elem.getCodGestione() + " Fabbrica:" + elem.getCodFabbrica() + " Produttore:"
                    + elem.getCodProduttore();
            list.add(s);
        }
        return list;
    }

    private List<String> buildFactory(List<FactoryPK> all) {
        List<String> list = new ArrayList<>();
        for (var elem : all) {
            String s = "" + elem.getCodFabbrica() + " " + elem.getVia() + " " + elem.getCitta();
            list.add(s);
        }
        return list;
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
        this.chb_ended.setDisable(b);
        this.dtpk_start.setDisable(b);
        this.chb_ended.setDisable(b);
        this.chb_ended.setSelected(false);
    }

}
