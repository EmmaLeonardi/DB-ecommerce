package db.ecommerce.model.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import db.ecommerce.model.Client;
import db.ecommerce.model.ClientPK;
import db.ecommerce.utils.DateConverter;

public class ClientTable implements Table<ClientPK, Integer> {

    private final Connection conn;
    private final static String TABLE_NAME = "CLIENTI";

    /**
     * @param conn the connection to the db
     */
    public ClientTable(Connection conn) {
        this.conn = conn;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public boolean createTable() {
        final String query = "CREATE TABLE" + TABLE_NAME + "(" + "Cod_cliente int not null auto_increment,"
                + "Codice_fiscale char(16) not null," + "Nome varchar(20) not null," + "Cognome varchar(20) not null,"
                + "Data_di_nascita date not null," + "Email varchar(50) not null," + "Numero_di_telefono int not null,"
                + "Via_residenza varchar(50) not null," + "Numero_civ_residenza bigint not null,"
                + "Città_residenza varchar(50) not null," + "Coordinate_bancarie varchar(25) not null,"
                + "constraint IDCLIENTE primary key (Cod_cliente))";
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
    public Optional<ClientPK> findByPrimaryKey(Integer primaryKey) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE Cod_cliente=?";
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, primaryKey);
            var result = statement.executeQuery();
            return convertResultSet(result).stream().findFirst();
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    private List<ClientPK> convertResultSet(ResultSet result) {
        final List<ClientPK> list = new ArrayList<>();
        if (result != null) {
            try {
                while (result.next()) {
                    list.add(new ClientPK(result.getInt("Cod_cliente"), result.getString("Codice_fiscale"),
                            result.getString("Nome"), result.getString("Cognome"),
                            DateConverter.sqlDateToDate(result.getDate("Data_di_nascita")), result.getString("Email"),
                            result.getInt("Numero_di_telefono"), result.getString("Via_residenza"),
                            result.getInt("Numero_civ_residenza"), result.getString("Città_residenza"),
                            result.getString("Coordinate_bancarie")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<ClientPK> findAll() {
        final String query = "SELECT * FROM " + TABLE_NAME;
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            var result = statement.executeQuery();
            return convertResultSet(result);
        } catch (final SQLException e) {
            return List.of();
        }
    }

    /**
     * Saves the Client into the db
     */
    public boolean save(Client value) {
        final String query = "INSERT INTO " + TABLE_NAME + " (Codice_fiscale, Nome, Cognome,"
                + " Data_di_nascita, Email, Numero_di_telefono, Via_residenza, Numero_civ_residenza,"
                + " Città_residenza, Coordinate_bancarie) VALUES (?,?,?,?,?,?,?,?,?,?)";

        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setString(1, value.getCodFis());
            statement.setString(2, value.getName());
            statement.setDate(3, DateConverter.dateToSqlDate(value.getDateBirth()));
            statement.setString(4, value.getEmail());
            statement.setInt(5, value.getPhoneNumber());
            statement.setString(6, value.getResStreet());
            statement.setInt(7, value.getResNumber());
            statement.setString(8, value.getResCity());
            statement.setString(9, value.getBankInfo());
            final var r = statement.executeUpdate();
            return r == 1;
        } catch (final SQLException e) {
            return false;
        }
    }

    /**
     * {@inheritDoc} Since the Cod_client is a value generated by the Database, all
     * ClientPK will be cast to Client and Cod_client values will be ignored
     */
    @Override
    public boolean save(ClientPK value) {
        return this.save(ClientPK.convertToClient(value));
    }

    /**
     * This method return the last Cod client inserted into the db. This operation
     * is safe if the latest insertion was possibile and no other insertions were
     * made meanwhile this method was called
     * 
     * @returns the Cod Client or -1 if the db is empty
     */
    public int getLastClientSaved() {

        final String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY DESC";
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            var result = statement.executeQuery();
            if (result != null && convertResultSet(result).stream().findFirst().isPresent()) {
                return convertResultSet(result).stream().findFirst().get().getCod_cliente();
            } else {
                return -1;
            }

        } catch (final SQLException e) {
            return -1;
        }

    }

    @Override
    public boolean update(ClientPK value) {
        final String query = "UPDATE " + TABLE_NAME + " SET Codice_fiscale=?, Nome=?, Cognome=?,"
                + " Data_di_nascita=?, Email=?, Numero_di_telefono=?, Via_residenza=?, Numero_civ_residenza=?,"
                + " Città_residenza=?, Coordinate_bancarie=? WHERE Cod_cliente=?";

        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setString(1, value.getCodFis());
            statement.setString(2, value.getName());
            statement.setDate(3, DateConverter.dateToSqlDate(value.getDateBirth()));
            statement.setString(4, value.getEmail());
            statement.setInt(5, value.getPhoneNumber());
            statement.setString(6, value.getResStreet());
            statement.setInt(7, value.getResNumber());
            statement.setString(8, value.getResCity());
            statement.setString(9, value.getBankInfo());
            statement.setInt(10, value.getCod_cliente());
            final var r = statement.executeUpdate();
            return r == 1;
        } catch (final SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Integer primaryKey) {
        final String query = "DELETE FROM " + TABLE_NAME + " WHERE Cod_cliente=?";

        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, primaryKey);
            final var r = statement.executeUpdate();
            return r == 1;
        } catch (final SQLException e) {
            return false;
        }
    }

}
