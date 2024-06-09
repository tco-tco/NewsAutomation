public class DBInfo {
    private String username="root";
    private String password="password";
    private String dbUrl = "localhost";
    private static String dbUrlWithDbName = "localhostt";
    
    

    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getDbUrl() {
        return dbUrl;
    }
    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }
    public String getDbUrlWithDbName() {
        return dbUrlWithDbName;
    }
    public static void setDbUrlWithDbName(String dbUrlWithDbName) {
        DBInfo.dbUrlWithDbName = dbUrlWithDbName;
    }
}