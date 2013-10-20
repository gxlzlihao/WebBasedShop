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
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author gxlzlihao
 * This bean is used to store the information of the new product and its items.
 */
public class ProductInfoBean {
    int pid;
    private String url;
    private String name;
    private float product_price;
    //the first argument Integer is item`s id
    //HashMap<Integer, ItemBean> itemsList = null;
    Collection itemsList = null;
    
    //items` information
    
    public ProductInfoBean(String _url, int _pid) throws Exception{
        url = _url;
        pid = _pid;
        
        Connection conn = null;
        Statement stmt = null;
        Statement stmt2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        
        //itemsList = new HashMap<Integer, ItemBean>();
        itemsList = new ArrayList();
        try{
            //get product record
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            stmt2 = conn.createStatement();
            String sql = "SELECT * FROM product WHERE product.id="+pid+";";
            rs = stmt.executeQuery(sql);
            if(rs.next()){
                name = rs.getString("name");
                product_price = Float.parseFloat(rs.getString("price"));
            }
            
            //get items records
            String sql_1 = "SELECT item_id, item_num FROM product_item WHERE product_item.product_id="+pid+";";
            rs = stmt.executeQuery(sql_1);
            while(rs.next()){
                int iid = Integer.parseInt(rs.getString("item_id"));
                int inum = Integer.parseInt(rs.getString("item_num"));
                String sql_2 = "SELECT * FROM item WHERE item.id="+iid+";";
                rs2 = stmt2.executeQuery(sql_2);
                while(rs2.next()){
                    //
                    ItemBean ib = new ItemBean();
                    ib.setId(iid);
                    ib.setName(rs2.getString("name"));
                    ib.setPrice(Float.parseFloat(rs2.getString("price")));
                    ib.setStock(inum);
                    itemsList.add(ib);
                }
            }
        }catch (SQLException sqle){
            throw new Exception(sqle);
        }
        
        finally{
            try{
                conn.close();
            }catch(Exception e){}
            try{
                stmt.close();
                stmt2.close();
            }catch(Exception e){}
            try{
                rs.close();
                rs.close();
            }catch(Exception e){}
        }
    }
    
    public String getXml(){
        StringBuffer xmlOut = new StringBuffer();
        Iterator iter = itemsList.iterator();
        ItemBean ib = null;
        xmlOut.append("<productInfor>");
        xmlOut.append("<product>");
        xmlOut.append("<product_id>");
        xmlOut.append(pid);
        xmlOut.append("</product_id>");
        xmlOut.append("<product_name>");
        xmlOut.append(name);
        xmlOut.append("</product_name>");
        xmlOut.append("<product_price>");
        xmlOut.append(product_price);
        xmlOut.append("</product_price>");
        xmlOut.append("</product>");
        
        xmlOut.append("<items>");
        while(iter.hasNext()){
            //
            ib = (ItemBean)iter.next();
            xmlOut.append(ib.getXml());
        }
        xmlOut.append("</items>");
        
        xmlOut.append("</productInfor>");
        return xmlOut.toString();
        
    }
    
}
