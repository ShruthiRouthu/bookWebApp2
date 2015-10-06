<%-- 
    Document   : manageAuthors
    Created on : Sep 28, 2015, 4:35:39 PM
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
        <title>Manage Authors</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    </head>
    <body bgcolor="${pageColor}" style="color: ${fontColor};">
        <h1> Manage  Authors</h1>
        
            
        <table width="500" border="1" cellspacing="0" cellpadding="4">
            <tr style="background-color: black;color:white;">
                <th align="left" class="tableHead">ID</th>
                <th align="left" class="tableHead">Author Name</th>
                <th align="right" class="tableHead">Date Added</th>
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
            
            <td> <input type="button" name="deleteAuthor" value="Delete" onclick="location.href='AuthorController?action=delete&authorID=${a.id}'" /></td>
    
            <td> <input type="button" name="editAuthor" value="Edit" onclick="location.href='AuthorController?action=showEditPage&authorID=${a.id}'"></td>                
                 
           
            </tr>
            </c:forEach>
            
            </table>
            
            
            <c:if test="${errMsg != null}">
                <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                    ${errMsg}</p>
            </c:if>
            
            <br>
            <input type="button"  name="addAuthor"  value="Add" data-toggle="modal" data-target="#addModal" >
            <br>
    
    <!-- Bootstrap Modal Dialog Boxes -->        
    <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <form id="addForm" name="addForm" method="POST" action="AuthorController?action=add">  
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="myModalLabel">Add Author</h4>
          </div>
            <div class="modal-body">
                                                               
                  <div class="form-group">
                    <label for="authorName">Author Name:  </label>
                    <input  class="form-control" id="authorName" name="authorName" type="text" value="" required>
                   </div>

                  <div class="form-group">
                    <label for="dateAdded">Date Added:  </label>
                    <input  class="form-control" id="dateAdded" name="dateAdded" type="date" value="" placeholder="Date format YYYY-MM-DD" required>
                  </div>
                
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary">Add</button>
            </div>
          </form>
        </div>
      </div>
    </div>
            
       <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          
        <form id="editForm" name="editForm" method="POST" action="AuthorController?action=edit">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="myModalLabel">Update Author</h4>
          </div>
          <div class="modal-body">
                
                  <input type="hidden" name="authorID" id="authorID" value="${a.id}" >
               
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
        </form>
        </div>
      </div>
    </div>
    <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <form id="deleteForm" name="deleteForm" method="POST" action="AuthorController?action=delete">  
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="myModalLabel">Delete Author</h4>
          </div>
        <div class="modal-body">
                
                  <input type="hidden" name="authorID" id="authorID" value="${a.id}" >
                                
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
                <button type="submit" class="btn btn-primary">Delete</button>
        </div>
        </form>
        </div>
      </div>
    </div>       
       
        <a href="AuthorController?action=home">Home</a>
        <script src="https://code.jquery.com/jquery-2.1.4.min.js"> </script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js">  </script>
    </body>
    
   
   
</html>
