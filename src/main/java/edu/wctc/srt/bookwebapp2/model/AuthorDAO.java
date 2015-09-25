
package edu.wctc.srt.bookwebapp2.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AuthorDAO implements AuthorDAOStrategy {
    
    private DBStrategy dbStrat; 
    private String driverClassName;
    private String url;
    private String userName;
    private String password;

    public AuthorDAO(DBStrategy dbStrat, String driverClassName, String url, String userName, String password) {
        this.dbStrat = dbStrat;
        this.driverClassName = driverClassName;
        this.url = url;
        this.userName = userName;
        this.password =password; 
    }
    
    @Override
    public final List<Author> getAllAuthors() throws Exception {
        
        List<Author> finalList = new ArrayList<>();
        dbStrat.openConnection(driverClassName, url, userName, password);
        
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
        dbStrat.closeConnection();
        return finalList ;
    }
    
    @Override
    public List<Author> getAuthorsByValue(String condColName, Object condColVal) throws Exception {
        List<Author> finalList = new ArrayList<>();
        dbStrat.openConnection(driverClassName, url, userName, password);
        
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
        dbStrat.closeConnection();
        return finalList ;
        
    }
   
    @Override
    public final int clearAuthorTable() throws Exception {
        dbStrat.openConnection(driverClassName, url, userName, password);
        int authorsDeleted = dbStrat.emptyTable("author");
        dbStrat.closeConnection(); 
        
        return authorsDeleted;
    }

    @Override
    public final int deleteAuthorByID(String pkName, Object pkValue) throws Exception {
        dbStrat.openConnection(driverClassName, url, userName, password);
        int authorsDeleted = dbStrat.deleteRecordByID("author", pkName, pkValue);
        dbStrat.closeConnection(); 
        
        return authorsDeleted;
    }

    @Override
    public final int updateAuthor(String conditionColName, Object conditionColValue, 
            List<String> keyList, List<Object> valueList) throws Exception {
        
        dbStrat.openConnection(driverClassName, url, userName, password);
        int authorsUpdated = dbStrat.updateRecord("author", conditionColName, conditionColValue, keyList, valueList);
        dbStrat.closeConnection(); 
        
        return authorsUpdated;
    }

    @Override
    public final int insertAuthor(List<String> colNameList, List<Object> colValueList) throws Exception {
        dbStrat.openConnection(driverClassName, url, userName, password);
        int authorsInserted = dbStrat.insertRecord("author", colNameList, colValueList);
        dbStrat.closeConnection(); 
        
        return authorsInserted;
    }
    
    
    public static void main(String[] args) throws Exception {
        DBStrategy db = new  MySqlDbStrategy();
        AuthorDAO dao = new AuthorDAO(db,"com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/book","root","DJ2015");
        
// ALL AUTHORS
//        List<Author> list = dao.getAllAuthors();
//        for(Author a : list) {
//            System.out.println(a);
//        }
        
// AUTHORS BY SOME CONDITION
//        List<Author> list = dao.getAuthorsByValue("author_name", "Matt");
//        for(Author a : list) {
//            System.out.println(a);
//        }            
       
        
//LISTS FOR UPDATE < INSERT 
//        List<String> key = new ArrayList();
//        key.add( "author_name");
//        key.add("date_added");
//        
//        List<Object> value = new ArrayList();
//        value.add("Obama");
//        value.add("2000-06-29");
        
//INSERT AUTHOR        
//        System.out.println(dao.insertAuthor( key, value));
        
//UPDATE AUTHOR
//        System.out.println(dao.updateAuthor("author_id", 13, key, value));
        
//DELETE AUTHOR BY ID
//            System.out.println(dao.deleteAuthorByID("author_id", 13));
            
//DELETE ALL AUTHORS
//        System.out.println(dao.clearAuthorTable());
        
    }

   
    
}
