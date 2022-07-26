import javax.swing.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

class DataBase {
    private Connection getConnection() throws SQLException {
        try {
            // uses sql library
            Class.forName("org.sqlite.JDBC");
            // creates a connection by getting a connection to the test using the library
            Connection conn = DriverManager.getConnection("jdbc:sqlite:Lib_DataBase.db");

            return conn;

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e);
            System.out.println("here1");
            return null;
        }

    }

    ResultSet search(String searchFor, JPanel panel) {
        PreparedStatement statement;
        Connection connection = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("select * from Bookshelf where Title = ? or Genre = ? or " +
                    "Author = ?");
            statement.setString(1, searchFor);
            statement.setString(2, searchFor);
            statement.setString(3, searchFor);
            return statement.executeQuery();

        } catch (SQLException e) {
            if (connection == null) {
                JOptionPane.showMessageDialog(panel, "Connection to database failed.");
            } else {
                JOptionPane.showMessageDialog(panel, "No results found for" + searchFor + " in the database.");
            }
            e.printStackTrace();
        }
        return null;
    }
}