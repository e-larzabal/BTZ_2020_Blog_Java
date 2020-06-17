package com.wildcodeschool.blogJava.repository;

import com.wildcodeschool.blogJava.config.AppConfig;
import com.wildcodeschool.blogJava.dao.TagDao;
import com.wildcodeschool.blogJava.model.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.wildcodeschool.blogJava.util.JdbcUtils;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TagRepository implements TagDao {

    // private final static String DB_URL =
    // "jdbc:mysql://captain.javarover.wilders.dev:33306/BLOG_JAVA?serverTimezone=GMT";
    // private final static String DB_USER = "root";
    // private final static String DB_PASSWORD = "egh5ohCuey0o";

    /*
     * List of article's tags
     */
    @Autowired
    private AppConfig config;

    @Override
    public Tag findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Tag> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Tag> findAllInArticle(Long id) {
        // TODO Auto-generated method stub
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection(config.mysql);
            statement = connection.prepareStatement("SELECT id, tagName, color FROM tag "
                    + "LEFT JOIN article_has_tag aht ON tag.id = aht.id_tag WHERE id_article = ? ;");
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            List<Tag> tags = new ArrayList<>();

            // TODO : fix color
            while (resultSet.next()) {
                Long idTag = resultSet.getLong("id");
                String tagName = resultSet.getString("tagName");
                Color color = new Color(resultSet.getInt("color"));

                tags.add(new Tag(idTag, tagName, color));
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

    @Override
    public Tag create(Tag tag) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Tag update(Tag tag) {
        // TODO Auto-generated method stub
        return null;
    }

}
