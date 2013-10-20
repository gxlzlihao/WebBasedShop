/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.beans.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author gxlzlihao
 */
class proRecord {
    public proRecord(int _num, ProductBean _product){
        num = _num;
        product = _product;
    }
    
    int num;    //the number of the products which are required in a order
    ProductBean product;
}

public class OrderListBean implements Serializable {
    
    public static final String PROP_SAMPLE_PROPERTY = "sampleProperty";
    private String sampleProperty;
    private PropertyChangeSupport propertySupport;
    
    private String url;
    HashMap<OrderBean, ArrayList<proRecord>> orderInfoList = null;
    
    
    public OrderListBean() {
        propertySupport = new PropertyChangeSupport(this);
    }
    
    public OrderListBean(String _url) throws Exception{
        propertySupport = new PropertyChangeSupport(this);
        url = _url;
        orderInfoList = new HashMap<OrderBean, ArrayList<proRecord>> ();
        
        //build the order list
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            String sql_1 = "SELECT * FROM orders;";
            String sql_2 = "SELECT * FROM order_product WHERE order_product.order_id = ?";
            String sql_3 = "SELECT * FROM product WHERE product.id = ?";
            rs1 = stmt.executeQuery(sql_1);
            stmt2 = conn.prepareStatement(sql_2);
            stmt3 = conn.prepareStatement(sql_3);
            while(rs1.next()){
                //query each order
                String order_id = rs1.getString("id");
                String user_id = rs1.getString("user_id");
                String user_name = rs1.getString("user_name");
                String order_date = rs1.getString("orderdate");
                String shippingAddress = rs1.getString("shippingAddress");
                String shippingZipcode = rs1.getString("shippingZipcode");
                String shippingCity = rs1.getString("shippingCity");
                OrderBean ob = new OrderBean(Integer.parseInt(order_id), url, user_id, user_name, order_date, shippingAddress,
                        shippingZipcode, shippingCity);
                
                stmt2.setString(1, order_id);
                rs2 = stmt2.executeQuery();
                ArrayList tempList = new ArrayList();
                while(rs2.next()){
                    //query each product
                    String product_id = rs2.getString("product_id");
                    int quantity = Integer.parseInt(rs2.getString("quantity"));
                    stmt3.setString(1, product_id);
                    rs3 = stmt3.executeQuery();
                    if(rs3.next()){
                        //record the product`s information
                        ProductBean pb = new ProductBean();
                        pb.setId(Integer.parseInt(product_id));
                        pb.setName(rs3.getString("name"));
                        pb.setPrice(Float.parseFloat(rs3.getString("price")));
                        tempList.add(new proRecord(quantity, pb));
                    }
                    else
                        throw new SQLException("no corresponding product in the product table!");
                }
                orderInfoList.put(ob, tempList);
            }
        }catch(SQLException sqle){
            throw new Exception(sqle);
        }
        
        finally{
            try{
                rs1.close();
                rs2.close();
                rs3.close();
            }catch(Exception e){}
            try{
                stmt.close();
                stmt2.close();
                stmt3.close();
            }catch(Exception e){}
            try{
                conn.close();
            }catch(Exception e){}
        }
    }
    
    public void refresh() throws Exception{
        //refresh the records stored in the database
        orderInfoList.clear();
        
        //build the order list
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            String sql_1 = "SELECT * FROM orders;";
            String sql_2 = "SELECT * FROM order_product WHERE order_product.order_id = ?";
            String sql_3 = "SELECT * FROM product WHERE product.id = ?";
            rs1 = stmt.executeQuery(sql_1);
            stmt2 = conn.prepareStatement(sql_2);
            stmt3 = conn.prepareStatement(sql_3);
            while(rs1.next()){
                //query each order
                String order_id = rs1.getString("id");
                String user_id = rs1.getString("user_id");
                String user_name = rs1.getString("user_name");
                String order_date = rs1.getString("orderdate");
                String shippingAddress = rs1.getString("shippingAddress");
                String shippingZipcode = rs1.getString("shippingZipcode");
                String shippingCity = rs1.getString("shippingCity");
                OrderBean ob = new OrderBean(Integer.parseInt(order_id), url, user_id, user_name, order_date, shippingAddress,
                        shippingZipcode, shippingCity);
                
                stmt2.setString(1, order_id);
                rs2 = stmt2.executeQuery();
                ArrayList tempList = new ArrayList();
                while(rs2.next()){
                    //query each product
                    String product_id = rs2.getString("product_id");
                    int quantity = Integer.parseInt(rs2.getString("quantity"));
                    stmt3.setString(1, product_id);
                    rs3 = stmt3.executeQuery();
                    if(rs3.next()){
                        //record the product`s information
                        ProductBean pb = new ProductBean();
                        pb.setId(Integer.parseInt(product_id));
                        pb.setName(rs3.getString("name"));
                        pb.setPrice(Float.parseFloat(rs3.getString("price")));
                        tempList.add(new proRecord(quantity, pb));
                    }
                    else
                        throw new SQLException("no corresponding product in the product table!");
                }
                orderInfoList.put(ob, tempList);
            }
        }catch(SQLException sqle){
            throw new Exception(sqle);
        }
        
        finally{
            try{
                rs1.close();
                rs2.close();
                rs3.close();
            }catch(Exception e){}
            try{
                stmt.close();
                stmt2.close();
                stmt3.close();
            }catch(Exception e){}
            try{
                conn.close();
            }catch(Exception e){}
        }
    }
    
    public String getXml(){
        //
        StringBuffer xmlOut = new StringBuffer();
        xmlOut.append("<OrderInfoList>");
        Iterator<OrderBean> iter = orderInfoList.keySet().iterator();
        while(iter.hasNext()){
            xmlOut.append("<OrderInfo>");
            OrderBean ob = iter.next();
            xmlOut.append(ob.getXml());
            ArrayList<proRecord> tempArr = orderInfoList.get(ob);
            Iterator iter2 = tempArr.iterator();
            xmlOut.append("<productList>");
            while(iter2.hasNext()){
                //
                xmlOut.append("<productRecord>");
                proRecord pr = (proRecord)iter2.next();
                xmlOut.append(pr.product.getXml());
                xmlOut.append("<quantity>");
                xmlOut.append(pr.num);
                xmlOut.append("</quantity>");
                xmlOut.append("</productRecord>");
            }
            xmlOut.append("</productList>");
            xmlOut.append("</OrderInfo>");
        }
        xmlOut.append("</OrderInfoList>");
        return xmlOut.toString();
    }
    public String getHtml(){
        //
        StringBuffer htmlOut = new StringBuffer();
        return htmlOut.toString();
    }
    
    public String getSampleProperty() {
        return sampleProperty;
    }
    
    public void setSampleProperty(String value) {
        String oldValue = sampleProperty;
        sampleProperty = value;
        propertySupport.firePropertyChange(PROP_SAMPLE_PROPERTY, oldValue, sampleProperty);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
}
