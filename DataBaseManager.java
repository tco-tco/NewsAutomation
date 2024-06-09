import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;



public class DataBaseManager {
    public String selectedIl;
    public String databaseUrl;
    
    
    public Connection getConnection(DBInfo dBInfo) throws SQLException{
        return DriverManager.getConnection(dBInfo.getDbUrl(), dBInfo.getUsername(), dBInfo.getPassword());    
    }
    public void createDB(DBInfo dbInfo, String selectedIl){
        try(Connection connection = DriverManager.getConnection(dbInfo.getDbUrl(), dbInfo.getUsername(), dbInfo.getPassword());
        Statement statement = connection.createStatement();){
            String sql = "CREATE DATABASE " + selectedIl;
            statement.executeUpdate(sql);
        
        }catch(SQLException exception){
            //exception.printStackTrace();
            System.out.println(exception);

        }
    }
    public static void createTablePartial(DBInfo dbInfo, String databaseUrl, String tableName) {
        try (Connection connection = DriverManager.getConnection(databaseUrl, dbInfo.getUsername(), dbInfo.getPassword());
                Statement statement = connection.createStatement()) {

            statement.executeUpdate("CREATE TABLE " + tableName + "(ID INT PRIMARY KEY AUTO_INCREMENT, Haber_Baslıgı VARCHAR(250), Haber_Tarihi VARCHAR(15), Gösterim_Sayısı INT(4), Haber_Text MEDIUMTEXT, URL MEDIUMTEXT, Haber_URL_No INT(4), IMG_URL MEDIUMTEXT, Denetim_Kelime_Sayısı INT(10), Hata VARCHAR(25))");

        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void createTableAll(DBInfo dbInfo, String databaseUrl,String tableName) {
        try (Connection connection = DriverManager.getConnection(databaseUrl, dbInfo.getUsername(), dbInfo.getPassword());
                Statement statement = connection.createStatement()) {

            statement.executeUpdate("CREATE TABLE " + tableName + "(ID INT PRIMARY KEY AUTO_INCREMENT, Haber_Baslıgı VARCHAR(250), Haber_Tarihi VARCHAR(15), Gösterim_Sayısı INT(4), URL MEDIUMTEXT, Haber_URL_No INT(4), IMG_URL MEDIUMTEXT, Hata VARCHAR(25))");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void insertValuePartial(DBInfo dbInfo, String databaseUrl,String tableName, String haberBasligi, String haberTarihi, int gosterimSayisi, String haberText, String url, int haberUrlNo, String imgUrl, int wordCounter, String hata) {
    String sql = "INSERT INTO " + tableName + " (Haber_Baslıgı, Haber_Tarihi, Gösterim_Sayısı, Haber_Text, URL, Haber_URL_No, IMG_URL, Denetim_Kelime_Sayısı, Hata) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection connection = DriverManager.getConnection(databaseUrl, dbInfo.getUsername(), dbInfo.getPassword());
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


        preparedStatement.setString(1, haberBasligi);
        preparedStatement.setString(2, haberTarihi);
        preparedStatement.setInt(3, gosterimSayisi);
        preparedStatement.setString(4, haberText);
        preparedStatement.setString(5, url);
        preparedStatement.setInt(6, haberUrlNo);
        preparedStatement.setString(7, imgUrl);
        preparedStatement.setInt(8, wordCounter);
        preparedStatement.setString(9, hata);


        preparedStatement.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
    }
    public static void insertValueAll(DBInfo dbInfo, String databaseUrl,String tableName, String haberBasligi, String haberTarihi, int gosterimSayisi, String url, int haberUrlNo, String imgUrl,String hata) {
    String sql = "INSERT INTO " + tableName + " (Haber_Baslıgı, Haber_Tarihi, Gösterim_Sayısı, URL, Haber_URL_No, IMG_URL, Hata) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection connection = DriverManager.getConnection(databaseUrl, dbInfo.getUsername(), dbInfo.getPassword());
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


        preparedStatement.setString(1, haberBasligi);
        preparedStatement.setString(2, haberTarihi);
        preparedStatement.setInt(3, gosterimSayisi);
        preparedStatement.setString(4, url);
        preparedStatement.setInt(5, haberUrlNo);
        preparedStatement.setString(6, imgUrl);
        preparedStatement.setString(7, hata);


        preparedStatement.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
    }
    
}
