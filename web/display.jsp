<%-- 
    Document   : display
    Created on : May 21, 2013, 9:51:40 PM
    Author     : gxlzlihao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" import="beans.*"%>
<%--<%@taglib prefix="bookshop" uri="/bookshop"%>--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@taglib prefix="onlineshop" uri="/WEB-INF/tlds/webbasedshop.tld"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>The Products Contained In The Shop</title>
    </head>
    <body>
    <center>
        <h1>LiHao`s Online Shop</h1>
        <jsp:useBean id="productList" class="beans.ProductListBean" scope="application">
            Error, the bean should have been created in the servlet!
        </jsp:useBean>

        <c:set var="productlist_xslt">
            <c:import url="productlist_xslt.xsl"/>
        </c:set> 

        <x:transform xslt="${productlist_xslt}">
            <jsp:getProperty name="productList" property="xml"/>
        </x:transform>

        <c:set var="shoppingcart_xslt">
            <c:import url="shoppingcart_xslt.xsl"/>
        </c:set> 
        <x:transform xslt="${shoppingcart_xslt}">
            <onlineshop:ShoppingCart/>
        </x:transform>

        <form name=LogIn action=shop?action=checkout method=post>
            <input type="submit" value="Checkout" name="Checkout" />
        </form>
    </center>

    <c:if test="${sessionScope.currentUser != null}">
        <form action=shop?action=profile method=post>
            <input type="submit" value="Update Profile">
        </form>

        <form action=shop?action=logout method=post>
            <input type="submit" value="Logout">
        </form>

        <onlineshop:MessageTag url="${jdbc_url}" user_id="${sessionScope.currentUser}">
            Thank for your attention!
        </onlineshop:MessageTag>

        <form action="shop?action=clear_messages" method="post">
            <input type="hidden" name="user_id" value="${sessionScope.currentUser}"/>
            <input type="submit" name="submit" value="clear messages">
        </form>

    </c:if>

</body>
</html>
