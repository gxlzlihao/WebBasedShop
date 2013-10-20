/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.*;
import java.sql.*;
import java.io.*;

//made by lihao
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;
//made by liaho

/**
 *
 * @author gxlzlihao
 */
public class ProfileBean {
    private String url = null;
    
    private String user_name;
    private String user_password;
    private String name;
    private String address;
    private String zip;
    private String city;
    private String country;
    
    private HashMap<String,Boolean> role = null;
    
    public ProfileBean(String _url){
        url = _url;
    }

    /**
     * @return the user_name
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * @param user_name the user_name to set
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /**
     * @return the user_password
     */
    public String getUser_password() {
        return user_password;
    }

    /**
     * @param user_password the user_password to set
     */
    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip the zip to set
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }
    
    public void populate(String _user_name) throws Exception{
        //return a profile instance from the database using a user name
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try{
            //find the information from the database
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            
            stmt = conn.createStatement();
            String sql;
            sql ="SELECT * from  USERS where USER_NAME = " +
                                             "'" + _user_name + "'";
            rs= stmt.executeQuery(sql);

	    // analyze the result set
	    
	    rs.next();
            user_name = _user_name;
            user_password = rs.getString("user_pass");
            name = rs.getString("name");
            address = rs.getString("street_address");
            zip = rs.getString("zip_code");
            city = rs.getString("city");
            country = rs.getString("country");
        }
        catch(SQLException sqle) {
            throw new Exception(sqle);
        }
        
        finally{
            try{
		rs.close();
            }
            catch(Exception e) {}
            try{
		stmt.close();
            }
	    catch(Exception e) {}
            try {
		conn.close();
            }
            catch(Exception e){}
        }
    };
    
    public boolean testUser(String u) throws Exception{
        //to test if a user is in the database
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try{
	    Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection(url);
            
            stmt = conn.createStatement();
            String sql;
            sql ="SELECT NAME from USERS WHERE USER_NAME = " + "'" + u + "'";
            rs= stmt.executeQuery(sql);

	    // check if we got any result set
	    
	    boolean b = rs.next();
            return b;
       }   
	catch(SQLException sqle){
            throw new Exception(sqle);
	}
        finally{
 	    try{
		rs.close();
            }
            catch(Exception e) {}
            try{
		stmt.close();
            }
	    catch(Exception e) {}
            try {
		conn.close();
            }
            catch(Exception e){}
        }
    }

    /**
     * @return the role
     */
    public HashMap<String,Boolean> getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(HashMap<String,Boolean> role) {
        this.role = role;
    }
    
    // return all roles that we have, return a MAP with names
    // and the flag set to false

     public HashMap<String,Boolean> getRoles() throws Exception {

        Connection conn =null;
        Statement stmt = null;
        ResultSet rs=null;
        HashMap<String,Boolean> role = new HashMap<String,Boolean>();
        
        //made by lihao
        DataSource ds = null;
        Context initContext;
        Context envContext;
        //the end

        try{
            
            //Class.forName("com.mysql.jdbc.Driver");
            
            //conn=DriverManager.getConnection(url, "root", "");
            initContext = new InitialContext();
            envContext = (Context)initContext.lookup("java:/comp/env");
            ds = (DataSource)envContext.lookup("OnlineShopDatabase");
            conn = ds.getConnection();
            
            stmt = conn.createStatement();
            String sql;
            sql ="SELECT * from USER_ROLES";
            rs= stmt.executeQuery(sql);
            while (rs.next()) {
		String r = rs.getString("ROLE_NAME");
		if(!role.containsKey(r))role.put(r,false);
            }
            return role;
	}   
	catch(SQLException sqle){
            throw new Exception(sqle);
	}
        finally{
 	    try{
		rs.close();
            }
            catch(Exception e) {}
            try{
		stmt.close();
            }
	    catch(Exception e) {}
            try {
		conn.close();
            }
            catch(Exception e){}
        }
     }
    
}
