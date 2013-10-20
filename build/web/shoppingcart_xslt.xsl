<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : shoppingcart_xslt.xsl
    Created on : May 23, 2013, 9:31 PM
    Author     : gxlzlihao
    Description:
        Purpose of transformation follows.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="html"/>

  <xsl:template match="shoppingcart">
  <br /> <br />
  <table border="0" cellspacing="0">
    <tr bgcolor="silver">
    <td colspan="4">
        <strong>Shoppingcart</strong>
    </td>
    <tr bgcolor="silver">
      <td>ProductName</td>
      <td>Quantity</td>
      <td colspan="2">Remove</td>
    </tr>
    </tr>
        <xsl:apply-templates/>
      <tr>
      <td colspan="2">
        <a href="shop?action=checkout">Checkout</a>
      </td>
    </tr>
   </table>
  </xsl:template>
  <xsl:template match="order">
  <form method="post" action="shop">
    <tr>
        <td>
            <xsl:value-of select="product/name"/>
        </td>
        <td align="right">
            <xsl:value-of select="quantity"/>
        </td>

        <td>
            <xsl:element name="input"> <!--A ordinary input in XSLT-->
              <xsl:attribute name="size">2</xsl:attribute>
              <xsl:attribute name="type">text</xsl:attribute>
              <xsl:attribute name="value">1</xsl:attribute>
              <xsl:attribute name="name">quantity</xsl:attribute>
            </xsl:element>        
        </td>
        <td>
            <input type="submit" value="Remove"/>
        </td>
    
      <xsl:element name="input"> <!--A ordinary input in XSLT-->
        <xsl:attribute name="type">hidden</xsl:attribute>
        <xsl:attribute name="value"><xsl:value-of select="product/id"/></xsl:attribute>
        <xsl:attribute name="name">productid</xsl:attribute>
      </xsl:element>
      <xsl:element name="input"> <!--A ordinary input in XSLT-->
        <xsl:attribute name="type">hidden</xsl:attribute>
        <xsl:attribute name="value">remove</xsl:attribute>
        <xsl:attribute name="name">action</xsl:attribute>
      </xsl:element>

    </tr>
    </form>
  </xsl:template>

  
</xsl:stylesheet>

