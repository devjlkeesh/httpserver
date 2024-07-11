package dev.jlkeesh.httpserver.todo;

import dev.jlkeesh.httpserver.config.DataSourceConfig;
import dev.jlkeesh.httpserver.config.SettingsConfig;
import dev.jlkeesh.httpserver.exception.DataAccessException;

import java.sql.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TodoDAO {
    private final Connection connection;
    private final String selectQuery = SettingsConfig.get("query.todo.select");
    private final String selectAllQuery = SettingsConfig.get("query.todo.select.all");
    private final String deleteQuery = SettingsConfig.get("query.todo.delete");
    private final String updateQuery = SettingsConfig.get("query.todo.update");
    private final String insertQuery = SettingsConfig.get("query.todo.insert");
    private final TodoRowMapper todoRowMapper = new TodoRowMapper();

    public TodoDAO() {
        this.connection = DataSourceConfig.getConnection();
    }

    public Optional<Todo> findById(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(todoRowMapper.toDomain(resultSet));
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
        return Optional.empty();
    }

    public Todo save(Todo todo) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, todo.getTitle());
            preparedStatement.setString(2, todo.getDescription());
            preparedStatement.setLong(3, todo.getUserId());
            preparedStatement.setBoolean(4, todo.isDone());
            preparedStatement.setString(5, todo.getPriority().name());
            preparedStatement.execute();
            return todo;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void deleteById(Long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public List<Todo> findAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAllQuery);
            return todoRowMapper.toDomainList(resultSet);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}
