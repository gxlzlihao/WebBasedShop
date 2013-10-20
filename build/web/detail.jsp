<%-- 
    Document   : detail
    Created on : May 23, 2013, 3:33:46 PM
    Author     : gxlzlihao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" import="beans.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>

<jsp:useBean id="product" class="beans.ProductBean" scope="request">
    Error, the bean should have been created in the servlet!
    No Product Bean is found!
</jsp:useBean>
    
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Details of Your Product</title>
    </head>
    <body>
        <c:set var="productdetail_xslt">
            <c:import url="productdetail.xsl"/>
        </c:set> 

        <x:transform xslt="${productdetail_xslt}">
            <detailpage>
                <jsp:getProperty name="product" property="xml"/>
            </detailpage>
        </x:transform>
    
        <center>go back to <a href="shop">products` page</a></center>
    </body>
</html>
