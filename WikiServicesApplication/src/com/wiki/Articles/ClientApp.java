package com.wiki.Articles;

import java.sql.SQLException;

import com.wiki.Articles.WikiApi;
public class ClientApp {

	public static void main(String[] args) throws SQLException {
    	WikiApi.getInstance().callWikiApi();

	}

}
