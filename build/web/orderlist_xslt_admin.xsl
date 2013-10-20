<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : orderlist_xslt_admin.xsl
    Created on : June 21, 2013, 10:41 PM
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
    <xsl:template match="OrderInfoList">
        <xsl:message>begin of the OrderInfoList template</xsl:message>
        <xsl:for-each select="OrderInfo">
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
            </table><br>
            <xsl:apply-templates select="productList"/>
        </xsl:for-each>
    </xsl:template>
    
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
                        <xsl:value-of select="product/id"/>
                    </td>
                    <td>
                        <xsl:value-of select="product/name"/>
                    </td>
                    <td>
                        <xsl:value-of select="product/price"/>
                    </td>
                    <td>
                        <xsl:value-of select="quantity"/>
                    </td>
                </tr>
            </xsl:for-each>
        </table>
    </xsl:template>
    <!--end of new template-->
    <!--
    <xsl:template match="OrderInfoList">
        <xsl:message>the first template</xsl:message>
        <xsl:apply-templates select="OrderInfo/Order"/>
        <xsl:apply-templates select="OrderInfo/productList"/>
    </xsl:template>
    
    <xsl:template match="OrderInfo/Order">
        <xsl:message>the second template</xsl:message>
        <xsl:for-each select=".">
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
                        <xsl:value-of select="order_id"/>
                    </td>
                    <td>
                        <xsl:value-of select="user_id"/>
                    </td>
                    <td>
                        <xsl:value-of select="user_name"/>
                    </td>
                    <td>
                        <xsl:value-of select="order_date"/>
                    </td>
                    <td>
                        <xsl:value-of select="shipping_address"/>
                    </td>
                    <td>
                        <xsl:value-of select="shipping_zipcode"/>
                    </td>
                    <td>
                        <xsl:value-of select="shipping_city"/>
                    </td>
                </tr>
            </table>
        </xsl:for-each>
    </xsl:template>
    
        <xsl:template match="OrderInfo/productList">
            <xsl:message>the third template</xsl:message>
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
    -->
</xsl:stylesheet>
