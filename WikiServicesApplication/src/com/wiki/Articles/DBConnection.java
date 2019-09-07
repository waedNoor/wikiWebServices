package com.wiki.Articles;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;


import com.wiki.common.Constants;


public class DBConnection {

	Connection conn = null;
	Statement stmt=null;
		   
		   public DBConnection() throws SQLException{
			   setConn(connect());
			   setStmt(conn.createStatement());
		   }
	
	private Connection connect() {
		
 		try {
			Class.forName(Constants.JDBC_DRIVER);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(Constants.DB_URL+Constants.DATABASE_NAME, Constants.USER_NAME, Constants.PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;

	}	
	
	public void save( ArticleEntity article) throws SQLException, UnsupportedEncodingException{
		setStmt(null);
		setStmt(getConn().createStatement());
		String sql = "INSERT INTO " +Constants.TABLE_NAME+
				  " (NS,TITLE,PAGEID,SIZE,WORDCOUNT,SNIPPET,TIMESTAMP) "+
                  "VALUES ("+article.getNs() +",'"+article.getTitle()+"',"+article.getPageid()+","+
				  article.getSize() +","+article.getWordcount()+",'"+(Base64.getEncoder().encodeToString(article.getSnippet().getBytes("UTF8")))+"','"+ article.getTimestamp()+"')";
		  try{
			  getStmt().executeUpdate(sql);
					  }
			     catch(SQLException se){
			         se.printStackTrace();
			      }catch(Exception e){
			         e.printStackTrace();
			      }finally{
			    	  getStmt().close();	
			      }
					
				

		
	}
	
	
	public List<ArticleEntity> getMatchedSnippet(String txt) throws SQLException, UnsupportedEncodingException{
		List<ArticleEntity> matchedSnippets= new ArrayList<ArticleEntity>();
		if(Constants.getCachedArticles().isEmpty())
			fetchAllArticles();
		txt=txt.toUpperCase();
		for(ArticleEntity eb : Constants.getCachedArticles()){
			if(eb.getSnippet().toUpperCase().contains(txt) || eb.getTitle().toUpperCase().contains(txt)){
				matchedSnippets.add(eb);
			}
				
		}
		return matchedSnippets;
		
	}

	private void fetchAllArticles() throws SQLException, UnsupportedEncodingException {
		if(Constants.getCachedArticles() != null && Constants.getCachedArticles().isEmpty()){
		ArrayList<ArticleEntity> articles= new ArrayList<>();
		String sql = "SELECT * from "+ Constants.TABLE_NAME ;
		ResultSet rs= stmt.executeQuery(sql);
		  while(rs.next()){
				ArticleEntity entity=new ArticleEntity();	

			  entity.setNs(rs.getInt(1));
		    	entity.setTitle(rs.getString(2));
		    	entity.setPageid(rs.getInt(3));
		    	entity.setSize(rs.getInt(4));
		    	entity.setWordcount(rs.getInt(5));
		    	entity.setSnippet(new String(Base64.getDecoder().decode(rs.getString(6)),"UTF-8"));
		    	entity.setTimestamp(rs.getString(7));
		    	articles.add(entity);
	  }
		  Constants.setCachedArticles(articles);  
		}
		stmt.close();


		
	}
	public int getMaximumWordCount() throws SQLException, UnsupportedEncodingException{
		
		  String sql = "SELECT  max([WORDCOUNT])+sum([WORDCOUNT]) from "+ Constants.TABLE_NAME;
		  ResultSet rs = stmt.executeQuery(sql);
		  int max=0;
			while (rs.next()) 
				max= rs.getInt(1);
			rs.close();
			stmt.close();
			
		  return max;
	}
	public int getMinimumWordCount() throws SQLException, UnsupportedEncodingException{
		
		  String sql =  "SELECT  min([WORDCOUNT])+sum([WORDCOUNT]) from "+ Constants.TABLE_NAME;
		  
		  ResultSet rs = stmt.executeQuery(sql);
		  int min=0;
			while (rs.next()) 
				min= rs.getInt(1);
			rs.close();
			stmt.close();
			
		  return min;
		 
		
	}  
	private int getTotalOdWordCounts() throws SQLException{
		int sum = 0;
	
		  String SumQuery =  "SELECT  SUM([WORDCOUNT])  from "+ Constants.TABLE_NAME;
		  ResultSet sumResultSet = stmt.executeQuery(SumQuery);
		  while(sumResultSet.next()){
			  sum= sumResultSet.getInt(1);
		  }
		  sumResultSet.close();
		return sum;
	}

	public String getMedianWordCount() throws SQLException, UnsupportedEncodingException{
		String midValue="";
		  int totalOfWordCounts= getTotalOdWordCounts();
		  System.out.println("total :"+totalOfWordCounts);

		  String sql =  "SELECT [WORDCOUNT]  from "+ Constants.TABLE_NAME;

		  ResultSet rs = stmt.executeQuery(sql);
		 
		  int mid=0;
		  List<Integer> wordCounts= new ArrayList<>();
			while (rs.next()) {
				 wordCounts.add(rs.getInt(1)+totalOfWordCounts );
			}
			  System.out.println("array before sort :"+wordCounts);

			Collections.sort(wordCounts);
			  System.out.println("array after sort :"+wordCounts);

			int midIndex=wordCounts.size()/2;
			if(wordCounts.size()%2 != 0){
				mid=wordCounts.get(midIndex);
				midValue=Integer.toString(mid);
			}else{
				midValue= Integer.toString(wordCounts.get(midIndex-1)) + " , "+ Integer.toString(wordCounts.get(midIndex))  ;
			}
				
			rs.close();
			stmt.close();
		  return midValue;
		 
		
	}  
	public Connection getConn() {
		return conn;
	}
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	public Statement getStmt() {
		return stmt;
	}
	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}
}
		   


