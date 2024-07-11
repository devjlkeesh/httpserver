package dev.jlkeesh.httpserver;

import dev.jlkeesh.httpserver.todo.Priority;
import dev.jlkeesh.httpserver.todo.Todo;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class Application {
    public static void main(String[] args) throws IOException {
        ResourceBundle settings = ResourceBundle.getBundle("application");
        String insertTodoQuery = settings.getString("query.todo.insert");
        String selectTodoQuery = settings.getString("query.todo.select");
        String url = settings.getString("datasource.url");
        String username = settings.getString("datasource.username");
        String password = settings.getString("datasource.password");
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select version();");
            if (resultSet.next()){
                String string = resultSet.getString(1);
                System.out.println("string = " + string);
            }


            ResultSet selectResultSet = statement.executeQuery(selectTodoQuery);
            if (selectResultSet.next()){

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
