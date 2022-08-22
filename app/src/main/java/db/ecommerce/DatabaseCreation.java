package db.ecommerce;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseCreation {

    private final Connection conn;
    private boolean wasCreated;

    public DatabaseCreation(String username, String password, String creation, String insert) throws SQLException {
        // Registering the Driver
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        // Getting the connection
        String mysqlUrl = "jdbc:mysql://localhost:3306/";
        this.conn = DriverManager.getConnection(mysqlUrl, username, password);
        // Creating the Statement
        Statement stmt = conn.createStatement();
        // Query to create a database
        String query = "CREATE database Ecommerce";
        // Executing the query
        try {
            stmt.execute(query);
            readFile(creation);
            readFile(insert);
            this.wasCreated=true;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1007) {
                // Database already exists error
                this.wasCreated=false;
            } else {
                // Altro errore
                this.wasCreated=false;
            }
        }
    }

    private void readFile(String path) throws SQLException {
        InputStream in = this.getClass().getResourceAsStream(path);
        Scanner s = new Scanner(in);
        s.useDelimiter("/\\*[\\s\\S]*?\\*/|--[^\\r\\n]*|;");

        Statement st = null;

        try {
            st = conn.createStatement();

            while (s.hasNext()) {
                String line = s.next().trim();

                if (!line.isEmpty()) {
                    st.execute(line);
                }
            }
        } finally {
            if (st != null)
                st.close();
            s.close();
        }
    }

    public boolean wasCreated() {
        return wasCreated;
    }

}
