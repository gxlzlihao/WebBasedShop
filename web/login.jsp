<%-- 
    Document   : login
    Created on : May 21, 2013, 10:01:10 PM
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
            <form method="POST" action='<%= response.encodeURL("j_security_check") %>' >
                <table border="0" cellspacing="5" align="center">
                    <tr>
                        <td colspan="2" bgcolor="#FFDC75"><h2>Log in to the Onlineshop</td>
                    </tr>
                    <tr><td colspan="2"></td></tr>
                        <tr>
                            <th align="right">Username:</th>
                            <td align="left"><input type="text" name="j_username"></td>
                        </tr>
                        <tr>
                            <th align="right">Password:</th>
                            <td align="left"><input type="password" name="j_password"></td>
                        </tr>
                        <tr>
                            <td align="right"><input type="submit" name="login" value="Login"></td>
                            <td align="left"><input type="reset"></td>
                        </tr>
                        <tr>
                            <td><a href="shop?action=newuser">I ain't got no account</a>      
                            </td>
                        </tr>
                </table>

            </form>
        </center>
    </body>
</html>
