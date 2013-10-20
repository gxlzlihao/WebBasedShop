/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.*;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

/**
 *
 * @author gxlzlihao
 */
public class OrderBean {
    
  private Connection con;
  private PreparedStatement orderPstmt;
  private PreparedStatement orderItemPstmt;
  private PreparedStatement stmt = null;
  private ResultSet rs=null;

  private String url;
  private ShoppingBean sb;
  
  private String buyerName;
  private String shippingAddress;
  private String shippingZipcode;
  private String shippingCity;
  
  private String buyerUserName;
  private String orderDate;
  private int paid;
  private int orderId;
  

    private static String orderSQL;
    private static String orderItemSQL;
   /* 
  public OrderBean(String _url, ShoppingBean _sb, String _buyerName, int _paid){
      url = _url;
      sb = _sb;
      buyerName = _buyerName;
      shippingAddress = null;
      shippingZipcode = null;
      shippingCity = null;
      paid = _paid;
  };
*/
  public OrderBean(String _url, ShoppingBean _sb, String _buyerName, String _buyerUserName, int _paid,
		   String _shippingAddress, String _shippingZipcode, 
		   String _shippingCity){
    orderId = -1;
    url = _url;
    sb = _sb;
    buyerName=_buyerName;
    buyerUserName = _buyerUserName;
    paid = _paid;
    shippingAddress=_shippingAddress;
    shippingZipcode=_shippingZipcode;
    shippingCity=_shippingCity;
  }
  
  public OrderBean(int _orderId, String _url, String _buyerName, String _buyerUserName, String _orderDate,
		   String _shippingAddress, String _shippingZipcode, 
		   String _shippingCity){
    orderId = _orderId;
    url = _url;
    sb = null;
    buyerName=_buyerName;
    buyerUserName = _buyerUserName;
    orderDate = _orderDate;
    paid = 1;
    shippingAddress=_shippingAddress;
    shippingZipcode=_shippingZipcode;
    shippingCity=_shippingCity;
  }
  
  public String getXml(){
      StringBuffer out = new StringBuffer();
      out.append("<Order>");
      out.append("<order_id>");
      out.append(orderId);
      out.append("</order_id>");
      out.append("<user_id>");
      out.append(buyerName);
      out.append("</user_id>");
      out.append("<user_name>");
      out.append(buyerUserName);
      out.append("</user_name>");
      out.append("<order_date>");
      out.append(orderDate);
      out.append("</order_date>");
      out.append("<shipping_address>");
      out.append(shippingAddress);
      out.append("</shipping_address>");
      out.append("<shipping_zipcode>");
      out.append(shippingZipcode);
      out.append("</shipping_zipcode>");
      out.append("<shipping_city>");
      out.append(shippingCity);
      out.append("</shipping_city>");
      out.append("</Order>");
      return out.toString();
  }

  /**
   * Saves an order in the database
   */
  public void saveOrder() throws Exception{
  /*orderSQL="INSERT INTO ORDERS(BUYER_NAME,";
    orderSQL += " SHIPPING_ADRESS, SHIPPING_ZIPCODE, SHIPPING_CITY)";
    orderSQL += " VALUES(?,?,?,?)";*/
    
    orderSQL = "INSERT INTO orders(user_id, user_name, ";
    orderSQL+= " orderdate, paid, delivered, shippingAddress, shippingZipcode, shippingCity)";
    orderSQL+= " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    
    /*orderSQL = "INSERT INTO ORDERS (USER_ID, USER_NAME";
    orderSQL+= " ORDERDATE, PAID, DELIVERED, SHIPPINGADDRESS, SHIPPINGZIPCODE, SHIPPINGCITY)";
    orderSQL+= " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";*/
    try{

	// load the driver and get a connection

	Class.forName("com.mysql.jdbc.Driver");
	con = DriverManager.getConnection(url);

	// turn off autocommit to handle transactions yourself
        
        //to get the current time
        Calendar rightNow = Calendar.getInstance();//getInstance返回一个Calendar对象，并由当前时间初始化
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");    //日期格式化格式
        orderDate = format.format(rightNow.getTime());

	con.setAutoCommit(false);
	orderPstmt = con.prepareStatement(orderSQL);
	orderPstmt.setString(1, buyerUserName);
        orderPstmt.setString(2, buyerName);
        orderPstmt.setString(3, orderDate);
        orderPstmt.setInt(4, paid);
        if(shippingAddress == null)
            orderPstmt.setInt(5, 0);
        else
            orderPstmt.setInt(5, 1);
	orderPstmt.setString(6, shippingAddress);
	orderPstmt.setString(7, shippingZipcode);
	orderPstmt.setString(8, shippingCity);
	boolean reExe = orderPstmt.execute();

	// now handle all items in the cart
        
	saveOrderItems();
	sb.clear();
	con.commit();  // end the transaction
    }
    catch(Exception e){
	try{
	    con.rollback();    // failed, rollback the database
	}
	catch(Exception ee){}
	throw new Exception("Error saving Order", e);
    }
    finally{
	try {
	    rs.close();
	}
	catch(Exception e) {}       
	try {
	    stmt.close();
	}
	catch(Exception e) {}
	try{
	    orderPstmt.close();
	}
	catch(Exception e){}
	try{
	    orderItemPstmt.close();
	}
	catch(Exception e){}
	try{
	    con.close();
	}
	catch(Exception e){}
    }
  }

/**
 * Saves the different items in the order
 */
  private void saveOrderItems() throws Exception{

      // get the value of the last stored AUTO_INCREMENT variable
      // i. e. ORDER_ID

      orderItemSQL="INSERT INTO order_product(order_id, ";
      orderItemSQL += "product_id, quantity) VALUES (?,?,?)";
      stmt = con.prepareStatement("SELECT LAST_INSERT_ID()");
      rs = stmt.executeQuery();
      rs.next();
      int orderId=rs.getInt(1);

      Iterator iter = ((Collection)sb.getCart()).iterator();
      ProductBean pb = null;
      Object tmpArr[];

      //Loop over the entire collection, i.e the entire shopping cart
 
      orderItemPstmt = con.prepareStatement(orderItemSQL);
      while(iter.hasNext()){
          
	  tmpArr = (Object[])iter.next();
	  pb = (ProductBean)tmpArr[0];
          orderItemPstmt.setInt(1,orderId);
          orderItemPstmt.setInt(2,pb.getId());
          orderItemPstmt.setInt(3,((Integer)tmpArr[1]).intValue());  
          orderItemPstmt.execute();
      }
  }
    
}
