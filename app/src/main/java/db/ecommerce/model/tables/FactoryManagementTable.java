package db.ecommerce.model.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import db.ecommerce.model.FactoryManagement;
import db.ecommerce.model.FactoryManagementPK;
import db.ecommerce.model.FactoryPK;
import db.ecommerce.model.ProducerPK;
import db.ecommerce.utils.DateConverter;

public class FactoryManagementTable implements Table<FactoryManagementPK, Integer> {

    private final Connection conn;
    private final static String TABLE_NAME = "GESTIONE_FABBRICHE";

    /**
     * @param conn
     */
    public FactoryManagementTable(Connection conn) {
        this.conn = conn;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public boolean createTable() {
        final String query = "CREATE TABLE" + TABLE_NAME + "(" + "Cod_gestione bigint not null auto_increment,"
                + "Cod_fabbrica int not null," + "Data_inizio date not null," + "Data_fine date,"
                + "Cod_produttore int not null," + "constraint IDGESTISTIONE_FABBRICA primary key (Cod_gestione))";
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
    public Optional<FactoryManagementPK> findByPrimaryKey(Integer primaryKey) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE Cod_gestione=?";
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, primaryKey);
            var result = statement.executeQuery();
            return convertResultSet(result).stream().findFirst();
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    private List<FactoryManagementPK> convertResultSet(ResultSet result) {
        final List<FactoryManagementPK> list = new ArrayList<>();
        if (result != null) {
            try {
                while (result.next()) {
                    list.add(new FactoryManagementPK(result.getInt("Cod_fabbrica"),
                            DateConverter.sqlDateToDate(result.getDate("Data_inizio")),
                            Optional.ofNullable(DateConverter.sqlDateToDate(result.getDate("Data_fine"))),
                            result.getInt("Cod_produttore"), result.getInt("Cod_gestione")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<FactoryManagementPK> findAll() {
        final String query = "SELECT * FROM " + TABLE_NAME;
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            var result = statement.executeQuery();
            return convertResultSet(result);
        } catch (final SQLException e) {
            return List.of();
        }
    }

    public Optional<FactoryManagementPK> save(FactoryManagement value) {
        final String query = "INSERT INTO " + TABLE_NAME
                + " (Cod_fabbrica, Data_inizio, Data_fine, Cod_produttore) VALUES (?,?,?,?)";

        try (final PreparedStatement statement = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, value.getCodFabbrica());
            statement.setDate(2, DateConverter.dateToSqlDate(value.getStart()));
            statement.setDate(3, DateConverter.dateToSqlDate(value.getEnd().isPresent() ? value.getEnd().get() : null));
            statement.setInt(4, value.getCodProduttore());
            final var r = statement.executeUpdate();
            if (r == 1) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return Optional.ofNullable(new FactoryManagementPK(value, generatedKeys.getInt(1)));
                    }
                }
            }
        } catch (final SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public Optional<FactoryManagementPK> save(FactoryManagementPK value) {
        return save(FactoryManagementPK.convertToFactoryManagement(value));
    }

    @Override
    public boolean update(FactoryManagementPK value) {
        final String query = "UPDATE " + TABLE_NAME
                + " SET Cod_fabbrica=?, Data_inizio=?, Data_fine=?, Cod_produttore=? WHERE Cod_gestione=?";

        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, value.getCodFabbrica());
            statement.setDate(2, DateConverter.dateToSqlDate(value.getStart()));
            statement.setDate(3, DateConverter.dateToSqlDate(value.getEnd().isPresent() ? value.getEnd().get() : null));
            statement.setInt(4, value.getCodProduttore());
            statement.setInt(5, value.getCodGestione());
            final var r = statement.executeUpdate();
            return r == 1;
        } catch (final SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Integer primaryKey) {
        final String query = "DELETE FROM " + TABLE_NAME + " WHERE Cod_gestione=?";

        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, primaryKey);
            final var r = statement.executeUpdate();
            return r == 1;
        } catch (final SQLException e) {
            return false;
        }
    }

    public List<FactoryManagementPK> allFactoryManagementOfProducer(ProducerPK p) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE Cod_produttore=?";
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, p.getCod_produttore());
            var result = statement.executeQuery();
            return convertResultSet(result);
        } catch (final SQLException e) {
            return List.of();
        }
    }

    private List<FactoryManagementPK> allFactoryManagementOfFactory(FactoryPK f) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE Cod_fabbrica=?";
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, f.getCodFabbrica());
            var result = statement.executeQuery();
            return convertResultSet(result);
        } catch (final SQLException e) {
            return List.of();
        }
    }

    /**
     * @param the        date min, the date max.
     * @param f          the factory being managed
     * @param management the management in question (necessary if the management is
     *                   being modified)
     * @return true if no other management is occurring in the timeline given
     *
     */
    public boolean isManagementLegal(Date min, Date max, FactoryPK f, FactoryManagementPK man) {
        List<FactoryManagementPK> allManagement = new ArrayList<>(this.allFactoryManagementOfFactory(f));
        if (man != null) {
            // Escludo la gestione stessa
            allManagement.remove(man);
        }
        if (max != null) {
            // Non è nullo, possono esserci gestioni dopo
            for (var elem : allManagement) {
                if (elem.getEnd().isPresent() && elem.getEnd().get().before(min)) {
                    // E' prima
                } else {
                    if (elem.getStart().after(max)) {
                        // E' dopo
                    } else {
                        // E' in mezzo
                        return false;
                    }
                }
            }
            return true;
        } else {
            // E' nullo, tutte le gestioni devono essere prima
            for (var elem : allManagement) {
                if (elem.getEnd().isPresent() && elem.getEnd().get().before(min)) {
                    // E' prima
                } else {
                    // E' dopo o in mezzo
                    return false;
                }
            }
            return true;
        }
    }

}
