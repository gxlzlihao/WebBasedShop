<%-- 
    Document   : error
    Created on : May 21, 2013, 10:35:11 PM
    Author     : gxlzlihao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Something is wrong!</title>
    </head>
    <body>
        <h1>You are stupid! Something is wrong in this web application!</h1>
        
        <c:if test="${sessionScope.debug == null}">
            I am very sorry but an Exception has occurred: ${pageContext.exception.message}
        </c:if>

        <c:if test="${sessionScope.debug != null}">
            ${pageContext.exception.traceback}
        </c:if>
    </body>
</html>
