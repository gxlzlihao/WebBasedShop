<%-- 
    Document   : newitem
    Created on : Jun 16, 2013, 4:26:10 PM
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
        <title>New Item Page</title>
    </head>
    <body>
        <h1>Please input the information of the new item!</h1>
        ${(itemInfoInvalid != null)?itemInfoInvalid:null};

        <%-- display the already input item information--%>
        <c:set var="newItemList_xslt_admin">
            <c:import url="newItemList_xslt_admin.xsl"/>
        </c:set> 
        <x:transform xslt="${newItemList_xslt_admin}">
            <onlineshop:newItemList/>
        </x:transform>

        <%-- input the new item information--%>
        <form action="admin?action=add_new_item" method="post">
            <table>
                <tr>
                    <td>Item Name</td>
                    <td><input type="text" name="new_item_name"/></td>
                </tr>
                <tr>
                    <td>Item Price</td>
                    <td><input type="text" name="new_item_price"/></td>
                </tr>
                <tr>
                    <td>Quantity</td>
                    <td><input type="text" name="new_item_stock"/></td>
                </tr>
            </table>
            <input type="submit" value="add"/>
        </form>
        <br><h4><a href="admin?action=create_new_items">done</a></h4>
    </body>
</html>
