package com.wildcodeschool.blogJava.repository;

import com.wildcodeschool.blogJava.model.Tag;
import org.springframework.stereotype.Repository;
import util.JdbcUtils;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TagRepository {

    private final static String DB_URL = "jdbc:mysql://captain.javarover.wilders.dev:33306/BLOG_JAVA?serverTimezone=GMT";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "egh5ohCuey0o";

    /*
     * List of article's tags
     */
    public static List<Tag> findAllInArticle(Integer id_article) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            statement = connection.prepareStatement("SELECT id, tagName, color FROM tag "
                    + "LEFT JOIN article_has_tag aht ON tag.id = aht.id_article " + "WHERE id = ?;");
            statement.setLong(1, id_article);
            resultSet = statement.executeQuery();

            List<Tag> tags = new ArrayList<>();

            // TODO : fix color
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String tagName = resultSet.getString("tagName");
                Color color = null;// resultSet.getColor("color");

                tags.add(new Tag(id, tagName, color));
            }

            return tags;
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
