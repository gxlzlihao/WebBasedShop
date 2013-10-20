<%-- 
    Document   : orders_admin
    Created on : Jun 23, 2013, 8:49:49 PM
    Author     : gxlzlihao
--%>

<%@page import="java.io.FileWriter"%>
<%@page contentType="text/html" pageEncoding="UTF-8" import="beans.*, java.io.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@taglib prefix="onlineshop" uri="/WEB-INF/tlds/webbasedshop.tld"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Orders Page</title>
    </head>
    <body>
        <h1>The Order List</h1>

        <jsp:useBean id="orderList" class="beans.OrderListBean" scope="application">
            Error, the item list bean should have been created in the servlet!
        </jsp:useBean>

        <c:set var="orderlist_xslt_admin">
            <c:import url="orderlist_xslt_admin.xsl"/>
        </c:set>
<%--
        <x:transform xslt="orderlist_xslt_admin2.xsl">
            <jsp:getProperty name="orderList" property="xml"/>
        </x:transform>
            --%>
        <%--
        the new way to display order list, using javascript
        --%>
        <%
            ServletContext scc = getServletContext();
            OrderListBean olb = (OrderListBean)scc.getAttribute("orderList");
            /*
            File f = new File("temp.xml");
            if(!f.exists()){
                throw new JspException("the temp.xml file doesn`t exist!");
            }
            FileWriter fw = new FileWriter(f);
            try{
                String jj = new String(olb.getXml());
                fw.write(olb.getXml());
            }catch(Exception e){
                throw new JspException(e);
            }*/
        %>
        <onlineshop:OrderListTag xmlSource="<%=olb.getXml()%>">
            Please input the xml input here!
        </onlineshop:OrderListTag>
        <%--
        <script>
            if(window.XMLHttpRequest){
                xmlhttp=new XMLHttpRequest();
            }
            else{
                xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
            }
            xmlhttp.open("GET","temp.xml",false);
            xmlhttp.send();
            xmlDoc=xmlhttp.responseXML;
            /*
            alert(orderXml);
            if(window.DOMParser){
                parser = new DOMParser();
                xmlDoc = parser.parseFromString(orderXml, "text/xml");
            }
            else{
                xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
                xmlDoc.async=false;
                xmlDoc.loadXML(orderXml);
            }*/
            //output the table
            document.write("<table border=\"0\">");
            document.write("<tr bgcolor=\"#FFDC75\"><td>OrderId</td><td>UserId</td><td>UserName</td><td>OrderDate</td><td>Shipping Address</td><td>Shipping Zipcode</td><td>Shipping City</td></tr>");
            
            var x=xmlDoc.getElementsByTagName("OrderInfo");
            alert(x);
            for(i=0;i<x.length;i++){
                //output the row for each order record
                document.write("<tr bgcolor=\"silver\" cellspacing=\"0\">");
                var currentOrder = x[i].getElementsByTagName("Order").childNodes[0];
                //output the order id
                document.write("<td>");
                document.wirte(currentOrder.getElementsByTagName("order_id").childNodes[0].nodeValue);
                document.write("</td>");
                
                
                document.write("<td>");
                document.wirte(currentOrder.getElementsByTagName("user_id").childNodes[0].nodeValue);
                document.write("</td>");
                
                
                document.write("<td>");
                document.wirte(currentOrder.getElementsByTagName("user_name").childNodes[0].nodeValue);
                document.write("</td>");
                
                
                document.write("<td>");
                document.wirte(currentOrder.getElementsByTagName("order_date").childNodes[0].nodeValue);
                document.write("</td>");
                
                
                document.write("<td>");
                document.wirte(currentOrder.getElementsByTagName("shipping_address").childNodes[0].nodeValue);
                document.write("</td>");
                
                
                document.write("<td>");
                document.wirte(currentOrder.getElementsByTagName("shipping_zipcode").childNodes[0].nodeValue);
                document.write("</td>");
                
                
                document.write("<td>");
                document.wirte(currentOrder.getElementsByTagName("shipping_city").childNodes[0].nodeValue);
                document.write("</td>");
                
                document.write("</tr>");
            }
            document.write("</table>");
        </script>
        --%>
        <%--
        end of the display
        --%>
        <%--
        <onlineshop:OrderDisplay xslt="orderlist_xslt_admin.xsl">
            <jsp:getProperty name="orderList" property="xml">
        </onlineshop:OrderDisplay>
        --%>
        <a href="admin?action=display"><h4>back</h4></a>
    </body>
</html>
