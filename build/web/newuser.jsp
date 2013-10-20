<%-- 
    Document   : newuser
    Created on : May 24, 2013, 12:55:18 PM
    Author     : gxlzlihao
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@taglib prefix="webbasedshop" uri="/WEB-INF/tlds/webbasedshop.tld"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>To Create A New User</title>
    </head>
    <body>
        <center>
            <h1>Please create the profile for you.</h1>
    <!-- the passwordInvalid is the attribute of the session
        the profile below is also the attribute of the session.-->
                ${(passwordInvalid != null)?(passwordInvalid):null}
            <table border=0>
                <form action=shop?action=usercreate method=post>
                    <tr>
                        <td>Username:</td>
                        <td> <input type="text" name="user" value="${profile.user_name}" ></td>
                    </tr>
                    <tr>
                        <td>New password:</td> 
                        <td><input type="password" name="password" value = ${profile.user_password}></td>
                    </tr>
                    <tr>
                        <td>Verify password:</td> 
                        <td><input type="password" name="password2" value = ${profile.user_password} ></td>
                    </tr>
                    <tr>
                        <td>Name:</td>
                        <td> <input type = "text" name = "name" value = "${profile.name}" ></td>
                    </tr>
                    <tr>
                        <td>Street Address:</td> 
                        <td><input type = "text" name = "street" value = "${profile.address}"></td>
                    </tr>
                    <tr>
                        <td>Zipcode:</td>
                        <td><input type = "text" name = "zip" value = "${profile.zip}"></td>
                    </tr>
                    <tr>
                        <td>City:</td>
                        <td> <input type = "text" name = "city" value = "${profile.city}"></td>
                    </tr>
                    <tr>
                        <td>Country:</td>
                        <td> <input type = "text" name = "country" value = "${profile.country}"></td>
                    </tr>
                    <tr>
                        <td>Select roles:</td>
                            <c:forEach var="next" items="${roles}">
                            <tr>
                                <c:if test="${next.value == true}">
                                <td><input type="checkbox" name="${next.key}" checked> ${next.key}    </td>
                                </c:if>
                                <c:if test="${next.value == false}">
                                <td><input type="checkbox" name="${next.key}"> ${next.key}    </td>
                                </c:if>

                            </tr>
                            </c:forEach>
                </table> 
                <input type="submit" value="Go">
               </form>
      
      </center>
    </body>
</html>
