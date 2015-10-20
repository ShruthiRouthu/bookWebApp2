<%-- 
    Document   : editAuthor
    Created on : Sep 29, 2015, 1:16:16 PM
    Author     : Professional
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Author</title>
         <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    </head>
    <body bgcolor="${pageColor}" style="color: ${fontColor};">
        <h1>Edit Author</h1>
         
        <form id="editForm" name="editForm" method="POST" action="AuthorController?action=edit">
            
         
                  <c:set var="author" scope="page" value="${selectedAuth}"/> 
                
                  <input type="hidden" name="authorID" id="authorID" value="${author.authorId}" >
               
                  <div class="form-group">
                    <label for="authorName">Author Name:  </label>
                    <input  class="form-control" id="authorName" name="authorName" type="text" value="${author.authorName}" required>
                   </div>

                
                  <button type="submit" class="btn btn-primary">Save changes</button>
        
        </form>
    </body>
</html>


<!--  <div class="form-group">
                    <label for="dateAdded">Date Added:  </label>
                    <input  class="form-control" id="dateAdded" name="dateAdded" type="text" value="<fmt:formatDate pattern='MM-dd-yyyy' value='${author.dateAdded}'/>" required>
                  </div> -->