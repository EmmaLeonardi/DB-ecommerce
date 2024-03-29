package db.ecommerce.model.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import db.ecommerce.model.ProductPK;
import db.ecommerce.model.SoldProduct;
import db.ecommerce.model.SoldProductPK;
import db.ecommerce.utils.DateConverter;
import db.ecommerce.utils.PRODUCTTYPE;

public class SoldProductTable implements Table<SoldProductPK, Integer> {

    private final Connection conn;
    private final static String TABLE_NAME = "PRODOTTI_IN_VENDITA";

    /**
     * @param conn
     */
    public SoldProductTable(Connection conn) {
        this.conn = conn;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public boolean createTable() {
        final String query = "CREATE TABLE" + TABLE_NAME + " (Cod_prodotto_vendita int not null auto_increment,"
                + "Prezzo decimal(6,2) not null," + "Data_inizio date not null," + "Data_fine date,"
                + "Tipo varchar(15) not null," + "Scadenza date," + "Taglia varchar(10)," + "Cod_prodotto int not null,"
                + "constraint IDPRODOTTO_IN_VENDITA primary key (Cod_prodotto_vendita))";
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
    public Optional<SoldProductPK> findByPrimaryKey(Integer primaryKey) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE Cod_prodotto_vendita=?";
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, primaryKey);
            var result = statement.executeQuery();
            return convertResultSet(result).stream().findFirst();
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    private List<SoldProductPK> convertResultSet(ResultSet result) {
        final List<SoldProductPK> list = new ArrayList<>();
        if (result != null) {
            try {
                while (result.next()) {
                    list.add(new SoldProductPK(result.getDouble("Prezzo"),
                            DateConverter.sqlDateToDate(result.getDate("Data_inizio")),
                            Optional.ofNullable(DateConverter.sqlDateToDate(result.getDate("Data_fine"))),
                            PRODUCTTYPE.convert(result.getString("Tipo")),
                            Optional.ofNullable(DateConverter.sqlDateToDate(result.getDate("Scadenza"))),
                            Optional.ofNullable(result.getString("Taglia")), result.getInt("Cod_prodotto"),
                            result.getInt("Cod_prodotto_vendita")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<SoldProductPK> findAll() {
        final String query = "SELECT * FROM " + TABLE_NAME;
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            var result = statement.executeQuery();
            return convertResultSet(result);
        } catch (final SQLException e) {
            return List.of();
        }
    }

    /**
     * Saves the SoldProduct into the db
     */
    public Optional<SoldProductPK> save(SoldProduct value) {
        final String query = "INSERT INTO " + TABLE_NAME
                + " (Prezzo, Data_inizio, Data_fine, Tipo, Scadenza, Taglia, Cod_prodotto) VALUES (?,?,?,?,?,?,?)";

        try (final PreparedStatement statement = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDouble(1, value.getPrice());
            statement.setDate(2, DateConverter.dateToSqlDate(value.getStart()));
            statement.setDate(3, DateConverter.dateToSqlDate(value.getEnd().isPresent()?value.getEnd().get():null));
            statement.setString(4, value.getType().name());
            statement.setDate(5, DateConverter
                    .dateToSqlDate(value.getExpiration().isPresent() ? value.getExpiration().get() : null));
            statement.setString(6, value.getSize().get());
            statement.setInt(7, value.getCodProduct());
            final var r = statement.executeUpdate();
            if (r == 1) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return Optional.ofNullable(new SoldProductPK(value, generatedKeys.getInt(1)));
                    }
                }
            }
        } catch (final SQLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc} Since the Cod_prodotto_vendita is a value generated by the
     * Database, all SoldProductPK will be cast to SoldProduct and
     * Cod_prodotto_vendita values will be ignored
     */
    @Override
    public Optional<SoldProductPK> save(SoldProductPK value) {
        return this.save(SoldProductPK.convertToSoldProduct(value));
    }

    @Override
    public boolean update(SoldProductPK value) {
        final String query = "UPDATE " + TABLE_NAME
                + " SET Prezzo=?, Data_inizio=?, Data_fine=?, Tipo=?, Scadenza=?, Taglia=?, Cod_prodotto=? WHERE Cod_prodotto_vendita=?";

        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setDouble(1, value.getPrice());
            statement.setDate(2, DateConverter.dateToSqlDate(value.getStart()));
            statement.setDate(3, DateConverter.dateToSqlDate(value.getEnd().get()));
            statement.setString(4, value.getType().name());
            statement.setDate(5, DateConverter.dateToSqlDate(value.getExpiration().get()));
            statement.setString(6, value.getSize().get());
            statement.setInt(7, value.getCodProduct());
            statement.setInt(8, value.getCodSoldProduct());
            final var r = statement.executeUpdate();
            return r == 1;
        } catch (final SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Integer primaryKey) {
        final String query = "DELETE FROM " + TABLE_NAME + " WHERE Cod_prodotto_vendita=?";

        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, primaryKey);
            final var r = statement.executeUpdate();
            return r == 1;
        } catch (final SQLException e) {
            return false;
        }
    }

    /**
     * @param the Product
     * @return all the Products versions sold
     */
    public List<SoldProductPK> allSoldProductsOfProduct(ProductPK p) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE Cod_prodotto=?";
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, p.getCod_prodotto());
            var result = statement.executeQuery();
            if (result != null) {
                return convertResultSet(result);
            } else {
                return List.of();
            }

        } catch (final SQLException e) {
            return List.of();
        }

    }

}
