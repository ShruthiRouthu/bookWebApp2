<%-- 
    Document   : edit_book
    Created on : Oct 23, 2015, 1:53:32 PM
    Author     : Shruthi Routhu 
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
        <title>Edit Book</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    </head>
    <body>
        <h1>Edit Book</h1>

        <form id="EditForm" name="EditForm" method="POST" action="BookController?action=edit">
            <sec:authorize access="hasAnyRole('ROLE_MGR')">

                <c:set var="book" scope="page" value="${selectedBook}"/> 


                <c:choose>
                    <c:when test="${not empty selectedBook}">
                        <input type="hidden" name="bookId" id="bookId" value="${book.bookId}" >        
                    </c:when>
                </c:choose>


                <div class="form-group">
                    <label for="bookTitle">Title:  </label>
                    <input  class="form-control" id="bookTitle" name="bookTitle" type="text" value="${book.title}" required>
                </div>

                <div class="form-group">
                    <label for="bookIsbn">Isbn:  </label>
                    <input  class="form-control" id="bookIsbn" name="bookIsbn" type="text" value="${book.isbn}" required>
                </div>

                <div class="form-group">
                    <label for="authorDropDown">Author:  </label>
                    <select id="authorDropDown" name="authorId">
                        <c:choose>
                            <c:when test="${not empty book.authorId}">
                                <option value="-1">None</option> 
                                <c:forEach var="author" items="${authors}">                                       
                                    <option value="${author.authorId}" <c:if test="${book.authorId.authorId == author.authorId}">selected</c:if>>${author.authorName}</option>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <option value="-1" selected>None</option>
                                <c:forEach var="author" items="${authors}" varStatus="rowCount">                                       
                                    <option value="${author.authorId}">${author.authorName}</option>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </select>

                </div>


                <button type="submit" class="btn btn-primary">Save changes</button>

            </sec:authorize>

        </form>
        <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
            Logged in as: <sec:authentication property="principal.username"></sec:authentication> ::
            <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
        </sec:authorize>           
    </body>
</html>
