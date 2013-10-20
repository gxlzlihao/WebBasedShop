<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : productinfo_xslt_admin.xsl
    Created on : June 20, 2013, 10:32 PM
    Author     : gxlzlihao
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    
    <h2>Product`s Information</h2>
    
    <xsl:template match="productInfor">
        <table border="0">
            <tr bgcolor="silver" cellspacing="0">
                <td>Product`s Id</td>
                <td>Product`s Name</td>
                <td>Product`s Price</td>
            </tr>
            <xsl:for-each select='product'>
                <tr bgcolor="#FFDC75">
                    <td>
                        <xsl:value-of select="product_id"/>
                    </td>
                    <td>
                        <xsl:value-of select="product_name"/>
                    </td>
                    <td>
                        <xsl:value-of select="product_price"/>
                    </td>
                </tr>
            </xsl:for-each>
        </table>
        <xsl:apply-templates select='items'/>
    </xsl:template>
    <!--
    <xsl:template match="product">
        <table border="0">
            <tr bgcolor="#FFDC75">
                <td>
                    <xsl:value-of select="product_id"/>
                </td>
                <td>
                    <xsl:value-of select="product_name"/>
                </td>
                <td>
                    <xsl:value-of select="product_price"/>
                </td>
            </tr>
        </table>
    </xsl:template>
    -->
    <h2>Items` Information</h2>
    
    <xsl:template match="items">
        <table border="0">
            <tr bgcolor="silver" cellspacing="0">
                <td>Item`s Id</td>
                <td>Item`s Name</td>
                <td>Item`s Price</td>
                <td>One Product Consists ? Items</td>
            </tr>
            <xsl:for-each select='item'>
                <tr bgcolor="#FFDC75">
                <td>
                    <xsl:value-of select="id"/>
                </td>
                <td>
                    <xsl:value-of select="name"/>
                </td>
                <td>
                    <xsl:value-of select="price"/>
                </td>
                <td>
                    <xsl:value-of select="stock"/>
                </td>
            </tr>
            </xsl:for-each>
        </table>
        <!--<xsl:apply-templates/>-->
    </xsl:template>
    <!--
    <xsl:template match="item">
        <table border="0">
            <tr bgcolor="#FFDC75">
                <td>
                    <xsl:value-of select="id"/>
                </td>
                <td>
                    <xsl:value-of select="name"/>
                </td>
                <td>
                    <xsl:value-of select="price"/>
                </td>
                <td>
                    <xsl:value-of select="stock"/>
                </td>
            </tr>
        </table>
    </xsl:template>-->

<!--for back up-->
<!--
    <xsl:template match="productInfor">
        <table border="0">
            <tr bgcolor="silver" cellspacing="0">
                <td>Product`s Id</td>
                <td>Product`s Name</td>
                <td>Product`s Price</td>
            </tr>
        </table>
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="product">
        <table border="0">
            <tr bgcolor="#FFDC75">
                <td>
                    <xsl:value-of select="product_id"/>
                </td>
                <td>
                    <xsl:value-of select="product_name"/>
                </td>
                <td>
                    <xsl:value-of select="product_price"/>
                </td>
            </tr>
        </table>
    </xsl:template>
    
    <h2>Items` Information</h2>
    
    <xsl:template match="items">
        <table border="0">
            <tr bgcolor="silver" cellspacing="0">
                <td>Item`s Id</td>
                <td>Item`s Name</td>
                <td>Item`s Price</td>
                <td>One Product Consists ? Items</td>
            </tr>
        </table>
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="item">
        <table border="0">
            <tr bgcolor="#FFDC75">
                <td>
                    <xsl:value-of select="id"/>
                </td>
                <td>
                    <xsl:value-of select="name"/>
                </td>
                <td>
                    <xsl:value-of select="price"/>
                </td>
                <td>
                    <xsl:value-of select="stock"/>
                </td>
            </tr>
        </table>
    </xsl:template>
    -->
</xsl:stylesheet>
