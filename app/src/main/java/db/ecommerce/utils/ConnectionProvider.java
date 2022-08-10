package db.ecommerce.utils;

import java.sql.Connection;

/**
 * This interface defines an object that creates the connection to the mySQL
 * server
 */
public interface ConnectionProvider {

    /**
     * @throws IllegalStateException if couldn't connect
     */
    public Connection getMySQLConnection();

}
