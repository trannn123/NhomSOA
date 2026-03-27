package QLDTAN;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/ThucAnNhanhdb"
                    + "?useUnicode=true"
                    + "&characterEncoding=UTF-8"
                    + "&useSSL=false"
                    + "&serverTimezone=UTC";
            conn = DriverManager.getConnection(url, "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}