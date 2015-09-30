
package edu.wctc.srt.bookwebapp2.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MySqlDbStrategy implements DBStrategy {
    
    private static final Boolean debug = true;
    private Connection conn;
    
    // Exception will be handled where the notifications are needed. Do validation too
    @Override
    public final void openConnection(String driverClass, String url, String userName, String password) throws Exception{     
        Class.forName (driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }
    
    @Override
    public final void closeConnection() throws SQLException{
        conn.close();
    }
    
    @Override
    public final List<Map<String,Object>> findAllRecords(String tableName) throws SQLException{
        
        String sql = "SELECT * FROM " + tableName ;
        List<Map<String,Object>> recordList = new ArrayList<>();
        Statement stmt =conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        
        while( rs.next()){
            Map<String,Object> record = new HashMap<>();
            for(int i=1; i <= columnCount; i++){
                record.put(metaData.getColumnName(i), rs.getObject(i));
            }
            recordList.add(record);    
        }
        
        return recordList;
    }
    
    @Override
    public List<Map<String,Object>> findRecordsByCondition(String tableName, String condColName, Object condColVal)throws SQLException {
        
        String sql = "SELECT * FROM " + tableName + " WHERE " +  condColName + " = ? " ;
        if(debug) System.out.println(sql);
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setObject(1,condColVal);
        
        ResultSet rs = stmt.executeQuery();
        ResultSetMetaData metaData = rs.getMetaData();
        
        int columnCount = metaData.getColumnCount();
        List<Map<String,Object>> recordList = new ArrayList<>();
        
         while( rs.next()){
            Map<String,Object> record = new HashMap<>();
            for(int i=1; i <= columnCount; i++){
                record.put(metaData.getColumnName(i), rs.getObject(i));
            }
            recordList.add(record);    
        }
        
        return recordList;
        
    }
    
     @Override
    public Map<String, Object> findRecordByID(String tableName, String pkName, Object pkVal) throws SQLException {
        
        String sql = "SELECT * FROM " + tableName + " WHERE " +  pkName + " = ? " ;
        if(debug) System.out.println(sql);
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setObject(1,pkVal);
        
        ResultSet rs = stmt.executeQuery();
        ResultSetMetaData metaData = rs.getMetaData();
        
        int columnCount = metaData.getColumnCount();
        Map<String,Object> record = new  HashMap<>();
        
         while( rs.next()){
            for(int i=1; i <= columnCount; i++){
                record.put(metaData.getColumnName(i), rs.getObject(i));
            }  
        }
        
        return record;
    }
    
    @Override
    public final int emptyTable(String tableName) throws SQLException{
        
        String sql = "DELETE FROM " + tableName ;
        Statement stmt = conn.createStatement();
        int noOfRecords = stmt.executeUpdate(sql);
        
        return noOfRecords;
        
    }
    
    @Override
    public final int deleteRecordByID(String tableName, String pkName ,Object pkValue ) throws SQLException {
   
        String sql = "DELETE FROM " + tableName + " WHERE " + pkName + " = ? " ;
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setObject(1 , pkValue);
        if(debug)System.out.println(sql);
        int recordsDeleted = stmt.executeUpdate();
        return  recordsDeleted;
    }
    
    // Will validate later
    @Override
    public final int updateRecord(String tableName, String conditionColName , Object conditionColValue,
            List<String> keyList ,List<Object> valueList) throws SQLException{
       
        String sql = buildUpdateSql(tableName, conditionColName ,conditionColValue, keyList);
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        int index = 1;
        for(Object value: valueList){
            stmt.setObject(index, value);
            index += 1;
        }
        
        stmt.setObject(index, conditionColValue); 
        
        return stmt.executeUpdate();
    }
    
    @Override
    public final int insertRecord(String tableName,List<String> colNameList ,List<Object> colValueList) throws SQLException{
    
        String sql = buildInsertSql(tableName,colNameList);
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        int index = 0;
        for(Object value: colValueList){
            index += 1;
            stmt.setObject(index, value);
        }
        
        return stmt.executeUpdate(); 
    }

    private String buildInsertSql(String tableName,List<String> colNameList){
       
        String sql = "Insert into " + tableName + "(" ;
        for(String name: colNameList){
            sql += name + ",";
        }
        
        int length = sql.length();
        String temp = sql.substring(0, length-1);
        sql = temp;
        
        length = colNameList.size();
        sql += ") values(" ;
        for(int i=0; i < length; i++){
            sql += "?,"; 
        }
        
        length = sql.length();
        temp = sql.substring(0, length-1);
        sql = temp;
        
        sql += ");" ;
        if(debug) System.out.println(sql);
         
        return sql;  
    }
    
    private String buildUpdateSql(String tableName, String conditionColName , Object conditionColValue, List<String> keyList ){
            
            String sql = "UPDATE " + tableName + " SET " ;
            for(int i=0; i< keyList.size() ; i++) {
                sql += " " + keyList.get(i) + " = ?," ;
            } 
            
            int length = sql.length();
            String temp = sql.substring(0, length-1);
            sql = temp;
            
            sql += " WHERE " + conditionColName + " = ?" ;
            if(debug)System.out.println(sql);
            return sql;
    }
    
   
    
    public static void main(String args[]) throws Exception{
       MySqlDbStrategy db = new  MySqlDbStrategy();
       db.openConnection("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/book","root","DJ2015");
 
//FIND ALL RECORDS       
//      List<Map<String,Object>> records = db.findAllRecords("author");
//      for(Map record : records) 
//         {  System.out.println(record);  }

//FIND RECORDS BY SOME VALUE / CONDITION
//        List<Map<String,Object>> records = db.findRecordsByCondition("author","author_id", 21);
//        for(Map record : records) 
//           {  System.out.println(record);  }
       
//FIND RECORDS BY ID 
//        Map<String,Object> record = db.findRecordByID("author","author_id", 32);
//             System.out.println(record);        

//EMPTY THE TABLE        
//      int no = db.emptyTable("author");
        
//DELETE RECORDS BY ID  
//      int no = db.deleteRecordByID("author","author_id", 23);
//      System.out.println(no);
       
// LISTS FOR RUNNING INSERT OR UPDATE         
//        List<String> key = new ArrayList();
//        key.add( "author_name");
//        key.add("date_added");
//        
//        List<Object> value = new ArrayList();
//        value.add("Robert Martin");
//        value.add("2015-09-01");

//INSERT A RECORD        
//      System.out.println(db.insertRecord("author", key, value));

//UPDATE A RECORD        
//        System.out.println(db.updateRecord("author" ,"author_id", 2, key, value));   
        
        
//      HashMap<String,Object> hm=new HashMap<String,Object>();  
//      hm.put("author_id",4);  
//      hm.put("name","vijay");  
//      hm.put("date","10/20/2015"); 
      

        
        
      
        db.conn.close(); 
    }

   
}

