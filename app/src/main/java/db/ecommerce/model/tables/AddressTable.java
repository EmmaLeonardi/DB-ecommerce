package db.ecommerce.model.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import db.ecommerce.model.Address;
import db.ecommerce.model.AddressPK;
import db.ecommerce.model.ClientPK;
import db.ecommerce.model.DeliveryPK;

public class AddressTable implements Table<AddressPK, Integer> {

    private final Connection conn;
    private final static String TABLE_NAME = "INDIRIZZI";

    /**
     * @param conn
     */
    public AddressTable(Connection conn) {

        this.conn = conn;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public boolean createTable() {
        final String query = "CREATE TABLE" + TABLE_NAME + " (Cod_indirizzo int not null auto_increment,"
                + "Via varchar(25) not null," + "Numero_civico int not null," + "Città varchar(25) not null,"
                + "CAP int not null," + "Provincia varchar(25) not null," + "Paese varchar(25) not null,"
                + "constraint IDINDIRIZZO primary key (Cod_indirizzo))";
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
    public Optional<AddressPK> findByPrimaryKey(Integer primaryKey) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE Cod_indirizzo=?";
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, primaryKey);
            var result = statement.executeQuery();
            return convertResultSet(result).stream().findFirst();
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    private List<AddressPK> convertResultSet(ResultSet result) {
        final List<AddressPK> list = new ArrayList<>();
        if (result != null) {
            try {
                while (result.next()) {
                    list.add(new AddressPK(result.getString("Via"), result.getInt("Numero_civico"),
                            result.getString("Città"), result.getInt("CAP"), result.getString("Provincia"),
                            result.getString("Paese"), result.getInt("Cod_indirizzo")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<AddressPK> findAll() {
        final String query = "SELECT * FROM " + TABLE_NAME;
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            var result = statement.executeQuery();
            return convertResultSet(result);
        } catch (final SQLException e) {
            return List.of();
        }
    }

    /**
     * Saves the Address into the db
     */
    public Optional<AddressPK> save(Address value) {
        final String query = "INSERT INTO " + TABLE_NAME + " (Via, Numero_civico, Città, CAP, Provincia, Paese)"
                + "VALUES (?,?,?,?,?,?);";
        try (final PreparedStatement statement = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, value.getRoad());
            statement.setInt(2, value.getnCiv());
            statement.setString(3, value.getCity());
            statement.setInt(4, value.getCAP());
            statement.setString(5, value.getRegion());
            statement.setString(6, value.getState());
            final var r = statement.executeUpdate();
            if (r == 1) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return Optional.ofNullable(new AddressPK(value, generatedKeys.getInt(1)));
                    }
                }
            }
        } catch (final SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc} Since the Cod_indirizzo is a value generated by the Database,
     * all AddressPK will be cast to Address and Cod_indirizzo values will be
     * ignored. The returned object is the one saved into the db
     */
    @Override
    public Optional<AddressPK> save(AddressPK value) {
        return this.save(AddressPK.convertToAddress(value));
    }

    @Override
    public boolean update(AddressPK value) {
        final String query = "UPDATE " + TABLE_NAME + "Via=?, Numero_civico=?, Città=?, CAP=?, Provincia=?, Paese=?"
                + " WHERE Cod_indirizzo=?";

        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setString(1, value.getRoad());
            statement.setInt(2, value.getnCiv());
            statement.setString(3, value.getCity());
            statement.setInt(4, value.getCAP());
            statement.setString(5, value.getRegion());
            statement.setString(6, value.getState());
            statement.setInt(7, value.getCodAddress());
            final var r = statement.executeUpdate();
            return r == 1;
        } catch (final SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Integer primaryKey) {
        final String query = "DELETE FROM " + TABLE_NAME + " WHERE Cod_indirizzo=?";

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
     * @return all the addresses of a Client
     */
    public List<AddressPK> allAddressOfClient(final ClientPK c) {
        List<AddressPK> l = new ArrayList<>();
        DeliveryTable dlvtbl = new DeliveryTable(conn);
        List<DeliveryPK> deliveryList = dlvtbl.allDeliveryOfClient(c);
        for (DeliveryPK elem : deliveryList) {
            var tmp = this.findByPrimaryKey(elem.getCodIndirizzo());
            if (tmp.isPresent()) {
                l.add(tmp.get());
            }
        }
        return l;
    }
    
    /**
     * @param the Client
     * @return all the distinct addresses of a Client
     */
    public List<AddressPK> allDistinctAddressOfClient(final ClientPK c) {
        final String query = "SELECT DISTINCT i.Cod_indirizzo, i.Via, i.Numero_civico, i.Città, i.CAP, i.Provincia, i.Paese FROM indirizzi i, consegne c, spese s WHERE c.Cod_indirizzo=i.Cod_indirizzo AND c.Cod_spesa=s.Cod_spesa AND s.Cod_cliente=?";
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, c.getCod_cliente());
            var result = statement.executeQuery();
            return convertResultSet(result);
        } catch (final SQLException e) {
            return List.of();
        }
    }

}
