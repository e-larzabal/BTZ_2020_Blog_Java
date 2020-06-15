package com.wildcodeschool.blogJava.util;

import com.wildcodeschool.blogJava.config.MysqlConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class JdbcUtils {

	private final static Logger L = LoggerFactory.getLogger(JdbcUtils.class);

	public static Connection getConnection(MysqlConfig config) throws SQLException {
		return DriverManager.getConnection(config.url, config.username, config.password);
	}

	public static void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				L.error("failed to close connection", e);
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				L.error("failed to close result set", e);
			}
		}
	}

	public static void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				L.error("failed to close statement", e);
			}
		}
	}
}