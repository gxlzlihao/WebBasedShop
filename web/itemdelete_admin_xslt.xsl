<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : itemdelete_admin_xslt.xsl
    Created on : June 17, 2013, 4:05 PM
    Author     : gxlzlihao
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <!--
<xsl:template match="/">
<html>
    <head>
        <title>itemdelete_admin_xslt.xsl</title>
    </head>
    <body>-->
    <xsl:template match="itemList">
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
            </tr>
            <xsl:apply-templates/>
        </table>
    </xsl:template>
                
    <xsl:template match="item">
        <form method="post" action="admin">
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
                    <input type="submit" value="remove"/>
                    <input type="hidden" name="action" value="remove"/>
                    <xsl:element name="input"> <!--A ordinary input in XSLT-->
                        <xsl:attribute name="type">hidden</xsl:attribute>
                        <xsl:attribute name="value">
                            <xsl:value-of select="id"/>
                        </xsl:attribute>
                        <xsl:attribute name="name">itemid</xsl:attribute>
                    </xsl:element>
                </td>
                
            </tr>
                        
                       
    
        </form>
    </xsl:template>
    <!--
                </body>
            </html>
        </xsl:template>
    -->
</xsl:stylesheet>
