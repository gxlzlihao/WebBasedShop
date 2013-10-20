<%-- 
    Document   : delete_item_admin
    Created on : Jun 17, 2013, 3:51:14 PM
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
        <title>Items Delete Page</title>
    </head>
    <body>
        <h1>To delete the items you want</h1>
        ${(itemDeleteResult==null)?null:itemDeleteResult};
        
        <jsp:useBean id="itemList" class="beans.ItemListBean" scope="application">
            Error, the item list bean should have been created in the servlet!
        </jsp:useBean>
        
        <c:set var="itemdelete_admin_xslt">
            <c:import url="itemdelete_admin_xslt.xsl"/>
        </c:set>    
            
        <x:transform xslt="${itemdelete_admin_xslt}">
            <jsp:getProperty name="itemList" property="xml"/>
        </x:transform>
        
        <br><a href="admin?action=itemRemoveDone"><h4>done</h4></a>
        
    </body>
</html>
