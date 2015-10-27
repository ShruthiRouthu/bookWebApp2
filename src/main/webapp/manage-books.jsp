<%-- 
    Document   : manage_books
    Created on : Oct 23, 2015, 1:52:56 PM
    Author     : Shruthi Routhu 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Books</title>
    </head>
    <body>
        <h1>Manage Books</h1>
        
        <table width="500" border="1" cellspacing="0" cellpadding="4">
            <tr style="background-color: black;color:white;">
                <th align="left" class="tableHead">ID</th>
                <th align="left" class="tableHead">Title</th>
                <th align="right" class="tableHead">ISBN</th>
                <th align="left" class="tableHead">Author</th>
            </tr>
        <c:forEach var="b" items="${books}" varStatus="rowCount">
            <c:choose>
                <c:when test="${rowCount.count % 2 == 0}">
                    <tr style="background-color: white;">
                </c:when>
                <c:otherwise>
                    <tr style="background-color: #ccffff;">
                </c:otherwise>
            </c:choose>
                        
            <td align="left">${b.bookId}</td>
            <td align="left">${b.title}</td>
            <td align="right">${b.isbn}</td>
            <td align="left">${b.authorId.authorName}</td>
            
            <td> <input type="button" name="deleteBook" value="Delete" onclick="location.href='BookController?action=delete&bookId=${b.bookId}'" /></td>
    
            <td> <input type="button" name="editAuthor" value="Edit" onclick="location.href='BookController?action=showEditPage&bookId=${b.bookId}'"></td>                
                 
           
         </c:forEach>
            
            </table>
            
            
            <c:if test="${errMsg != null}">
                <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                    ${errMsg}</p>
            </c:if>
            
            <br>
            <input type="button"  name="addAuthor"  value="Add" onclick="location.href='BookController?action=showAddPage'" >
            <br>
    
        
        
        
        <a href="BookController?action=showHomePage">Home</a>
        <script src="https://code.jquery.com/jquery-2.1.4.min.js"> </script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js">  </script>
        
    </body>
</html>
