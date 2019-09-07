package com.wiki.common;

import java.util.ArrayList;
import java.util.List;

import com.wiki.Articles.ArticleEntity;

public class Constants {
	// JDBC driver name and database URL
	public static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static final String DB_URL = "jdbc:sqlserver://DESKTOP-DFKBIP9\\SQLEXPRESS;databaseName=";

	// Database credentials
	public static final String USER_NAME = "sa";
	public static final String PASSWORD = "root1234";
	
	public static final String DATABASE_NAME = "wiki_db";
	public static final String TABLE_NAME = "[wiki_db].[dbo].[WIKI_ARTICLES]";
	public static final String WIKI_END_POINT = "https://en.wikipedia.org/w/api.php";
	public static final String WIKI_SEARCH_CRITIRIA = "Amman,Jordan";
	public static List<ArticleEntity> CachedArticles = new ArrayList<ArticleEntity>();
	public static List<ArticleEntity> getCachedArticles() {
		return CachedArticles;
	}
	public static void setCachedArticles(List<ArticleEntity> cachedArticles) {
		CachedArticles = cachedArticles;
	}
	

	
	
	
	
}
