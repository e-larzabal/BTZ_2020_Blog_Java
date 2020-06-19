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

    /*
     * List of article's tags
     */
    @Autowired
    private AppConfig config;

    @Override
    public Tag findById(Long id) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection(config.mysql);
            statement = connection.prepareStatement("SELECT id, tagName, color " + " FROM tag " + " WHERE id = ? ");

            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            Tag tag = new Tag();

            if (resultSet.next()) {

                // Read the tag
                String tagName = resultSet.getString("tagName");
                Color color = new Color(resultSet.getInt("color"));

                tag.setId(id);
                tag.setTagName(tagName);
                tag.setColor(color);
            }

            return tag;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }

        return null;
    }

    @Override
    public List<Tag> findAll() {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection(config.mysql);
            statement = connection
                    .prepareStatement("SELECT id, tagName, color " + "  FROM tag " + "  ORDER BY tagName DESC ; ");
            resultSet = statement.executeQuery();

            List<Tag> tags = new ArrayList<>();

            while (resultSet.next()) {

                // Read the article
                Long id = resultSet.getLong("id");
                String tagName = resultSet.getString("tagName");
                Color color = new Color(resultSet.getInt("color"));

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

    @Override
    public void deleteById(Long id) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection(config.mysql);
            statement = connection.prepareStatement("DELETE FROM tag WHERE id = ? ");
            statement.setLong(1, id);

            if (statement.executeUpdate() != 1) {
                throw new SQLException("failed to delete data in tag table");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }

    }

    @Override
    public List<Tag> findAllInArticle(Long id) {
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

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        try {

            connection = JdbcUtils.getConnection(config.mysql);
            statement = connection.prepareStatement("INSERT INTO tag (tagName, color) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, tag.getTagName());
            statement.setInt(2, tag.getColor().getRGB());

            if (statement.executeUpdate() != 1) {
                throw new SQLException("failed to insert data into tag table");
            }

            generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(1);
                tag.setId(id);
                return tag;
            } else {
                throw new SQLException("failed to get inserted id of tag table");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(generatedKeys);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }

        return null;
    }

    @Override
    public Tag update(Tag tag) {

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = JdbcUtils.getConnection(config.mysql);
            statement = connection.prepareStatement("UPDATE tag SET tagName=?, color=? WHERE id=?");
            statement.setString(1, tag.getTagName());
            statement.setInt(2, tag.getColor().getRGB());
            statement.setLong(3, tag.getId());

            if (statement.executeUpdate() != 1) {
                throw new SQLException("failed to update data into tag table");
            }
            return tag;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }

        return null;
    }

}
