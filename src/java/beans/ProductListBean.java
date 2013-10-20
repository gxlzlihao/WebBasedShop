/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.*;
import java.util.*;

/**
 *
 * @author gxlzlihao
 */
public class ProductListBean {

    private Collection productList;
    private String url = null;

    public ProductListBean(String _url) throws Exception {
        url = _url;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        productList = new ArrayList();

        try {
            //communicate with the database
            // get a database connection and load the JDBC-driver

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);

            // create SQL statements to load the books into the list
            // each book is a BookBean object

            stmt = conn.createStatement();
            String sql = "SELECT id, name, price FROM product;";
            rs = stmt.executeQuery(sql);

            // analyze the result set

            while (rs.next()) {

                ProductBean bb = new ProductBean();

                bb.setId(rs.getInt("id"));
                bb.setName(rs.getString("name"));
                bb.setPrice(rs.getFloat("price"));
                productList.add(bb);

            }
        } catch (SQLException sqle) {
            throw new Exception(sqle);
        } // note the we always try to close all services
        // even if one or more fail to close
        finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    //ProductListBean without parameter constructor is only for testing
    //not practical usage
    public ProductListBean() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // return the productlist
    java.util.Collection getProduktLista() {
        return productList;
    }

    // create an XML document from the productlist
    public String getXml() {

        ProductBean bb = null;
        Iterator iter = productList.iterator();
        StringBuffer buff = new StringBuffer();

        buff.append("<productlist>");
        while (iter.hasNext()) {
            bb = (ProductBean) iter.next();
            buff.append(bb.getXml());
        }
        buff.append("</productlist>");

        return buff.toString();
    }

    // search for a product by product ID
    public ProductBean getById(int id) {
        ProductBean bb = null;
        Iterator iter = productList.iterator();

        while (iter.hasNext()) {
            bb = (ProductBean) iter.next();
            if (bb.getId() == id) {
                return bb;
            }
        }
        return null;
    }

    public boolean refresh() throws Exception {
        //refresh the productList
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        productList.clear();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();

            String sql = "SELECT id, name, price FROM product;";
            rs = stmt.executeQuery(sql);

            // analyze the result set

            while (rs.next()) {

                ProductBean pb = new ProductBean();

                pb.setId(rs.getInt("id"));
                pb.setName(rs.getString("name"));
                pb.setPrice(rs.getFloat("price"));
                productList.add(pb);

            }
        } catch (SQLException sqle) {
            throw new Exception(sqle);
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                conn.close();
            } catch (Exception e) {
            }
        }

        return true;
    }
    // a main used for testing, remember that a bean can be run
    // without a container

    /*public static void main(String[] args){
     try{
     ProductListBean plb = new ProductListBean();
     System.out.println(plb.getXml());
     }
     catch(Exception e){
     System.out.println(e.getMessage());
     }
     }*/
}
