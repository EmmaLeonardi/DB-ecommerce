package db.ecommerce.model.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import db.ecommerce.model.Product;
import db.ecommerce.model.ProductPK;

public class ProductTable implements Table<ProductPK, Integer> {

    private final Connection conn;
    private final static String TABLE_NAME = "PRODOTTI";

    /**
     * @param conn
     */
    public ProductTable(Connection conn) {
        super();
        this.conn = conn;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public boolean createTable() {
        final String query = "CREATE TABLE" + TABLE_NAME + " (Cod_prodotto int not null auto_increment,"
                + "Materiale varchar(25) not null," + "Descrizione varchar(100) not null,"
                + "Cod_produttore int not null," + "Cod_fabbrica int not null,"
                + "constraint IDPRODOTTO primary key (Cod_prodotto))";
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
    public Optional<ProductPK> findByPrimaryKey(Integer primaryKey) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE Cod_prodotto=?";
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, primaryKey);
            var result = statement.executeQuery();
            return convertResultSet(result).stream().findFirst();
        } catch (final SQLException e) {
            return Optional.empty();
        }
    }

    private List<ProductPK> convertResultSet(ResultSet result) {
        final List<ProductPK> list = new ArrayList<>();
        if (result != null) {
            try {
                while (result.next()) {
                    list.add(new ProductPK(result.getString("Materiale"), result.getString("Descrizione"),
                            result.getInt("Cod_produttore"), result.getInt("Cod_fabbrica"),
                            result.getInt("Cod_prodotto")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<ProductPK> findAll() {
        final String query = "SELECT * FROM " + TABLE_NAME;
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            var result = statement.executeQuery();
            return convertResultSet(result);
        } catch (final SQLException e) {
            return List.of();
        }
    }

    /**
     * Saves the Product into the db
     */
    public boolean save(Product value) {
        final String query = "INSERT INTO (Materiale, Descrizione, Cod_produttore, Cod_fabbrica) " + TABLE_NAME
                + " VALUES (?,?,?,?)";

        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setString(1, value.getMateriale());
            statement.setString(2, value.getDescrizione());
            statement.setInt(3, value.getCod_produttore());
            statement.setInt(4, value.getCod_fabbrica());
            final var r = statement.executeUpdate();
            return r == 1;
        } catch (final SQLException e) {
            return false;
        }
    }

    /**
     * {@inheritDoc} Since the Cod_prodotto is a value generated by the Database,
     * all ProductPK will be cast to Product and Cod_prodotto values will be ignored
     */
    @Override
    public boolean save(ProductPK value) {
        return this.save(ProductPK.convertToProduct(value));
    }

    /**
     * This method returns the last Cod prodotto inserted into the db. This
     * operation is safe if the latest insertion was possibile and no other
     * insertions were made meanwhile this method was called
     * 
     * @returns the Cod prodotto or -1 if the db is empty
     */
    public int getLastProductSaved() {

        final String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY DESC";
        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            var result = statement.executeQuery();
            if (result != null && convertResultSet(result).stream().findFirst().isPresent()) {
                return convertResultSet(result).stream().findFirst().get().getCod_prodotto();
            } else {
                return -1;
            }

        } catch (final SQLException e) {
            return -1;
        }

    }

    @Override
    public boolean update(ProductPK value) {
        final String query = "UPDATE " + TABLE_NAME
                + " SET Materiale=?, Descrizione=?, Cod_produttore=?, Cod_fabbrica=? WHERE Cod_prodotto=?";

        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setString(1, value.getMateriale());
            statement.setString(2, value.getDescrizione());
            statement.setInt(3, value.getCod_produttore());
            statement.setInt(4, value.getCod_fabbrica());
            statement.setInt(5, value.getCod_prodotto());
            final var r = statement.executeUpdate();
            return r == 1;
        } catch (final SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Integer primaryKey) {
        final String query = "DELETE FROM " + TABLE_NAME + " WHERE Cod_prodotto=?";

        try (final PreparedStatement statement = this.conn.prepareStatement(query)) {
            statement.setInt(1, primaryKey);
            final var r = statement.executeUpdate();
            return r == 1;
        } catch (final SQLException e) {
            return false;
        }
    }

}
