<%-- 
    Document   : deleteAuthor
    Created on : Sep 28, 2015, 2:13:55 PM
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
        <title>Delete Author Data</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    </head>
    <body>
        
        <h1>Edit Author Data</h1>
        
        <form id="deleteForm" name="deleteForm" method="POST" action="AuthorController?action=deleteForm">
            <table width="500" border="1" cellspacing="0" cellpadding="4">
                <tr style="background-color: black;color:white;">
                    <th align="left" class="tableHead">ID</th>
                    <th align="left" class="tableHead">Author Name</th>
                    <th align="right" class="tableHead">Date Added</th>
                    <th align="left" class="tableHead">Choose One</th>
                </tr>
            <c:forEach var="a" items="${authors}" varStatus="rowCount">
                <c:choose>
                    <c:when test="${rowCount.count % 2 == 0}">
                        <tr style="background-color: white;">
                    </c:when>
                    <c:otherwise>
                        <tr style="background-color: #ccffff;">
                    </c:otherwise>
                </c:choose>
                <td align="left">${a.id}</td>
                <td align="left">${a.name}</td>
                <td align="right"><fmt:formatDate pattern="M/d/yyyy" value="${a.dateAdded}"></fmt:formatDate></td>
                <td> <input type="radio" name="authorSelected" value="${a.id}" > </td>
                <td> <input type="button" name="editAuthor" value="Edit" data-toggle="modal" data-target="#myModal"></td>
                
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="myModalLabel">Update Author</h4>
          </div>
          
              <div class="modal-body">

                  <div class="form-group">
                    <label for="authorName">Author Name:  </label>
                    <input  class="form-control" id="authorName" name="authorName" type="text" value="${a.name}" required>
                   </div>

                  <div class="form-group">
                    <label for="dateAdded">Date Added:  </label>
                    <input  class="form-control" id="dateAdded" name="dateAdded" type="date" value="${a.dateAdded}" required>
                  </div>

              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary">Save changes</button>
              </div>
                  
        
        </div>
      </div>
    </div>
            </tr>
            </c:forEach>
            </table>
            
            
            <c:if test="${errMsg != null}">
                <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                    ${errMsg}</p>
            </c:if>
            
            <br><br>
            <input  id="deleteButton" name="deleteButton" class="btn btn-primary" type="submit" value="Delete" >
            <br><br>
        </form>
         
        <script src="https://code.jquery.com/jquery-2.1.4.min.js"> </script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js">  </script>
        
    </body>
</html>
