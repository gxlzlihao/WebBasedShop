/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author gxlzlihao
 */
public class ItemListBean {

    private String url = null;
    private Collection itemList = null;

    public ItemListBean(String _url) throws Exception {
        url = _url;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        itemList = new ArrayList();

        try {
            //communicate with the database
            // get a database connection and load the JDBC-driver

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);

            // create SQL statements to load the items into the list
            // each book is a ItemBean object

            stmt = conn.createStatement();
            String sql = "SELECT id, name, price, stock FROM item;";
            rs = stmt.executeQuery(sql);

            // analyze the result set

            while (rs.next()) {

                ItemBean ib = new ItemBean();

                ib.setId(rs.getInt("id"));
                ib.setName(rs.getString("name"));
                ib.setPrice(rs.getFloat("price"));
                ib.setStock(rs.getInt("stock"));

                itemList.add(ib);

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

    public void refresh() throws Exception {
        //refresh the records stored in the itemList
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        //empty the itemList
        itemList.clear();
        try {
            //communicate with the database
            // get a database connection and load the JDBC-driver

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);

            // create SQL statements to load the items into the list
            // each book is a ItemBean object

            stmt = conn.createStatement();
            String sql = "SELECT id, name, price, stock FROM item;";
            rs = stmt.executeQuery(sql);

            // analyze the result set

            while (rs.next()) {

                ItemBean ib = new ItemBean();

                ib.setId(rs.getInt("id"));
                ib.setName(rs.getString("name"));
                ib.setPrice(rs.getFloat("price"));
                ib.setStock(rs.getInt("stock"));

                itemList.add(ib);

            }
        } catch (SQLException sqle) {
            throw new Exception(sqle);
        }
        
        //
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

    public ItemListBean() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // return the itemlist
    java.util.Collection getItemLista() {
        return itemList;
    }

    public String getXml() {

        ItemBean ib = null;
        Iterator iter = itemList.iterator();
        StringBuffer buff = new StringBuffer();

        buff.append("<itemList>");
        while (iter.hasNext()) {
            ib = (ItemBean) iter.next();
            buff.append(ib.getXml());
        }
        buff.append("</itemList>");

        return buff.toString();
    }

    // search for a item by item name
    public ItemBean getByName(String _name) {
        ItemBean ib = null;
        Iterator iter = itemList.iterator();

        while (iter.hasNext()) {
            ib = (ItemBean) iter.next();
            if (ib.getName().equals(_name)) {
                return ib;
            }
        }
        return null;
    }
}
