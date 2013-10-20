<%-- 
    Document   : productInfo_admin
    Created on : Jun 20, 2013, 11:02:54 AM
    Author     : gxlzlihao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@taglib prefix="onlineshop" uri="/WEB-INF/tlds/webbasedshop.tld"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Information Page</title>
    </head>
    <body>
        <h1>Product`s Information</h1>
        
        <c:set var="productinfo_xslt_admin">
                <c:import url="productinfo_xslt_admin.xsl"/>
        </c:set> 
                
        <x:transform xslt="${productinfo_xslt_admin}">
                <onlineshop:proInfoTag pid="${pid}" jdbc_url="${jdbc_url}"/>
        </x:transform>
        
        <a href="admin?action=display">back</a>
        
    </body>
</html>
