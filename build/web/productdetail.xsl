<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : productdetail.xsl
    Created on : May 23, 2013, 3:59 PM
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
                <title>productdetail.xsl</title>
            </head>
            <body>
                <center>
                <table border="1">
                    <tr bgcolor="yellow" cellspacing="0">
                        <td><strong>ProductID</strong></td>
                        <td><strong>ProductName</strong></td>
                        <td><strong>ProductPrice</strong></td>
                    </tr>
                </table>
                </center>
                <xsl:apply-templates/>

            </body>
        </html>
    </xsl:template>
    
    <xsl:template match="product">
        <center>
                <table border="1">
                        <tr bgcolor="#FFDC75" cellspacing="0">
                            <td><xsl:value-of select="id"/></td>
                            <td><xsl:value-of select="name"/></td>
                            <td><xsl:value-of select="price"/></td>
                        </tr>
                </table>
        </center>
    </xsl:template>

</xsl:stylesheet>
