package com.wildcodeschool.blogJava.repository;

import com.wildcodeschool.blogJava.config.AppConfig;
import com.wildcodeschool.blogJava.dao.ArticleDao;
import com.wildcodeschool.blogJava.dao.TagDao;
import com.wildcodeschool.blogJava.dao.UserDao;
import com.wildcodeschool.blogJava.model.Article;
import com.wildcodeschool.blogJava.model.Tag;
import com.wildcodeschool.blogJava.model.User;
import com.wildcodeschool.blogJava.util.JdbcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.awt.*;
import java.sql.*;
import java.util.Date;
import java.util.*;
import java.util.List;

@Repository
public class ArticleRepository implements ArticleDao {

    @Autowired
    private AppConfig config;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TagDao tagDao;

    @Override
    public List<Article> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection(config.mysql);

            String sql = "SELECT * FROM article as a" +
                    "JOIN article_has_tag on article_has_tag.id_article = a.id" +
                    "JOIN tag as t on article_has_tag.id_tag = t.id" +
                    "ORDER BY published DESC";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            Map<Long, Article> articles = new HashMap<>();
            Map<Long, Tag> tags = new HashMap<>();

            while (resultSet.next()) {


                // article
                Long idArticle = resultSet.getLong("id_article");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                String image = resultSet.getString("image");
                Date published = resultSet.getDate("published");

                // tag
                Long idTag = resultSet.getLong("id_tag");
                String name = resultSet.getString("tagName");
                Integer color = resultSet.getInt("color");

                // get object instances
                if (!articles.containsKey(idArticle)) {
                    articles.put(idArticle, new Article());
                }
                Article article = articles.get(idArticle);

                if (!tags.containsKey(idTag)) {
                    tags.put(idTag, new Tag());
                }
                Tag tag = tags.get(idTag);

                // fill them
                article.setId(idArticle);
                article.setTitle(title);
                article.setContent(content);
                article.setImage(image);
                article.setPublished(published);
                article.addTag(tag);
                tag.setId(idTag);
                tag.setColor(new Color(color));
                tag.setTagName(name);

                // user
                Long idUser = resultSet.getLong("id_user");
                User user = userDao.findById(idUser);
                article.setUser(user);

            }

            return new ArrayList<>(articles.values());

        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "findAllFailed", e);
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
    }


    public List<Article> findAllOld() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection(config.mysql);
            statement = connection.prepareStatement("SELECT id, title, content, image, published, id_user  "
                    + "  FROM article " + "  ORDER BY published DESC ; ");
            resultSet = statement.executeQuery();

            List<Article> articles = new ArrayList<>();

            while (resultSet.next()) {

                // Read the article
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                String image = resultSet.getString("image");
                Date published = resultSet.getDate("published");
                Long id_user = resultSet.getLong("id_user");

                // Read the article's author
                User user = userDao.findById(id_user);

                // Read the tag list of the article
                List<Tag> tags = null;
                tags = tagDao.findAllInArticle(id);

                articles.add(new Article(id, title, content, image, published, user, tags));

            }

            return articles;

        } catch (SQLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "findAllFailed", e);
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
    }
    @Override
    public Article findById(Long id) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection(config.mysql);
            statement = connection.prepareStatement(
                    "SELECT id, title, content, image, published, id_user " + " FROM article " + " WHERE id = ? ");
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            Article article = new Article();

            if (resultSet.next()) {

                // Read the article
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                String image = resultSet.getString("image");
                Date published = resultSet.getDate("published");
                Long id_user = resultSet.getLong("id_user");

                // Read the article's author
                User user = new User();
                user = userDao.findById(id_user);

                // Read the tag list of the article
                List<Tag> tags = new ArrayList<>();
                tags = tagDao.findAllInArticle(id);

                article.setId(id);
                article.setTitle(title);
                article.setContent(content);
                article.setImage(image);
                article.setPublished(published);
                article.setUser(user);
                article.setListTag(tags);
            }

            return article;

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
    public List<Article> FindByIdTag(Long idTag) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection(config.mysql);
            statement = connection.prepareStatement("SELECT id, title, content, image, published, id_user  "
                    + "  FROM article LEFT JOIN article_has_tag ON id = id_article WHERE id_tag = ? "
                    + "  ORDER BY published DESC ; ");

            statement.setLong(1, idTag);

            resultSet = statement.executeQuery();

            List<Article> articles = new ArrayList<>();

            while (resultSet.next()) {

                // Read the article
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                String image = resultSet.getString("image");
                Date published = resultSet.getDate("published");
                Long id_user = resultSet.getLong("id_user");

                // Read the article's author
                User user = new User();
                user = userDao.findById(id_user);

                // Read the tag list of the article
                List<Tag> tags = new ArrayList<>();
                tags = tagDao.findAllInArticle(id);

                articles.add(new Article(id, title, content, image, published, user, tags));

            }

            return articles;

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
            statement = connection.prepareStatement("DELETE FROM article WHERE id = ?");
            statement.setLong(1, id);

            if (statement.executeUpdate() != 1) {
                throw new SQLException("failed to delete data in article table");
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
    public Article create(Article article) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        try {

            connection = JdbcUtils.getConnection(config.mysql);
            statement = connection.prepareStatement(
                    "INSERT INTO article (title, content, image, id_user) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, article.getTitle());
            statement.setString(2, article.getContent());
            statement.setString(3, article.getImage());
            statement.setLong(4, article.getUser().getId());

            if (statement.executeUpdate() != 1) {
                throw new SQLException("failed to insert data into article table");
            }

            generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(1);
                article.setId(id);
                return article;
            } else {
                throw new SQLException("failed to get inserted id of article");
            }

            //insert les id_article / id_tags

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
    public Article update(Article article) {

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = JdbcUtils.getConnection(config.mysql);
            statement = connection.prepareStatement("UPDATE article SET title= ?, content=?, image=?, id_user=? WHERE id=?");
            statement.setString(1, article.getTitle());
            statement.setString(2, article.getContent());
            statement.setString(3, article.getImage());
            statement.setLong(4, article.getUser().getId());
            statement.setLong(5, article.getId());

            if (statement.executeUpdate() != 1) {
                throw new SQLException("failed to update data into article");
            }
            return article;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }

        return null;
    }

    @Override
    public void addArticleTag(Long idArticle, Long idTag) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection(config.mysql);
            statement = connection.prepareStatement("INSERT INTO article_has_tag (id_article, id_tag) VALUES (?, ?);");
            statement.setLong(1, idArticle);
            statement.setLong(2, idTag);

            if (statement.executeUpdate() != 1) {
                throw new SQLException("failed to insert data");
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
    public void delAllArticleTag(Long idArticle) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection(config.mysql);
            statement = connection.prepareStatement("DELETE FROM article_has_tag WHERE id_article = ?");
            statement.setLong(1, idArticle);

            if (statement.executeUpdate() != 1) {
                throw new SQLException("failed to delete data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }

    }

}