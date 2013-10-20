<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : newItemList_xslt.xsl
    Created on : June 17, 2013, 12:53 AM
    Author     : gxlzlihao
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <html>
            <head>
                <title>newItemList_xslt_admin.xsl</title>
            </head>
            <body>
                <xsl:template match="newItemList">
                    <table border="0">
                        <tr bgcolor="green" cellspacing="0">
                            <td>
                                <strong>The Name of The Item</strong>
                            </td>
                            <td>
                                <strong>The Price of Item</strong>
                            </td>
                            <td>
                                <strong>Quantity</strong>
                            </td>
                        </tr>
                        <xsl:apply-templates select='item'/>
                    </table>
                </xsl:template>
                
                <xsl:template match="item">
                    <table border="0">
                        <tr bycolor="#FFDC75" cellspacing="0">
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
                        <!--<xsl:apply-templates/>-->
                    </table>
                </xsl:template>
                
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
