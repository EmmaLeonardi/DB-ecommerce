package db.ecommerce.model.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import db.ecommerce.model.Contains;
import db.ecommerce.model.ContainsImpl;
import javafx.util.Pair;

/**
 * The first element of the pair is the Cod_prodotto_vendita, the second the
 * Cod_spesa
 */
public class ContainsTable implements Table<Contains, Pair<Integer, Integer>> {

    private final Connection conn;
    private final static String TABLE_NAME = "CONTENUTO";

    /**
     * @param conn the connection to the db
     */
    public ContainsTable(Connection conn) {
        this.conn = conn;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public boolean createTable() {
        final String query = "CREATE TABLE" + TABLE_NAME + "(Cod_spesa bigint not null auto_increment,)"
                + "Costo decimal(6,2) not null," + "Cod_cliente int not null,"
                + "constraint IDSPESA_ID primary key (Cod_spesa))";
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
    public Optional<Contains> findByPrimaryKey(Pair<Integer, Integer> primaryKey) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE Cod_prodotto_vendita=? AND Cod_spesa=?";
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, primaryKey.getKey());
            statement.setInt(2, primaryKey.getValue());
            var result = statement.executeQuery();
            return convertResultSet(result).stream().findFirst();
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    private List<Contains> convertResultSet(ResultSet result) {
        final List<Contains> list = new ArrayList<>();
        if (result != null) {
            try {
                while (result.next()) {
                    list.add(new ContainsImpl(result.getInt("Cod_prodotto_vendita"), result.getInt("Cod_spesa")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<Contains> findAll() {
        final String query = "SELECT * FROM " + TABLE_NAME;
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            var result = statement.executeQuery();
            return convertResultSet(result);
        } catch (final SQLException e) {
            return List.of();
        }
    }

    @Override
    public Optional<Contains> save(Contains value) {
        final String query = "INSERT INTO " + TABLE_NAME + " (Cod_prodotto_vendita, Cod_spesa) VALUES (?,?)";

        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, value.getCodProdotto());
            statement.setInt(2, value.getCodSpesa());
            final var r = statement.executeUpdate();
            if (r == 1) {
                return Optional.ofNullable(value);
            }
        } catch (final SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Contains updatedValue) {
        throw new IllegalStateException("Cannot change a record that has only primary keys");
    }

    @Override
    public boolean delete(Pair<Integer, Integer> primaryKey) {
        final String query = "DELETE FROM " + TABLE_NAME + " WHERE Cod_prodotto_vendita=? AND Cod_spesa=?";

        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, primaryKey.getKey());
            statement.setInt(1, primaryKey.getValue());
            final var r = statement.executeUpdate();
            return r == 1;
        } catch (final SQLException e) {
            return false;
        }
    }

}
