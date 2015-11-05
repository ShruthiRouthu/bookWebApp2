<%-- 
    Document   : editAuthor
    Created on : Sep 29, 2015, 1:16:16 PM
    Author     : Professional
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Author</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    </head>
    
    <body bgcolor="${pageColor}" style="color: ${fontColor};">
        <h1>Add/Edit Author</h1>

        <form id="editForm" name="editForm" method="POST" action="AuthorController?action=edit">
            <sec:authorize access="hasAnyRole('ROLE_MGR')">    

                <c:set var="author" scope="page" value="${selectedAuth}"/> 

                <input type="hidden" name="authorID" id="authorID" value="${author.authorId}" >

                <div class="form-group">
                    <label for="authorName">Author Name:  </label>
                    <input  class="form-control" id="authorName" name="authorName" type="text" value="${author.authorName}" required>
                </div>
                
                <c:choose>
                    <c:when test="${not empty bookSet}">

                        <div class="form-group">
                            <label for="bookDropDown">Books:  </label>
                            <select id="booksDropDown" name="bookDropDown">
                                <c:forEach var="book" items="${bookSet}" varStatus="rowCount">                                       
                                    <option value="${book.bookId}">${book.title}</option>
                                </c:forEach>
                            </select>
                        </div>
                        
                    </c:when>
                    <c:otherwise>
                      
                        <div class="form-group">
                            <label for="bookDropDown2">Books:  </label>
                            <select id="booksDropDown2" name="bookDropDown">                                      
                                    <option value="">none</option>  
                            </select>
                        </div>
                       
                    </c:otherwise>
                </c:choose>
                
                <button type="submit" class="btn btn-primary">Save changes</button>

            </sec:authorize>
        </form>

        <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
            Logged in as: <sec:authentication property="principal.username"></sec:authentication> ::
            <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
        </sec:authorize>           
    </body>
</html>


<!--  <div class="form-group">
                    <label for="dateAdded">Date Added:  </label>
                    <input  class="form-control" id="dateAdded" name="dateAdded" type="text" value="<fmt:formatDate pattern='MM-dd-yyyy' value='${author.dateAdded}'/>" required>
                  </div> -->