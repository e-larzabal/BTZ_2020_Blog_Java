package com.wildcodeschool.blogJava.repository;

import com.wildcodeschool.blogJava.config.AppConfig;
import com.wildcodeschool.blogJava.dao.UserDao;
import com.wildcodeschool.blogJava.model.User;
import org.springframework.stereotype.Repository;
import com.wildcodeschool.blogJava.util.JdbcUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository implements UserDao {

    @Autowired
    private AppConfig config;

    @Override
    public User findById(Long id) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection(config.mysql);
            statement = connection.prepareStatement("SELECT id, userName, firstName, lastName FROM user WHERE id = ?;");
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            User user = new User();

            if (resultSet.next()) {
                String userName = resultSet.getString("userName");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");

                user.setId(id);
                user.userName(userName);
                user.firstName(firstName);
                user.lastName(lastName);
            }

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }

        return null;
    }

    @Override
    public List<User> findAll() {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection(config.mysql);
            statement = connection.prepareStatement("SELECT id, userName, firstName, lastName FROM user");
            resultSet = statement.executeQuery();

            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String userName = resultSet.getString("userName");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");

                users.add(new User(id, userName, firstName, lastName));
            }

            return users;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }

        return null;
    }

    @Override
    public void deleteById(Long id) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection(config.mysql);
            statement = connection.prepareStatement("DELETE FROM user WHERE id = ?;");
            statement.setLong(1, id);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
    }

    @Override
    public User create(User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection(config.mysql);
            statement = connection.prepareStatement("INSERT INTO user (userName, firstName, lastName) VALUES (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());

            resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                Long id = resultSet.getLong(1);
                user.setId(id);
                return user;
            } else {
                throw new SQLException("failed to get insert id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }

        return null;
    }

    @Override
    public User update(User entity) {
        // TODO Auto-generated method stub
        return null;
    }

}
