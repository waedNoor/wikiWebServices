package com.wiki.service;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.wiki.Articles.ArticleEntity;
import com.wiki.Articles.DBConnection;

@Path("/search")
public class wikiService {
	
	@GET
	@Path("/find")
	@Produces(MediaType.TEXT_HTML)
	public String findArticlesByTitle(@QueryParam("query") String txt) throws SQLException, UnsupportedEncodingException{
		String output= "";

		DBConnection db= new DBConnection();
         List<ArticleEntity> results= db.getMatchedSnippet(txt);
         if(results !=null){
	         output ="<HTML><HEAD><meta charset='UTF-8'></HEAD><TITLE>Articles based on Title/Text : "+txt+"</TITLE><BODY>";
	         for(ArticleEntity eb: results){
	        	 output+="NS : "+eb.getNs()+"</br>";
	        	 output+="Title : "+eb.getTitle()+"</br>";
	        	 output+="Page Id : "+eb.getPageid()+"</br>";
	        	 output+="Size : "+eb.getSize()+"</br>"; 
	        	 output+="Word Count :  "+eb.getWordcount()+"</br>";
	        	 output+="Text : "+eb.getSnippet()+"</br>";
	        	 output+="Time Stamp : "+eb.getTimestamp()+"</br></br>";

	         }
	         if(results.size()==0){
	        	  output +="No Results Found";
	         }
	         output +="</BODY></HTML>";
         }
          
		return output;

		
	
		
	}
}
