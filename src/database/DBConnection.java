package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//vidhi(2510990452)
public class DBConnection {

    private static final String URL =
        "jdbc:mysql://localhost:3306/urgentis";

    private static final String USER = "root";

    private static final String PASSWORD = "vidhi";

    private static boolean messageShown = false;

    public static synchronized Connection getConnection() {

        try {

            Connection connection = DriverManager.getConnection(
                URL, USER, PASSWORD
            );

            if (!messageShown) {
                System.out.println("MySQL Connected Successfully!");
                messageShown = true;
            }

            return connection;

        } catch (SQLException e) {

            System.out.println("Database Connection Failed!");

            e.printStackTrace();

            return null;
        }
    }
}
