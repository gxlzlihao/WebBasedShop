<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : itemlist_xslt_admin.xsl
    Created on : June 16, 2013, 3:49 PM
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
            <xsl:for-each select="item">
                <form method="post" action="admin">
                <tr bgcolor="#FFDC75" >
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
                        <input type='text' name='add_quantity' value='0'/>
                        <xsl:element name="input">
                            <xsl:attribute name="type">hidden</xsl:attribute>
                            <xsl:attribute name="value">
                                <xsl:value-of select="id"/>
                            </xsl:attribute>
                            <xsl:attribute name="name">itemid</xsl:attribute>
                        </xsl:element>
    
                        <input type="hidden" name="action" value="add_item_quantity"/>
                    </td>
                    <td>
                        <input type='submit' value='add'/>
                    </td>
                    <td>
                            <xsl:element name="a">
                                <xsl:attribute name="href">
                                    <xsl:text disable-output-escaping="yes"><![CDATA[admin?action=totally_delete_item&itemid=]]></xsl:text>
                                    <xsl:value-of select="id"/>
                                </xsl:attribute>
                                <xsl:text>Delete</xsl:text>
                            </xsl:element>
                    </td>
                </tr>
                </form>
            </xsl:for-each>
        </table>
    </xsl:template>
    <!--
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
        <table border="1">
            <tr bgcolor="#FFDC75" >
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
    
            <xsl:element name="input"> 
                <xsl:attribute name="type">hidden</xsl:attribute>
                <xsl:attribute name="value">
                    <xsl:value-of select="id"/>
                </xsl:attribute>
                <xsl:attribute name="name">productid</xsl:attribute>
            </xsl:element>
    
                <input type="hidden" name="action" value="add"/>
            </table>
        </xsl:template>
    -->
</xsl:stylesheet>
