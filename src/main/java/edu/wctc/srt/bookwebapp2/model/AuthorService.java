
package edu.wctc.srt.bookwebapp2.model;

import java.util.List;
import java.util.ArrayList;

public class AuthorService {
    
    
    private AuthorDAOStrategy dao;

    public AuthorService(AuthorDAOStrategy dao) {
        this.dao = dao;
    }
    
    public List<Author> getAllAuthors() throws Exception{
        return dao.getAllAuthors();
    }
    
    public List<Author> getAuthorsByValue(String condColName, Object condColVal) throws Exception {
        return dao.getAuthorsByValue(condColName, condColVal);
    }
    
    public final int clearAuthorTable() throws Exception {
        return dao.clearAuthorTable();
    }
    
    public final int deleteAuthorByID(String pkName, Object pkValue) throws Exception {
        return dao.deleteAuthorByID(pkName, pkValue);
    }
    
    public final int updateAuthor(String conditionColName, Object conditionColValue, 
            List<String> keyList, List<Object> valueList) throws Exception {
        return dao.updateAuthor(conditionColName, conditionColValue, keyList, valueList);
    }
    
    public final int insertAuthor(List<String> colNameList, List<Object> colValueList) throws Exception {
        return dao.insertAuthor(colNameList, colValueList);
    }
    
    public static void main(String[] args) throws Exception{
                
        AuthorService service = new AuthorService( new AuthorDAO(new  MySqlDbStrategy() ,"com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/book","root","DJ2015"));
       
// ALL AUTHORS
//        List<Author> Authors = service.getAllAuthors();
//        for(Author a : Authors) {
//            System.out.println(a);
//        }
                
// AUTHORS BY SOME CONDITION
//        List<Author> Authors = service.getAuthorsByValue("author_name", "Matt");
//        for(Author a : Authors) {
//            System.out.println(a);
//        }            
//       
        
//LISTS FOR UPDATE < INSERT 
//        List<String> key = new ArrayList();
//        key.add( "author_name");
//        key.add("date_added");
//        
//        List<Object> value = new ArrayList();
//        value.add("Obama");
//        value.add("2000-06-29");
        
//INSERT AUTHOR        
//        System.out.println(service.insertAuthor( key, value));
        
//UPDATE AUTHOR
//        System.out.println(service.updateAuthor("author_id", 18, key, value));
        
//DELETE AUTHOR BY ID
//            System.out.println(service.deleteAuthorByID("author_id", 23));
            
//DELETE ALL AUTHORS
//        System.out.println(service.clearAuthorTable());
    }
    
}
