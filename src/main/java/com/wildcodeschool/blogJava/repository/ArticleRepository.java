package com.wildcodeschool.blogJava.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wildcodeschool.blogJava.config.AppConfig;
import com.wildcodeschool.blogJava.dao.UserDao;
import com.wildcodeschool.blogJava.dao.CrudDao;
import com.wildcodeschool.blogJava.dao.TagDao;
import com.wildcodeschool.blogJava.model.Article;
import com.wildcodeschool.blogJava.model.Tag;
import com.wildcodeschool.blogJava.model.User;
import com.wildcodeschool.blogJava.util.JdbcUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepository implements CrudDao<Article> {

    // private final static String DB_URL =
    // "jdbc:mysql://captain.javarover.wilders.dev:33306/BLOG_JAVA?serverTimezone=GMT";
    // private final static String DB_USER = "root";
    // private final static String DB_PASSWORD = "egh5ohCuey0o";

    @Autowired
    private AppConfig config;
    private UserDao userDao;
    private TagDao tagDao;


    @Override
    public List<Article> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = JdbcUtils.getConnection(config.mysql);
            statement = connection.prepareStatement("SELECT a.id, a.title, a.content, a.image, a.published, a.id_user  "
                    + "  FROM article AS a                                                  "
                    + "  ORDER BY published DESC ;                                          ");
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
    public Article save(Article entity) {
        // TODO Auto-generated method stub

        try {

        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            // JdbcUtils.closeResultSet(resultSet);
            // JdbcUtils.closeStatement(statement);
            // JdbcUtils.closeConnection(connection);
            // }
        }

        return null;
    }

    @Override
    public Article findById(Long id) {

        try {

        } catch (Exception e) {

        } finally {
            // JdbcUtils.closeResultSet(resultSet);
            // JdbcUtils.closeStatement(statement);
            // JdbcUtils.closeConnection(connection);
        }
        return null;

    }

    @Override
    public Article update(Article entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub

    }

}