package hr.java.spring.boot.Example.test;

import java.sql.*;

public class SqlDatabaseConnection {

    public static void main(String[] args) {
        String connectionUrl =
                "jdbc:sqlserver://localhost:1433;"
                        + "database=hardware_web_shop;"
                        + "user=sa;"
                        + "password=SQL;"
                        + "encrypt=true;"
                        + "trustServerCertificate=true;"
                        + "loginTimeout=30;";

        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id, name, description, price, categoryId FROM dbo.Hardware");
        ) {

            while (resultSet.next()) {
                System.out.println(resultSet.getString("name") + " " + resultSet.getString("description"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
