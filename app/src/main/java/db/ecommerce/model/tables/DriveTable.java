package db.ecommerce.model.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import db.ecommerce.model.Drive;
import db.ecommerce.model.DrivePK;
import db.ecommerce.utils.ConvertTime;
import db.ecommerce.utils.DateConverter;

public class DriveTable implements Table<DrivePK, Integer> {

    private final Connection conn;
    private final static String TABLE_NAME = "MEZZI";

    /**
     * @param conn
     */
    public DriveTable(Connection conn) {
        this.conn = conn;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public boolean createTable() {
        final String query = "CREATE TABLE" + TABLE_NAME + "(" + "Cod_guida bigint not null auto_increment,"
                + "Data_inizio date not null," + "Data_fine date," + "Ora_inizio decimal(4,2) not null,"
                + "Ora_fine decimal(4,2)," + "Cod_corriere int not null," + "Targa varchar(15) not null,"
                + "constraint IDGUIDA primary key (Cod_guida));";
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
    public Optional<DrivePK> findByPrimaryKey(Integer primaryKey) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE Cod_guida=?";
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, primaryKey);
            var result = statement.executeQuery();
            return convertResultSet(result).stream().findFirst();
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    private List<DrivePK> convertResultSet(ResultSet result) {
        final List<DrivePK> list = new ArrayList<>();
        if (result != null) {
            try {
                while (result.next()) {
                    list.add(new DrivePK(
                            ConvertTime.convertIntoTime(DateConverter.sqlDateToDate(result.getDate("Data_inizio")),
                                    result.getDouble("Ora_inizio")),
                            Optional.ofNullable(ConvertTime.convertIntoTime(
                                    DateConverter.sqlDateToDate(result.getDate("Data_fine")),
                                    result.getDouble("Ora_fine"))),
                            result.getInt("Cod_corriere"), result.getString("Targa"), result.getInt("Cod_guida")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<DrivePK> findAll() {
        final String query = "SELECT * FROM " + TABLE_NAME;
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            var result = statement.executeQuery();
            return convertResultSet(result);
        } catch (final SQLException e) {
            return List.of();
        }
    }

    /**
     * {@inheritDoc} Since the Cod_guida is a value generated by the Database, all
     * DrivePK will be cast to Drive and Cod_guida values will be ignored
     */
    @Override
    public Optional<DrivePK> save(DrivePK value) {
        return save(DrivePK.convertToDrive(value));
    }

    /**
     * Saves the Drive into the db
     */
    public Optional<DrivePK> save(Drive value) {
        final String query = "INSERT INTO " + TABLE_NAME
                + " (Data_inizio, Data_fine, Ora_inizio, Ora_fine, Cod_corriere, Targa) VALUES (?,?,?,?,?)";

        try (final PreparedStatement statement = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, DateConverter.dateToSqlDate(value.getStart()));
            statement.setDate(2, DateConverter.dateToSqlDate(value.getEnd().get()));
            statement.setDouble(3, ConvertTime.convertIntoDouble(value.getStart()));
            statement.setDouble(4, ConvertTime.convertIntoDouble(value.getEnd().get()));
            statement.setInt(5, value.getCodCorriere());
            statement.setString(6, value.getTarga());
            final var r = statement.executeUpdate();
            if (r == 1) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return Optional.ofNullable(new DrivePK(value, generatedKeys.getInt(1)));
                    }
                }
            }
        } catch (final SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(DrivePK value) {
        final String query = "UPDATE " + TABLE_NAME
                + " SET Data_inizio=?, Data_fine=?, Ora_inizio=?, Ora_fine=?, Cod_corriere=?, Targa=? WHERE Cod_guida=?";

        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setDate(1, DateConverter.dateToSqlDate(value.getStart()));
            statement.setDate(2, DateConverter.dateToSqlDate(value.getEnd().get()));
            statement.setDouble(3, ConvertTime.convertIntoDouble(value.getStart()));
            statement.setDouble(4, ConvertTime.convertIntoDouble(value.getEnd().get()));
            statement.setInt(5, value.getCodCorriere());
            statement.setString(6, value.getTarga());
            statement.setInt(7, value.getCodDrive());
            final var r = statement.executeUpdate();
            return r == 1;
        } catch (final SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Integer primaryKey) {
        final String query = "DELETE FROM " + TABLE_NAME + " WHERE Cod_guida=?";

        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, primaryKey);
            final var r = statement.executeUpdate();
            return r == 1;
        } catch (final SQLException e) {
            return false;
        }

    }
}