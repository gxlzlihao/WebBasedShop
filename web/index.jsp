<%-- 
    Document   : index
    Created on : May 21, 2013, 9:05:51 PM
    Author     : gxlzlihao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" tyle="text/css" href="style_one.css">
        <title>JSP Page</title>
    </head>
    <body>
    <center>
        <h1>Welcome the LiHao`s Online Shop</h1>
        
        <table border="0">
            <tr bgcolor="red">
                <td><a href="shop">Enter the Shop</a></td>
            </tr>
            <tr bgcolor="red">
                <%--<td><a href="login_admin.jsp">Administrate the Shop</a></td>--%>
                <td><a href="admin?action=index">Administrate the Shop</a></td>
            </tr>
        </table>
    </center>
</body>
</html>
