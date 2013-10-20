<%-- 
    Document   : checkout
    Created on : May 31, 2013, 12:56:37 PM
    Author     : gxlzlihao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" import="beans.*, tags.*"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@taglib prefix="onlineshop" uri="/WEB-INF/tlds/webbasedshop.tld"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checkout Page</title>
    </head>
    <body>
        <h1>Please verify your shopping cart and checkout!</h1>

    <c:if test="${sessionScope.currentUser == null}">
        <onlineshop:profile url="${jdbc_url}"/>
    </c:if>
    <c:set var="shoppingcart_checkout_xslt">
        <c:import url="shoppingcart_checkout_xslt.xsl"/>
    </c:set> 
    <x:transform xslt="${shoppingcart_checkout_xslt}">
        <checkout>
            <onlineshop:ShoppingCart/>
            <name>${profile.name}</name>
            <address>${profile.address}</address>
            <zip>${profile.zip}</zip>
            <city>${profile.city}</city>
        </checkout>
    </x:transform>

</body>
</html>
