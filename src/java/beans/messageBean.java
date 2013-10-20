/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.beans.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author gxlzlihao
 */
public class messageBean implements Serializable {
    
    public static final String PROP_SAMPLE_PROPERTY = "sampleProperty";
    private String sampleProperty;
    private PropertyChangeSupport propertySupport;
    
    //defined by lihao
    Collection messageList = null;
    private String url = null;
    
    public messageBean(String _url) {
        propertySupport = new PropertyChangeSupport(this);
        url = _url;
        messageList = new ArrayList();
    }
    
    public ArrayList reteriveMessage(String _user_name) throws Exception{
        //retrieve messages from the database and return the message list
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            String sql = "SELECT * FROM messages WHERE receiver='"+_user_name+"';";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                //
                messageList.add(rs.getString("message"));
            }
        }catch(SQLException sqle){
            throw new Exception(sqle);
        }
        
        finally{
            try{
                rs.close();
            }catch(Exception e){}
            try{
                stmt.close();
            }catch(Exception e){}
            try{
                conn.close();
            }catch(Exception e){}
        }
        return (ArrayList)messageList;
    }
    
    public boolean sendMessage(String _user_name, String _message) throws Exception{
        //send message to one user, either client or admin
        Connection conn = null;
        Statement stmt = null;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            String sql = "INSERT INTO messages(receiver, message) VALUES('"+_user_name+"', '"+_message+"');";
            int rs = stmt.executeUpdate(sql);
            if(rs==0)
                return false;
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
        return true;
    }
    
    public boolean sendAdminMessage(String _message) throws Exception{
        try{
            if(!sendMessage("admin",_message))
                return false;
        }catch(Exception e){
            throw e;
        }
        return true;
    }
    
    public boolean clearMessages(String _user_id) throws Exception{
        //clear all the messages for this user
        Connection conn = null;
        Statement stmt = null;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            String sql_d = "DELETE FROM messages WHERE receiver='"+_user_id+"';";
            stmt.executeUpdate(sql_d);
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
        return true;
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
