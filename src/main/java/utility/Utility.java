package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Utility {
    private static Connection conInsta;

    private static Connection conAdni;

    public static Connection getInstaConnection() throws ClassNotFoundException, SQLException {
        if (conInsta == null) {
            Class.forName("mongodb.jdbc.MongoDriver");
            String url = "jdbc:mongo://ec2-3-137-209-245.us-east-2.compute.amazonaws.com/Instacart?authSource=admin";
            conInsta = DriverManager.getConnection(url, "AdminUser", "Admin@123");
        }
        return conInsta;
    }

    public static Connection getAdniConnection() throws ClassNotFoundException, SQLException {
        if (conAdni == null) {
            Class.forName("mongodb.jdbc.MongoDriver");
            String url = "jdbc:mongo://ec2-3-137-209-245.us-east-2.compute.amazonaws.com/AdnimergeDatabase?authSource=admin";
            conAdni = DriverManager.getConnection(url, "AdminUser", "Admin@123");
        }
        return conAdni;
    }
}
