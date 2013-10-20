<%-- 
    Document   : display_admin
    Created on : Jun 1, 2013, 9:13:25 PM
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
        <title>The Product List</title>
    </head>
    <body>
        <onlineshop:MessageTag url="${jdbc_url}" user_id="admin"/>

        <h1>The Product List</h1>

        <jsp:useBean id="productList" class="beans.ProductListBean" scope="application">
            Error, the product list bean should have been created in the servlet!
        </jsp:useBean>

        <c:set var="productlist_xslt_admin">
            <c:import url="productlist_xslt_admin.xsl"/>
        </c:set> 

        <x:transform xslt="${productlist_xslt_admin}">
            <jsp:getProperty name="productList" property="xml"/>
        </x:transform>

        <br><a href="admin?action=new_product">add new product</a>

        <h1>The Item List</h1>

        <jsp:useBean id="itemList" class="beans.ItemListBean" scope="application">
            Error, the item list bean should have been created in the servlet!
        </jsp:useBean>

        <c:set var="itemlist_xslt_admin">
            <c:import url="itemlist_xslt_admin.xsl"/>
        </c:set>

        <x:transform xslt="${itemlist_xslt_admin}">
            <jsp:getProperty name="itemList" property="xml"/>
        </x:transform>

        <br><a href="admin?action=new_item">add new item</a>
        <br><a href="admin?action=delete_item">delete items</a>

        <a href="admin?action=view_orders"><h1>View Orders</h1></a>

    </body>
</html>
