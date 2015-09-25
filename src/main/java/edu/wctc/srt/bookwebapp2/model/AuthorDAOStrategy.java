
package edu.wctc.srt.bookwebapp2.model;

import java.sql.SQLException;
import java.util.List;

public interface AuthorDAOStrategy {

    List<Author> getAllAuthors() throws Exception;
    
    List<Author> getAuthorsByValue( String condColName, Object condColVal) throws Exception;
    
    int clearAuthorTable() throws Exception;
    
    int deleteAuthorByID(String pkName ,Object pkValue ) throws Exception;
    
    int updateAuthor( String conditionColName , Object conditionColValue,
            List<String> keyList ,List<Object> valueList) throws Exception;
    
    int insertAuthor(List<String> colNameList ,List<Object> colValueList) throws Exception;
    
}
