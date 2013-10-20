/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpSession;

/**
 *
 * @author gxlzlihao description: this bean is used for the administrator to
 * communicate with the database.
 */
public class AdminDBUtilBean {

    private String url;     //url the location of the database

    public AdminDBUtilBean(String _url) {
        url = _url;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /*
     * to authenticate the administrator`s information
     */
    public String authenticateAdmin(String _username, String _password) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        Statement stmt2 = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;

        try {
            //communicate with the database
            // get a database connection and load the JDBC-driver

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);

            // create SQL statements to load the books into the list
            // each book is a BookBean object

            stmt = conn.createStatement();
            stmt2 = conn.createStatement();
            String sql_1 = "SELECT USER_NAME, USER_PASS FROM USERS WHERE USER_NAME = '" + _username + "';";
            rs1 = stmt.executeQuery(sql_1);
            String sql_2 = "select USER_NAME, ROLE_NAME FROM USER_ROLES WHERE USER_NAME = '" + _username + "';";
            rs2 = stmt2.executeQuery(sql_2);

            while (rs1.next()) {
                if (rs1.getString("USER_PASS").equals(_password)) {
                    while (rs2.next()) {
                        if (rs2.getString("ROLE_NAME").equals("admin")) {
                            return "true";
                        }
                    }
                    return "Your current username is not a administrator!";
                } else {
                    return "Your password does not match your username!";
                }
            }
            return "Your username is invalid!";
        } catch (SQLException e) {
            throw new Exception(e);
        } // note the we always try to close all services
        // even if one or more fail to close
        finally {
            try {
                rs1.close();
                rs2.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
                stmt2.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    public boolean addNewItem(ItemBean ib) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        int rs1;
        ResultSet rst = null;

        String name = ib.getName();
        float price = ib.getPrice();
        int stock = ib.getStock();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            //processing
            stmt = conn.createStatement();
            //String sql_1 = "SELECT USER_NAME, USER_PASS FROM USERS WHERE USER_NAME = " + _username + ";";
            String sql_t = "SELECT id, name, price, stock FROM item WHERE item.name='" + name + "';";
            rst = stmt.executeQuery(sql_t);
            if (rst.next()) {
                //the record has already existed
                int temp = 0;
                temp = Integer.parseInt(rst.getString("stock")) + stock;
                String sql_1 = "UPDATE item SET item.stock=" + temp + " WHERE item.name='" + name + "';";
                rs1 = stmt.executeUpdate(sql_1);
                if (rs1 == 0) {
                    throw new SQLException("update fails 1");
                }
            } else {
                //the record does not exist
                String sql_1 = "INSERT INTO item(name, price, stock) VALUES ('" + name + "', " + price + ", " + stock + ");";
                rs1 = stmt.executeUpdate(sql_1);
                if (rs1 == 0) {
                    throw new SQLException("update fails 2");
                }
            }

        } catch (SQLException e) {
            throw new Exception(e);
        }
        
        finally{
            try{
                rst.close();
            }catch(Exception e){}
            try{
                stmt.close();
            }catch(Exception e){}
            try{
                conn.close();
            }catch(Exception e){}
        }
        return true;
    }

    public boolean removeItem(int itemId, int quantity) throws Exception {
        //remove the items you want
        Connection conn = null;
        Statement stmt = null;
        int rs1;
        String sql_d;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            //processing
            stmt = conn.createStatement();
            sql_d = "UPDATE item SET stock=stock-" + quantity + " WHERE item.id=" + itemId + " ;";
            rs1 = stmt.executeUpdate(sql_d);
            if (rs1 == 0) {
                return false;
            }
        } catch (SQLException sqle) {
            throw new Exception(sqle);
            //return false;
        }
        
        finally{
            try{
                stmt.close();
                conn.close();
            }catch(Exception e){}
        }
        return true;
    }
    
    public boolean addItemQuantity(int itemid, int quantity) throws Exception {
        //add item`s quantity
        Connection conn = null;
        Statement stmt = null;
        int rs1;
        String sql_a;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            //processing
            stmt = conn.createStatement();
            sql_a = "UPDATE item SET stock=stock+" + quantity + " WHERE item.id=" + itemid + " ;";
            rs1 = stmt.executeUpdate(sql_a);
            if (rs1 == 0) {
                return false;
            }
        } catch (SQLException sqle) {
            throw new Exception(sqle);
            //return false;
        }
        
        finally{
            try{
                stmt.close();
                conn.close();
            }catch(Exception e){}
        }
        return true;
    }

    public boolean totallyDeleteItem(int itemid) throws Exception {
        
        //totally delete the item from the database according to its id
        Connection conn = null;
        Statement stmt = null;
        int rs1;
        String sql_d;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            //processing
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            //sql_a = "UPDATE item SET stock=stock+" + quantity + " WHERE item.id=" + itemid + " ;";
            sql_d = "DELETE FROM item where item.id="+itemid+";";
            rs1 = stmt.executeUpdate(sql_d);
            conn.commit();
            if (rs1 == 0) {
                return false;
            }
        } catch (SQLException sqle) {
            throw new Exception(sqle);
            //return false;
        }
        
        finally{
            try{
                stmt.close();
                conn.close();
            }catch(Exception e){}
        }
        
        return true;
    }
    
    public boolean addNewProduct(String productName, float totalPrice, Map<Integer, Integer> _itemsInfo) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs1 = null;
        ResultSet rs11 = null;
        HashMap<Integer, Integer> itemsInfo = new HashMap<Integer, Integer>(_itemsInfo);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            //check if the product is in the database already
            String sql_q = "SELECT * FROM product WHERE product.name='" + productName + "';";
            stmt = conn.createStatement();
            rs1 = stmt.executeQuery(sql_q);
            if (rs1.next()) {
                //the record has already exited in the database
                //the new record will cover the old one
                String pid = rs1.getString("id");
                String sql_u = "UPDATE product SET product.price="+totalPrice+" WHERE product.name='"+productName+"';";
                int rs2 = stmt.executeUpdate(sql_u);
                if(rs2==0) return false;
                
                if(!addProductItems(pid, stmt, itemsInfo)) return false;
                
            }else{
                //in the database is no record
                //add new record into the database
                String sql_u = "INSERT INTO product(name, price) VALUES('"+productName+"', "+totalPrice+");";
                int rs2 = stmt.executeUpdate(sql_u);
                if(rs2==0) return false;
                
                String sql_q2 = "SELECT id FROM product WHERE product.name='"+productName+"';";
                rs11 = stmt.executeQuery(sql_q2);
                rs11.next();
                String pid = rs11.getString("id");
                
                if(!addProductItems(pid, stmt, itemsInfo)) return false;
            }
        } catch (SQLException sqle) {
            throw new Exception(sqle);
        }
        
        finally{
            try{
                rs1.close();
                rs11.close();
            } catch(Exception e){}
            try{
                stmt.close();
            }catch(Exception e){}
            try{
                conn.close();
            }catch(Exception e){}
        }
        return true;
    }
    
    private boolean addProductItems(String pid, Statement stmt, HashMap<Integer, Integer> itemsInfo) throws SQLException{
        //add records into the product_item table
        Iterator<Integer> iter = itemsInfo.keySet().iterator();
        while(iter.hasNext()){
            Integer key = iter.next();
            String sql = "INSERT INTO product_item(product_id, item_id, item_num) VALUES("+pid+", "+key+", "+itemsInfo.get(key)+");";
            int rs = stmt.executeUpdate(sql);
            if(rs==0) return false;
        }
        return true;
    }
    
    public boolean removeProduct(int pid) throws Exception{
        Connection conn = null;
        Statement stmt = null;
        Statement stmt2 = null;
        Statement stmt3 = null;
        ResultSet rs = null;
        int rs1;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            stmt3 = conn.createStatement();
            //check if this product has been ordered by some customer
            String sql_q = "SELECT * FROM order_product WHERE order_product.product_id="+pid+";";
            rs = stmt3.executeQuery(sql_q);
            if(rs.next()) return false;
            
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt2 = conn.createStatement();
            
            String sql_2 = "DELETE FROM product_item WHERE product_item.product_id="+pid+";";
            rs1 = stmt.executeUpdate(sql_2);
            //if(rs1==0) return false;
            
            String sql_1 = "DELETE FROM product WHERE product.id="+pid+";";
            rs1 = stmt2.executeUpdate(sql_1);
            //if(rs1==0) return false;
            conn.commit();
        }catch(SQLException sqle){
            throw new Exception(sqle);
        }
        finally{
            try{
                rs.close();
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
        return true;
    }
    
    public void removeOrder(int _orderId) throws Exception{
        //remove the order from the database
        Connection conn = null;
        Statement stmt = null;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            
            String sql_d1 = "DELETE FROM order_product WHERE order_product.order_id="+_orderId+";";
            int rs1 = stmt.executeUpdate(sql_d1);
            String sql_d2 = "DELETE FROM orders WHERE orders.id="+_orderId+";";
            int rs2 = stmt.executeUpdate(sql_d2);
            
        }catch(SQLException sqle){
            throw new Exception(sqle);
        }
        
        finally{
            try{
                stmt.close();
            }catch(Exception e){}
            try{
                conn.close();
            }catch(Exception e){}
        }
    }
    
    public boolean processOrder(int _orderId) throws Exception{
        Connection conn = null;
        Statement stmt = null;
        Statement stmt1 = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;
        PreparedStatement stmt4 = null;
        ResultSet rs1 = null;
        ResultSet rs11 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        
        messageBean mb = new messageBean(url);
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            stmt1 = conn.createStatement();
            String sql_q1 = "SELECT * FROM order_product WHERE order_product.order_id="+_orderId+";";
            String sql_q11 = "SELECT user_id FROM orders WHERE orders.id="+_orderId+";";
            String sql_q2 = "SELECT * FROM product_item WHERE product_item.product_id=?";
            String sql_q3 = "SELECT * FROM item WHERE item.id=?";
            String sql_d = "UPDATE item SET item.stock=item.stock-? WHERE item.id=?";
            stmt2 = conn.prepareStatement(sql_q2);
            stmt3 = conn.prepareStatement(sql_q3);
            stmt4 = conn.prepareStatement(sql_d);
            rs1 = stmt.executeQuery(sql_q1);
            rs11 = stmt1.executeQuery(sql_q11);
            while(rs1.next()){
                //get the user id of the current order
                if(!rs11.next()) return false;
                String user_id = rs11.getString("user_id");
                //
                String product_id = rs1.getString("product_id");
                int num = Integer.parseInt(rs1.getString("quantity"));
                stmt2.setString(1, product_id);
                rs2 = stmt2.executeQuery();
                while(rs2.next()){
                    //check each item
                    int item_id = Integer.parseInt(rs2.getString("item_id"));
                    int item_num = Integer.parseInt(rs2.getString("item_num"));
                    stmt3.setInt(1, item_id);
                    rs3 = stmt3.executeQuery();
                    if(rs3.next()){
                        int stock = Integer.parseInt(rs3.getString("stock"));
                        if(num*item_num>stock){
                            mb.sendMessage(user_id, "Your product(id) "+product_id+" in order(id)"+_orderId+"has no enough items(id) "+item_id+" in the database.");
                            return false;
                        }
                        else{
                            //items stored in database are enough, 
                            //so can safely decrease the items` number
                            stmt4.setInt(1, item_num*num);
                            stmt4.setInt(2, item_id);
                            int res = stmt4.executeUpdate();
                            if(res==0) return false;
                        }
                    }
                    else{
                        //no needed items in the database
                        mb.sendMessage(user_id, "Your product(id)"+product_id+" in order(id)"+_orderId+" does not have item(id)"+item_id+" in the database;");
                        return false;
                    }
                }
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
                stmt4.close();
            }catch(Exception e){}
            try{
                conn.close();
            }catch(Exception e){}
        }
        return true;
    }
}
