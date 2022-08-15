package db.ecommerce.model.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import db.ecommerce.model.Vehicle;
import db.ecommerce.model.VehicleImpl;

public class VehicleTable implements Table<Vehicle, String> {

    private final Connection conn;
    private final static String TABLE_NAME = "MEZZI";

    /**
     * @param conn
     */
    public VehicleTable(Connection conn) {
        this.conn = conn;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public boolean createTable() {
        final String query = "CREATE TABLE" + TABLE_NAME + "(" + "Targa varchar(15) not null,"
                + "Paese_immatricolazione varchar(25) not null," + "Marca varchar(25) not null,"
                + "Tipo_veicolo varchar(25) not null," + "constraint IDMEZZO primary key (Targa));";
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
    public Optional<Vehicle> findByPrimaryKey(String primaryKey) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE Targa=?";
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setString(1, primaryKey);
            var result = statement.executeQuery();
            return convertResultSet(result).stream().findFirst();
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    private List<Vehicle> convertResultSet(ResultSet result) {
        final List<Vehicle> list = new ArrayList<>();
        if (result != null) {
            try {
                while (result.next()) {
                    list.add(new VehicleImpl(result.getString("Targa"), result.getString("Paese_immatricolazione"),
                            result.getString("Marca"), result.getString("Tipo_veicolo")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<Vehicle> findAll() {
        final String query = "SELECT * FROM " + TABLE_NAME;
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            var result = statement.executeQuery();
            return convertResultSet(result);
        } catch (final SQLException e) {
            return List.of();
        }
    }

    @Override
    public Optional<Vehicle> save(Vehicle value) {
        final String query = "INSERT INTO " + TABLE_NAME
                + " (Targa, Paese_immatricolazione, Marca, Tipo_veicolo) VALUES (?,?,?,?)";

        try (final PreparedStatement statement = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, value.getTarga());
            statement.setString(2, value.getStato());
            statement.setString(3, value.getMarca());
            statement.setString(4, value.getTipo_veicolo());
            final var r = statement.executeUpdate();
            if (r == 1) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return Optional.ofNullable(value);
                    }
                }
            }
        } catch (final SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Vehicle value) {
        final String query = "UPDATE " + TABLE_NAME
                + " SET Paese_immatricolazione=?, Marca=?, Tipo_veicolo=? WHERE Targa=?";

        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setString(1, value.getStato());
            statement.setString(2, value.getMarca());
            statement.setString(3, value.getTipo_veicolo());
            statement.setString(4, value.getTarga());
            final var r = statement.executeUpdate();
            return r == 1;
        } catch (final SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(String primaryKey) {
        final String query = "DELETE FROM " + TABLE_NAME + " WHERE Targa=?";

        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setString(1, primaryKey);
            final var r = statement.executeUpdate();
            return r == 1;
        } catch (final SQLException e) {
            return false;
        }
    }

}
