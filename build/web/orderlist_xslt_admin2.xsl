<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : orderlist_xslt_admin2.xsl
    Created on : June 26, 2013, 12:48 PM
    Author     : gxlzlihao
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <!--new template-->
    
        <xsl:for-each select="OrderInfoList/OrderInfo">
            <xsl:message>begin of the OrderInfo/Order</xsl:message>
            <h3>Order</h3>
            <table border="0">
                <tr bgcolor="#FFDC75">
                    <td>OrderId</td>
                    <td>UserId</td>
                    <td>UserName</td>
                    <td>OrderDate</td>
                    <td>Shipping Address</td>
                    <td>Shipping Zipcode</td>
                    <td>Shipping City</td>
                </tr>
                <tr bgcolor="silver" cellspacing="0">
                    <td>
                        <xsl:value-of select="Order/order_id"/>
                    </td>
                    <td>
                        <xsl:value-of select="Order/user_id"/>
                    </td>
                    <td>
                        <xsl:value-of select="Order/user_name"/>
                    </td>
                    <td>
                        <xsl:value-of select="Order/order_date"/>
                    </td>
                    <td>
                        <xsl:value-of select="Order/shipping_address"/>
                    </td>
                    <td>
                        <xsl:value-of select="Order/shipping_zipcode"/>
                    </td>
                    <td>
                        <xsl:value-of select="Order/shipping_city"/>
                    </td>
                </tr>
            </table>
        </xsl:for-each>
     
    
    <xsl:template match="productList">
        <xsl:message>begin of productList</xsl:message>
        <h4>Product List</h4>
        <table border="0">
            <tr bgcolor="#FFDC75">
                <td>Product Id</td>
                <td>Product Name</td>
                <td>Product Price</td>
                <td>Quantity</td>
            </tr>
            <xsl:for-each select="productRecord">
                <tr bgcolor="silver" cellspacing="0">
                    <td>
                        <xsl:value-of select="product/product_id"/>
                    </td>
                    <td>
                        <xsl:value-of select="product/product_name"/>
                    </td>
                    <td>
                        <xsl:value-of select="product/product_price"/>
                    </td>
                    <td>
                        <xsl:value-of select="quantity"/>
                    </td>
                </tr>
            </xsl:for-each>
        </table>
    </xsl:template>
    <!--end of new template-->
</xsl:stylesheet>

