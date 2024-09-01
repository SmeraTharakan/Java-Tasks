package com.companydb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FetchDataThread extends Thread {
    private String tableName;
    private final Object lock;

    public FetchDataThread(String tableName, Object lock) {
        this.tableName = tableName;
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) {
            try (Connection connection = DatabaseConnection.getConnection();
                 Statement statement = connection.createStatement()) {

                String query = "SELECT * FROM public." + tableName;
                ResultSet resultSet = statement.executeQuery(query);

                System.out.println("Data from table " + tableName + ":");

                int columnCount = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(resultSet.getMetaData().getColumnName(i) + "\t");
                }
                System.out.println();

                while (resultSet.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.print(resultSet.getObject(i) + "\t");
                    }
                    System.out.println();
                }
                System.out.println(); 

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
