/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author gxlzlihao
 */
public class ItemBean {

    private int id=-1;
    private String name;
    private float price;
    private int stock;
    //

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    /**
     * @param xml the xml to get
     */
    public String getXml(){
        
      StringBuffer xmlOut = new StringBuffer();
      
      xmlOut.append("<item>");
      if(id>0){
        xmlOut.append("<id>");
        xmlOut.append(id);
        xmlOut.append("</id>");
      }
      xmlOut.append("<name>");
      xmlOut.append(name);
      xmlOut.append("</name>");
      xmlOut.append("<price>");
      xmlOut.append(price);
      xmlOut.append("</price>");
      xmlOut.append("<stock>");
      xmlOut.append(stock);
      xmlOut.append("</stock>");
      xmlOut.append("</item>");
      
      return xmlOut.toString();
      
    }
}
