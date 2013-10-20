<%-- 
    Document   : product_profile_admin
    Created on : Jun 15, 2013, 12:10:32 PM
    Author     : gxlzlihao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product`s Information</title>
    </head>
    <body>
        <h1>Please input the new product`s information</h1>
        ${(productInforInvalid!=null)?productInforInvalid:null};

        <%--input the product information--%>
        <form method="post" action="admin?action=create_new_product">
            <table bgcolor="#FFDC75">
                <tr>
                    <td>Product`s Name</td>
                    <td><input type="text" name="new_product_name"></td>
                </tr>
            </table>

            <jsp:useBean id="itemList" class="beans.ItemListBean" scope="application">
                Error, the item list bean should have been created in the servlet!
            </jsp:useBean>

            <c:set var="iteminfor_admin_xslt">
                <c:import url="iteminfor_admin_xslt.xsl"/>
            </c:set>    

            <x:transform xslt="${iteminfor_admin_xslt}">
                <jsp:getProperty name="itemList" property="xml"/>
            </x:transform>
                
            <input type="submit" name="submit">

        </form>

        <%--input the quantities of available items--%>

    </body>
</html>
