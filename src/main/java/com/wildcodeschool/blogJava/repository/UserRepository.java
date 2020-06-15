package com.wildcodeschool.blogJava.repository;

import com.wildcodeschool.blogJava.config.AppConfig;
import com.wildcodeschool.blogJava.model.User;
import org.springframework.stereotype.Repository;
import com.wildcodeschool.blogJava.util.JdbcUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;

@Repository
public class UserRepository {

    // private final static String DB_URL =
    // "jdbc:mysql://captain.javarover.wilders.dev:33306/BLOG_JAVA?serverTimezone=GMT";
    // private final static String DB_USER = "root";
    // private final static String DB_PASSWORD = "egh5ohCuey0o";
    @Autowired
    private static AppConfig config;

    /*
     * A user by his id
     */
    public static User findById(Long id) {

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
}
