/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.srt.bookwebapp2.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 *
 * @author Professional
 */
public class AuthorDAOUsingConnectionPool implements AuthorDAOStrategy {

    private DBStrategy dbStrat;
    private DataSource ds;
    
    public AuthorDAOUsingConnectionPool(DBStrategy dbStrat, DataSource ds) {
        this.dbStrat = dbStrat;
        this.ds = ds;
    }
     
    @Override
    public final List<Author> getAllAuthors() throws Exception {
        
        List<Author> finalList = new ArrayList<>();
        dbStrat.openConnection(ds);
        
        List<Map<String,Object>> rawData =  dbStrat.findAllRecords("author");
        
        for(Map m : rawData){
            Author a = new Author();
           
            Object obj = (m.get("author_id")== null)? "" : m.get("author_id");
            a.setId(Integer.parseInt(obj.toString()));
            
            obj = (m.get("author_name")== null)? "" : m.get("author_name");
            a.setName(obj.toString());
            
            obj = (m.get("date_added")== null)? "" : m.get("date_added");
            a.setDateAdded((Date)obj);
            
            finalList.add(a);
        }
       
        return finalList ;
    }
    
    @Override
    public List<Author> getAuthorsByValue(String condColName, Object condColVal) throws Exception {
        List<Author> finalList = new ArrayList<>();
        dbStrat.openConnection(ds);
        
        List<Map<String,Object>> rawData =  dbStrat.findRecordsByCondition("author",condColName ,condColVal);
        for(Map m : rawData){
            Author a = new Author();
           
            Object obj = (m.get("author_id")== null)? "" : m.get("author_id");
            a.setId(Integer.parseInt(obj.toString()));
            
            obj = (m.get("author_name")== null)? "" : m.get("author_name");
            a.setName(obj.toString());
            
            obj = (m.get("date_added")== null)? "" : m.get("date_added");
            a.setDateAdded((Date)obj);
            
            finalList.add(a);
        }
        
        return finalList ;
        
    }
    
     @Override
    public Author getAuthorByID(String pkName, Object pkValue) throws Exception {
        
        Author selectedAuthor ;
        dbStrat.openConnection(ds);
        
        Map<String,Object> rawData =  dbStrat.findRecordByID("author",pkName ,pkValue);
        
            Author author = new Author();
           
            Object obj = (rawData.get("author_id")== null)? "" : rawData.get("author_id");
            author.setId(Integer.parseInt(obj.toString()));
            
            obj = (rawData.get("author_name")== null)? "" : rawData.get("author_name");
            author.setName(obj.toString());
            
            obj = (rawData.get("date_added")== null)? "" : rawData.get("date_added");
            author.setDateAdded((Date)obj);
      
        return author;
    }
   
    @Override
    public final int clearAuthorTable() throws Exception {
        dbStrat.openConnection(ds);
        int authorsDeleted = dbStrat.emptyTable("author");
        
        return authorsDeleted;
    }

    @Override
    public final int deleteAuthorByID(String pkName, Object pkValue) throws Exception {
        dbStrat.openConnection(ds);
        int authorsDeleted = dbStrat.deleteRecordByID("author", pkName, pkValue);
         
        return authorsDeleted;
    }

    @Override
    public final int updateAuthor(String conditionColName, Object conditionColValue, 
            List<String> keyList, List<Object> valueList) throws Exception {
        
        dbStrat.openConnection(ds);
        int authorsUpdated = dbStrat.updateRecord("author", conditionColName, conditionColValue, keyList, valueList);
       
        return authorsUpdated;
    }

    @Override
    public final int insertAuthor(List<String> colNameList, List<Object> colValueList) throws Exception {
        dbStrat.openConnection(ds);
        int authorsInserted = dbStrat.insertRecord("author", colNameList, colValueList);
         
        return authorsInserted;
    }
    
}
