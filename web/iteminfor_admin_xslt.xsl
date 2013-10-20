<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : iteminfor_admin_xslt.xsl.xsl
    Created on : June 19, 2013, 10:52 AM
    Author     : gxlzlihao
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="itemList">
        <!--the begin of the itemList-->
        <table border="0">
            <tr bgcolor="silver" cellspacing="0">
                <td>
                    <strong>The Id of The Item</strong>
                </td>
                <td>
                    <strong>Name of Item</strong>
                </td>
                <td>
                    <strong>Price</strong>
                </td>
                <td>
                    <strong>Stock</strong>
                </td>
                <td>
                    <strong>Quantity</strong>
                </td>
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
                <td>
                    <xsl:element name="input"> <!--A ordinary input in XSLT-->
                        <xsl:attribute name="size">2</xsl:attribute>
                        <xsl:attribute name="type">text</xsl:attribute>
                        <xsl:attribute name="value">0</xsl:attribute>
                        <xsl:attribute name="name">quantity</xsl:attribute>
                    </xsl:element>
                </td>
                <td>
                    <xsl:element name="input"> <!--A ordinary input in XSLT-->
                        <xsl:attribute name="type">hidden</xsl:attribute>
                        <xsl:attribute name="value">
                            <xsl:value-of select="id"/>
                        </xsl:attribute>
                        <xsl:attribute name="name">itemsid</xsl:attribute>
                    </xsl:element>
                </td>
                <td>
                    <xsl:element name="input"> <!--A ordinary input in XSLT-->
                        <xsl:attribute name="type">hidden</xsl:attribute>
                        <xsl:attribute name="value">
                            <xsl:value-of select="price"/>
                        </xsl:attribute>
                        <xsl:attribute name="name">singlePrice</xsl:attribute>
                    </xsl:element>
                </td>
            </tr>
                
            </xsl:for-each>
        </table>
        <!--<xsl:apply-templates/>-->
    </xsl:template>
    <!--for back up-->
    <!--
    <xsl:template match="item">
        <table border="0" name="listItemInfo">
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
                <td>
                    <xsl:element name="input"> 
                        <xsl:attribute name="size">2</xsl:attribute>
                        <xsl:attribute name="type">text</xsl:attribute>
                        <xsl:attribute name="value">0</xsl:attribute>
                        <xsl:attribute name="name">quantity</xsl:attribute>
                    </xsl:element>
                </td>
                <td>
                    <xsl:element name="input"> 
                        <xsl:attribute name="type">hidden</xsl:attribute>
                        <xsl:attribute name="value">
                            <xsl:value-of select="id"/>
                        </xsl:attribute>
                        <xsl:attribute name="name">itemsid</xsl:attribute>
                    </xsl:element>
                </td>
                <td>
                    <xsl:element name="input"> 
                        <xsl:attribute name="type">hidden</xsl:attribute>
                        <xsl:attribute name="value">
                            <xsl:value-of select="price"/>
                        </xsl:attribute>
                        <xsl:attribute name="name">singlePrice</xsl:attribute>
                    </xsl:element>
                </td>
            </tr>
        </table>
        
    </xsl:template>
-->
</xsl:stylesheet>
