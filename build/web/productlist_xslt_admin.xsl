<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : productlist_xslt_admin.xsl
    Created on : June 16, 2013, 2:29 PM
    Author     : gxlzlihao
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="productlist">
        <table border="0">
            <tr bgcolor="silver" cellspacing="0">
                <td>
                    <strong>The Id of The Product</strong>
                </td>
                <td>
                    <strong>Name of Product</strong>
                </td>
                <td>
                    <strong>Price</strong>
                </td>
                <xsl:for-each select="product">
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
                        <!--<td>
                            <xsl:element name="input"> 
                                <xsl:attribute name="size">2</xsl:attribute>
                                <xsl:attribute name="type">text</xsl:attribute>
                                <xsl:attribute name="value">1</xsl:attribute>
                                <xsl:attribute name="name">quantity</xsl:attribute>
                            </xsl:element>        
                        </td>-->
                        <td>
                
                            <xsl:element name="a">
                                <xsl:attribute name="href">
                                    <xsl:text disable-output-escaping="yes"><![CDATA[admin?action=product_detail&productid=]]></xsl:text>
                                    <xsl:value-of select="id"/>
                                </xsl:attribute>
                                <xsl:text>Detail</xsl:text>
                            </xsl:element>
                        </td>
                        <td>
                            <xsl:element name="a">
                                <xsl:attribute name="href">
                                    <xsl:text disable-output-escaping="yes"><![CDATA[admin?action=remove_product&productid=]]></xsl:text>
                                    <xsl:value-of select="id"/>
                                </xsl:attribute>
                                <xsl:text>Remove</xsl:text>
                            </xsl:element>
                        </td>
                        <td>
                            <xsl:element name="input"> <!--A ordinary input in XSLT-->
                                <xsl:attribute name="type">hidden</xsl:attribute>
                                <xsl:attribute name="value">
                                    <xsl:value-of select="id"/>
                                </xsl:attribute>
                                <xsl:attribute name="name">productid</xsl:attribute>
                            </xsl:element>
    
                            <input type="hidden" name="action" value="add"/>
                        </td>
                    </tr>
                </xsl:for-each>
            </tr>
        </table>
    </xsl:template>
    <!--
    <xsl:template match="productlist">
        <table border="0">
            <tr bgcolor="silver" cellspacing="0">
                <td>
                    <strong>The Id of The Product</strong>
                </td>
                <td>
                    <strong>Name of Product</strong>
                </td>
                <td>
                    <strong>Price</strong>
                </td>
            </tr>
            <xsl:apply-templates/>
        </table>
    </xsl:template>
  
    <xsl:template match="product">
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
                
                    <xsl:element name="a">
                        <xsl:attribute name="href">
                            <xsl:text disable-output-escaping="yes"><![CDATA[admin?action=product_detail&productid=]]></xsl:text>
                            <xsl:value-of select="id"/>
                        </xsl:attribute>
                        <xsl:text>Detail</xsl:text>
                    </xsl:element>
                </td>
                <td>
                    <xsl:element name="a">
                        <xsl:attribute name="href">
                            <xsl:text disable-output-escaping="yes"><![CDATA[admin?action=remove_product&productid=]]></xsl:text>
                            <xsl:value-of select="id"/>
                        </xsl:attribute>
                        <xsl:text>Remove</xsl:text>
                    </xsl:element>
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
