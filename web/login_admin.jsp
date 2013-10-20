<%-- 
    Document   : login_admin
    Created on : Jun 1, 2013, 9:12:55 PM
    Author     : gxlzlihao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>The Login page</title>
    </head>
    <body>
    <center>
        ${(AuthenticationInvalid == null)?null:AuthenticationInvalid}
        <form method="POST" action='<%= response.encodeURL("admin")%>'>
            <table method='post' action='<%= response.encodeURL("admin")%>' border="0" cellspacing="5" align="center">
                <tr>
                    <td colspan="2" bgcolor="#FFDC75"><h2>Log in to the Onlineshop</td>
                </tr>
                <tr><td colspan="2"></td></tr>
                <tr>
                    <th align="right">Username:</th>
                    <td align="left"><input type="text" name="admin_username"></td>
                </tr>
                <tr>
                    <th align="right">Password:</th>
                    <td align="left"><input type="password" name="admin_password"></td>
                </tr>
                <tr>
                <input type='hidden' name='action' value='login'>
                </tr>
                <tr>
                    <td align="right"><input type="submit" name="login" value="Login"></td>
                    <td align="left"><input type="reset"></td>
                </tr>

            </table>

        </form>
        
    </center>
</body>
</html>
