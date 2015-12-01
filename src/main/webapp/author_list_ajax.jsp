<%-- 
    Document   : author_list_ajax
    Created on : Nov 15, 2015, 5:53:48 PM
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
        <title>Author List Using Ajax</title>
    </head>
    <body class="authorList">
    
        <h1>Author List</h1>
        <table id="authorTable" style="width: 50%;" border="1" cellspacing="0" cellpadding="4">
            <thead>
                <tr style="background-color: black;color:white;">
                    <th align="left" class="tableHead">ID</th>
                    <th align="left" class="tableHead">Author Name</th>
                    <th align="right" class="tableHead">Date Added</th>
                </tr>
            </thead>
            <tbody id="authorTableBody">
                
            </tbody>
           
        </table> 
        
        <table id="addEditAuthor" style="display:none;" width="500" border="1" cellspacing="0" cellpadding="4">
            <tr>
                <td style="background-color: black;color:white;" align="left">ID</td>
                <td align="left"><input type="text" value="" id="authorId" name="authorId" readonly/></td>
            </tr>         
            <tr>
                <td style="background-color: black;color:white;" align="left">Name</td>
                <td align="left"><input type="text" value="" id="authorName" name="authorName" /></td>
            </tr>
            <tr>
                <td style="background-color: black;color:white;" align="left">Date Added</td>
                <td align="left"><input type="text" value="" id="dateAdded" name="dateAdded" readonly /></td>
            </tr>         
        </table>

        <sec:authorize access="hasAnyRole('ROLE_MGR','ROLE_USER')">
            Logged in as: <sec:authentication property="principal.username"></sec:authentication> ::
            <a href='<%= this.getServletContext().getContextPath() + "/j_spring_security_logout"%>'>Log Me Out</a>
        </sec:authorize>    
              
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" type="text/javascript"></script>
        <script src="resources/js/author_list_ajax.js" type="text/javascript"></script>
    </body>
</html>
