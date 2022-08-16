package db.ecommerce.model.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import db.ecommerce.model.ClientPK;
import db.ecommerce.model.CourierPK;
import db.ecommerce.model.Delivery;
import db.ecommerce.model.DeliveryPK;
import db.ecommerce.model.ShoppingPK;
import db.ecommerce.utils.DateConverter;
import db.ecommerce.utils.TYPEDELIVERY;

public class DeliveryTable implements Table<DeliveryPK, Integer> {

    private final Connection conn;
    private final static String TABLE_NAME = "CONSEGNE";

    /**
     * @param conn the connection to the db
     */
    public DeliveryTable(Connection conn) {
        this.conn = conn;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public boolean createTable() {
        final String query = "CREATE TABLE" + TABLE_NAME + " (Cod_consegna int not null auto_increment,"
                + "Cod_spesa bigint," + "Costo_consegna decimal(6,2) not null," + "Data date,"
                + "Tipo varchar(20) not null," + "Cod_indirizzo int not null," + "Cod_corriere int,"
                + "Targa varchar(15)," + "constraint IDCONSEGNA primary key (Cod_consegna),"
                + "constraint FKRECAPITATO_ID unique (Cod_spesa))";
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.executeUpdate(query);
            return true;
        } catch (final SQLException e) {
            return false;
        }
    }

    @Override
    public boolean dropTable() {
        final String query = "DROP TABLE " + TABLE_NAME;
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.executeUpdate(query);
            return true;
        } catch (final SQLException e) {
            return false;
        }
    }

    @Override
    public Optional<DeliveryPK> findByPrimaryKey(Integer primaryKey) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE Cod_consegna=?";
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, primaryKey);
            var result = statement.executeQuery();
            return convertResultSet(result).stream().findFirst();
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    private List<DeliveryPK> convertResultSet(ResultSet result) {
        final List<DeliveryPK> list = new ArrayList<>();
        if (result != null) {
            try {
                while (result.next()) {
                    list.add(new DeliveryPK(Optional.ofNullable(result.getInt("Cod_spesa")),
                            result.getDouble("Costo_consegna"),
                            Optional.ofNullable(DateConverter.sqlDateToDate(result.getDate("Data"))),
                            TYPEDELIVERY.convert(result.getString("Tipo")), result.getInt("Cod_indirizzo"),
                            Optional.ofNullable(
                                    result.getInt("Cod_corriere") == 0 ? null : result.getInt("Cod_corriere")),
                            Optional.ofNullable(result.getString("Targa")), result.getInt("Cod_consegna")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<DeliveryPK> findAll() {
        final String query = "SELECT * FROM " + TABLE_NAME;
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            var result = statement.executeQuery();
            return convertResultSet(result);
        } catch (final SQLException e) {
            return List.of();
        }
    }

    /**
     * Saves the Delivery into the db
     */
    public Optional<DeliveryPK> save(Delivery value) {
        final String query = "INSERT INTO " + TABLE_NAME
                + " (Cod_spesa, Costo_consegna, Data, Tipo, Cod_indirizzo, Cod_corriere, Targa)"
                + " VALUES (?,?,?,?,?,?,?)";
        try (final PreparedStatement statement = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, value.getCod_spesa().orElse(0));
            statement.setDouble(2, value.getPriceDelivery());
            statement.setDate(3, value.getDate().isEmpty() ? null : DateConverter.dateToSqlDate(value.getDate().get()));
            statement.setString(4, value.getType().name());
            statement.setInt(5, value.getCodIndirizzo());
            statement.setInt(6, value.getCodCorriere().isPresent() ? value.getCodCorriere().get() : null);
            statement.setString(7, value.getTarga().orElse(null));
            final var r = statement.executeUpdate();
            if (r == 1) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return Optional.ofNullable(new DeliveryPK(value, generatedKeys.getInt(1)));
                    }
                }
            }
        } catch (final SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    /**
     * Saves the Delivery into the db, used by the Client
     */
    public Optional<DeliveryPK> saveClient(Delivery value) {
        final String query = "INSERT INTO " + TABLE_NAME + " (Cod_spesa, Costo_consegna, Tipo, Cod_indirizzo)"
                + " VALUES (?,?,?,?)";
        try (final PreparedStatement statement = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, value.getCod_spesa().orElse(0));
            statement.setDouble(2, value.getPriceDelivery());
            statement.setString(3, value.getType().name());
            statement.setInt(4, value.getCodIndirizzo());
            final var r = statement.executeUpdate();
            if (r == 1) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return Optional.ofNullable(new DeliveryPK(value, generatedKeys.getInt(1)));
                    }
                }
            }
        } catch (final SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc} Since the Cod_consegna is a value generated by the Database,
     * all DeliveryPK will be cast to Delivery and Cod_consegna values will be
     * ignored
     */
    @Override
    public Optional<DeliveryPK> save(DeliveryPK value) {
        return this.save(DeliveryPK.convertToDelivery(value));
    }

    @Override
    public boolean update(DeliveryPK value) {
        final String query = "UPDATE " + TABLE_NAME
                + "Cod_spesa=?, Costo_consegna=?, Data=?, Tipo=?, Cod_indirizzo=?, Cod_corriere=?, Targa=?"
                + " WHERE Cod_consegna=?";

        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, value.getCod_spesa().orElseGet(null));
            statement.setDouble(2, value.getPriceDelivery());
            statement.setDate(3, value.getDate().isEmpty() ? null : DateConverter.dateToSqlDate(value.getDate().get()));
            statement.setString(4, value.getType().name());
            statement.setInt(5, value.getCodIndirizzo());
            statement.setInt(6, value.getCodCorriere().orElseGet(null));
            statement.setString(7, value.getTarga().orElse(null));
            final var r = statement.executeUpdate();
            return r == 1;
        } catch (final SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Integer primaryKey) {
        final String query = "DELETE FROM " + TABLE_NAME + " WHERE Cod_consegna=?";

        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, primaryKey);
            final var r = statement.executeUpdate();
            return r == 1;
        } catch (final SQLException e) {
            return false;
        }
    }

    /**
     * @param the Client
     * @return all the deliveries of a Client
     */
    public List<DeliveryPK> allDeliveryOfClient(final ClientPK c) {
        ShoppingTable shtbl = new ShoppingTable(conn);
        List<ShoppingPK> listShopping = shtbl.allShoppingOfClient(c);
        List<DeliveryPK> listDelivery = new ArrayList<>();
        for (ShoppingPK elem : listShopping) {
            var t = this.deliveryOfShopping(elem);
            if (t.isPresent()) {
                listDelivery.add(t.get());
            }
        }
        return listDelivery;
    }

    /**
     * @param the Client
     * @return all the deliveries of a Client that are not yet sent
     */
    public List<DeliveryPK> allDeliveryOfClientUnsent(final ClientPK c) {
        List<DeliveryPK> listDelivery = this.allDeliveryOfClient(c);
        return listDelivery.stream()
                .filter(l -> l.getCodCorriere().isEmpty() && l.getDate().isEmpty() && l.getTarga().isEmpty())
                .collect(Collectors.toUnmodifiableList());

    }

    /**
     * @param the Shopping
     * @return the Delivery of the Shopping
     */
    public Optional<DeliveryPK> deliveryOfShopping(final ShoppingPK s) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE Cod_spesa=?";
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, s.getCodSpesa());
            var result = statement.executeQuery();
            return convertResultSet(result).stream().findFirst();
        } catch (final SQLException e) {
            return Optional.empty();
        }

    }

    /**
     * @param the Courier
     * @return all the deliveries of a Courier
     */
    public List<DeliveryPK> allDeliveryOfCourier(final CourierPK c) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE Cod_corriere=?";
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, c.getCod_corriere());
            var result = statement.executeQuery();
            return convertResultSet(result);
        } catch (final SQLException e) {
            return List.of();
        }
    }

    /**
     * @param the Courier
     * @return all the deliveries of a Courier
     */
    public Optional<ClientPK> getClient(final DeliveryPK d) {
        ShoppingTable s = new ShoppingTable(conn);
        ClientTable c = new ClientTable(conn);
        var app = s.findByPrimaryKey(d.getCod_Consegna());
        if (app.isPresent()) {
            var tmp = c.findByPrimaryKey(app.get().getCodCliente());
            if (tmp.isPresent()) {
                return tmp;
            }
        }
        return Optional.empty();
    }

}
