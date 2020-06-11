package com.wildcodeschool.blogJava.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wildcodeschool.blogJava.dao.ArticleDao;
import com.wildcodeschool.blogJava.model.Article;
import com.wildcodeschool.blogJava.model.User;

import org.springframework.stereotype.Repository;

import util.JdbcUtils;

@Repository
public class ArticleRepository implements ArticleDao {
    
    private final static String DB_URL = "jdbc:mysql://captain.javarover.wilders.dev:33306/BLOG_JAVA?serverTimezone=GMT";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "egh5ohCuey0o";

    @Override
    public List<Article> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = DriverManager.getConnection( DB_URL, DB_USER, DB_PASSWORD );
            statement = connection.prepareStatement(
                "SELECT a.id, a.title, a.content, a.image, a.published, a.id_user  " +
                "     , u.userName, u.firstName, u.lastName, t.tagName, t.color    " + 
                "  FROM article AS a LEFT JOIN user AS u ON a.id_user = u.id       " + 
                "  LEFT JOIN article_has_tag ON a.id = id_article                  " + 
                "  LEFT JOIN tag as t on id_tag = t.id                             " + 
                "  ORDER BY published DESC ;                                       "
        );
            resultSet = statement.executeQuery();

            List<Article> articles = new ArrayList<>();

        while (resultSet.next()) {

            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            String content = resultSet.getString("content");
            String image = resultSet.getString("image");
            Date published = resultSet.getDate("published");

            User user = new User(resultSet.getInt("id_user"), 
                                 resultSet.getString("userName"), 
                                 resultSet.getString("firstName"),
                                 resultSet.getString("lastName") );

//             [ |  article 1 | User 1 ...  |  Tag 1 | Tag Colors 1 ]
//             [ |  article 1 | User 1 ...  |  Tag 2 | Tag Colors 2 ]
//             [ |  article 1 | User 1 ...  |  Tag 3 | Tag Colors 3 ]

//             [ |  article 2 | User 2 ...  |  Tag 1 | Tag Colors 1 ]
//             [ |  article 2 | User 2 ...  |  Tag 2 | Tag Colors 2 ]

//             [ |  article 3 | User 1 ...  |  Tag 1 | Tag Colors  ]
//             


//             [ |  article 1 | User 1 ...  |  Tag 1 + '|' + Tag 2 + '|' + Tag 3 TAGS  | Tag Color 1 + '|' + Tag Color 2 + '|' + Tag COlor 3 TAGcolors
//             [ |  article 2 | User 2 ...  |  Tag 1 + '|' + Tag 2                     | Tag Color 1 + '|' + Tag Color 2                     TAGcolors
//             [ |  article 3 | User 1 ...  |  Tag 1                                   | Tag Color 1                                         TAGcolors

            for(String tag : resultSet.getString("TAGS").split("|") ) {

            }
                 

            //articles.add(new Article(id, title, content, image, published, listTag));
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
    public Article save(Article entity) {
        // TODO Auto-generated method stub

        try {
            
        } catch (Exception e) {
            //TODO: handle exception
        } finally {
            JdbcUtils.closeResultSet(generatedKeys);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }

        return null;
    }

    @Override
    public Article findById(Integer id) {

        try {
            
        } catch (Exception e) {
            
        } finally {
            JdbcUtils.closeResultSet(generatedKeys);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }


    @Override
    public Article update(Article entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        // TODO Auto-generated method stub

    }
    
}