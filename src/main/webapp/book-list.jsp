<%-- 
    Document   : book_list
    Created on : Oct 20, 2015, 12:33:00 PM
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
        <title>Book List</title>
    </head>
    <body bgcolor="${pageColor}" style="color: ${fontColor};">

        <h1>Book List</h1>

        <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">

            <table width="500" border="1" cellspacing="0" cellpadding="4">
                <tr style="background-color: black;color:white;">
                    <th align="left" class="tableHead">ID</th>
                    <th align="left" class="tableHead">Title</th>
                    <th align="left" class="tableHead">ISBN</th>
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
                    </tr>
                </c:forEach>
            </table>
            <c:if test="${errMsg != null}">
                <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                    ${errMsg}</p>
                </c:if>


        </sec:authorize>


        <!--   <a href="BookController?action=home">Home</a> -->

        <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
            Logged in as: <sec:authentication property="principal.username"></sec:authentication> ::
            <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
        </sec:authorize>
    </body>
</html>

