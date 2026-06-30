package config;

public final class DatabaseConfig {
    public static final String HOST = "localhost";
    public static final int PORT = 3306;
    public static final String DATABASE_NAME = "inventaris_gudang";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    public static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    public static final String SERVER_URL = "jdbc:mysql://" + HOST + ":" + PORT
            + "?useSSL=false&serverTimezone=Asia/Jakarta";
    public static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE_NAME
            + "?useSSL=false&serverTimezone=Asia/Jakarta";

    private DatabaseConfig() {
    }
}
