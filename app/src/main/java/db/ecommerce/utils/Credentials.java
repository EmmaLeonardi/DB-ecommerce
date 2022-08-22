package db.ecommerce.utils;

public class Credentials {

    private static String username = null;
    private static String password = null;
    private static String dbName = "ecommerce";

    public Credentials(String username, String password) {
        Credentials.username = username;
        Credentials.password = password;
    }

    /**
     * @return the username
     */
    public static String getUsername() {
        return username;
    }

    /**
     * @return the password
     */
    public static String getPassword() {
        return password;
    }

    /**
     * @return the dbname
     */
    public static String getDbname() {
        return dbName;
    }

}
