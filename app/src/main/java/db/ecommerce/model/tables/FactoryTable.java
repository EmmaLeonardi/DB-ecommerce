package db.ecommerce.model.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import db.ecommerce.model.Factory;
import db.ecommerce.model.FactoryManagementPK;
import db.ecommerce.model.FactoryPK;
import db.ecommerce.model.ProducerPK;

public class FactoryTable implements Table<FactoryPK, Integer> {

    private final Connection conn;
    private final static String TABLE_NAME = "FABBRICHE";

    /**
     * @param conn
     */
    public FactoryTable(Connection conn) {
        this.conn = conn;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public boolean createTable() {
        final String query = "CREATE TABLE" + TABLE_NAME + "(" + "Cod_fabbrica int not null auto_increment,"
                + "Via varchar(50) not null," + "Numero_civ varchar(10) not null," + "Citta varchar(25) not null,"
                + "constraint IDFABBRICA_ID primary key (Cod_fabbrica));";
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
    public Optional<FactoryPK> findByPrimaryKey(Integer primaryKey) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE Cod_fabbrica=?";
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, primaryKey);
            var result = statement.executeQuery();
            return convertResultSet(result).stream().findFirst();
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    private List<FactoryPK> convertResultSet(ResultSet result) {
        final List<FactoryPK> list = new ArrayList<>();
        if (result != null) {
            try {
                while (result.next()) {
                    list.add(new FactoryPK(result.getString("Via"), result.getInt("Numero_civ"),
                            result.getString("Citta"), result.getInt("Cod_fabbrica")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<FactoryPK> findAll() {
        final String query = "SELECT * FROM " + TABLE_NAME;
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            var result = statement.executeQuery();
            return convertResultSet(result);
        } catch (final SQLException e) {
            return List.of();
        }
    }

    public Optional<FactoryPK> save(Factory value) {
        final String query = "INSERT INTO " + TABLE_NAME + " (Via, Numero_civ, Citta) VALUES (?,?,?)";

        try (final PreparedStatement statement = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, value.getVia());
            statement.setInt(2, value.getnCiv());
            statement.setString(3, value.getCitta());
            final var r = statement.executeUpdate();
            if (r == 1) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return Optional.ofNullable(new FactoryPK(value, generatedKeys.getInt(1)));
                    }
                }
            }
        } catch (final SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public Optional<FactoryPK> save(FactoryPK value) {
        return save(FactoryPK.convertToFactory(value));
    }

    @Override
    public boolean update(FactoryPK value) {
        final String query = "UPDATE " + TABLE_NAME + " SET Via=?, Numero_civ=?, Citta=? WHERE Cod_fabbrica=?";

        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setString(1, value.getVia());
            statement.setInt(2, value.getnCiv());
            statement.setString(3, value.getCitta());
            statement.setInt(4, value.getCodFabbrica());
            final var r = statement.executeUpdate();
            return r == 1;
        } catch (final SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Integer primaryKey) {
        final String query = "DELETE FROM " + TABLE_NAME + " WHERE Cod_fabbrica=?";

        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, primaryKey);
            final var r = statement.executeUpdate();
            return r == 1;
        } catch (final SQLException e) {
            return false;
        }
    }

    public List<FactoryPK> allFactoryOfProducer(ProducerPK p) {
        var fcm = new FactoryManagementTable(conn);
        List<FactoryManagementPK> list = fcm.allFactoryManagementOfProducer(p);
        List<FactoryPK> l = new ArrayList<>();
        for (var elem : list) {
            var tmp = this.findByPrimaryKey(elem.getCodFabbrica());
            if (tmp.isPresent()) {
                l.add(tmp.get());
            }
        }
        return l;
    }

}
