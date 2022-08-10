package db.ecommerce.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProviderImpl implements ConnectionProvider {

    private final String username;
    private final String password;
    private final String dbName;

    /**
     * @param username
     * @param password
     * @param dbName
     */
    public ConnectionProviderImpl(final String username, final String password, final String dbName) {
        this.username = username;
        this.password = password;
        this.dbName = dbName;
    }

    /** {@inheritDoc} */
    
    @Override
    public Connection getMySQLConnection() {
        final String parameters = "jdbc:mysql://localhost:3306/" + this.dbName;
        try {
            return DriverManager.getConnection(parameters,this.username,this.password);
        } catch (SQLException e) {
            throw new IllegalStateException("Couldn't connect to the db");
        }
    }

}
