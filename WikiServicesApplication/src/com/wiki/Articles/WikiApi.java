package com.wiki.Articles;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.wiki.common.Constants;

import org.json.JSONArray;
import java.sql.*;


public class WikiApi {

	private static WikiApi instance;
	private DBConnection dbConnection;
	
	private WikiApi(){}
	public static WikiApi getInstance(){
		
		if(instance==null)
			instance=new WikiApi(); 
		return instance;
	}
	
	public void callWikiApi() throws SQLException {
		System.out.print("callApi");
		String searchPage = Constants.WIKI_SEARCH_CRITIRIA;
		String endPoint= Constants.WIKI_END_POINT;

		Map<String, String> map= new HashMap<String, String>();
		map.put("action", "query");
		map.put("list", "search");
		map.put("srsearch", searchPage);
		map.put("utf8", "");
		map.put("format", "json");
		String url= endPoint.concat("?").concat(buildQuery(map));
        try{
           JSONObject json = readJsonFromUrl(url);
           JSONArray search =  json.getJSONObject("query").getJSONArray("search");
   		   dbConnection = new DBConnection();
           System.out.println("Connection is Done Successfully");
           for (int i = 0; i < search.length(); i++){
        	   ArticleEntity entityToSaved= fillArticleEntity(search.getJSONObject(i));
               dbConnection.save(entityToSaved);
          }
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        finally{
        	if(dbConnection.getConn()!=null)
        		dbConnection.getConn().close();
 }
 
	}
	
	private ArticleEntity fillArticleEntity(JSONObject obj){
		String title=obj.get("title").toString();
    	int ns=(Integer.parseInt(obj.get("ns").toString()));
    	String snippet=obj.get("snippet").toString();
    	String timestamp=obj.get("timestamp").toString();
    	int pageid=(Integer.parseInt(obj.get("pageid").toString()));
    	int size=(Integer.parseInt(obj.get("size").toString()));
    	int wordcount=(Integer.parseInt(obj.get("wordcount").toString()));
    	
    	ArticleEntity entity=new ArticleEntity();
    	entity.setNs(ns);
    	entity.setPageid(pageid);
    	entity.setSize(size);
    	entity.setSnippet(snippet);
    	entity.setTimestamp(timestamp);
    	entity.setWordcount(wordcount);
    	entity.setTitle(title);
    	return entity;
	}
	 private JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		    InputStream is = new URL(url).openStream();
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      String jsonText = readAll(rd);
		      JSONObject json = new JSONObject(jsonText);
		     
		      return json;
		    } finally {
		      is.close();
		    }
		  }
	 private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  }
	 private String buildQuery(Map<String, String> params){
		
		String query= "";
		for(String key: params.keySet()){
			query += key + "=" + params.get(key)+"&";
			
		}
		return query.substring(0,query.length()-1);
	}

}
