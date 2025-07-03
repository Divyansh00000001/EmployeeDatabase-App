
import java.sql.*;

public class DBConnection {
    public static Connection getConnection() throws SQLException{
        String url = "jdbc:mysql://localhost:3306/task7";
        String user = "root";
        String password = "ferrari458";

        return DriverManager.getConnection(url, user, password);
    }
}
