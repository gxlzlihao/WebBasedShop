/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author gxlzlihao
 */
public class ProductBean {
    private int id;
    private String name;
    private float price;
    
    public int getId(){return id;};
    public void setId(int _id){id = _id;};

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String _name) {
        this.name = _name;
    }

    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(float _price) {
        this.price = _price;
    }
    
    /**
     * @param xml the xml to get
     */
    public String getXml(){
        
      StringBuffer xmlOut = new StringBuffer();
      
      xmlOut.append("<product>");
      xmlOut.append("<id>");
      xmlOut.append(id);
      xmlOut.append("</id>");      
      xmlOut.append("<name><![CDATA[");
      xmlOut.append(name);
      xmlOut.append("]]></name>");
      xmlOut.append("<price><![CDATA[");
      xmlOut.append(price);
      xmlOut.append("]]></price>");
      xmlOut.append("<price>");
      xmlOut.append(price);      
      xmlOut.append("</price>");
        //xmlOut.append("<description><![CDATA[");
        //xmlOut.append(description);
        //xmlOut.append("]]></description>");
      xmlOut.append("</product>");
      
      return xmlOut.toString();
      
    }
}
