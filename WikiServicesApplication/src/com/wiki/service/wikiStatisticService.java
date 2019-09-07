package com.wiki.service;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.wiki.Articles.DBConnection;

@Path("/wordCount")
public class wikiStatisticService {
	
	@GET
	@Path("/{param}")
	@Produces(MediaType.TEXT_HTML)
	public String allAboutWordsCount(@PathParam("param") String param) throws SQLException{
		String output="";
		  DBConnection dbConnection= new DBConnection();

	 try{

          String result = "" ;
         if(param.equalsIgnoreCase("max"))
        	 result=Integer.toString(dbConnection.getMaximumWordCount());
         else if(param.equalsIgnoreCase("min"))
        	 result=Integer.toString(dbConnection.getMinimumWordCount()); 
         else if(param.equalsIgnoreCase("mid"))
        	 result= dbConnection.getMedianWordCount();
         else {
        	 result= " Not Found ";
         }
        	 
	         output ="<HTML><HEAD><meta charset='UTF-8'><TITLE>"+param+" Words Count </TITLE></HEAD><BODY><h1>"+param+" : </h1>"+result;
	        
	         output +="</BODY></HTML>";
	 }
	        
	 catch(Exception e){
		 e.printStackTrace();
	 }
	 finally{
		 if(dbConnection.getConn()!=null)
	         dbConnection.getConn().close();
	 
	 }
          
		return output;

		
	
		
	}
	}
