/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.*;

/**
 *
 * @author gxlzlihao
 */
public class ShoppingBean {
    private Collection cart;
    
    public ShoppingBean(){
        cart =new ArrayList();
    }
    
    public void addProduct(ProductBean pb, int quantity){
        //to add a new product into the current cart
        Object newItem[] = null;
        ProductBean tmpBean = null;
        
        if(cart.isEmpty()){
            newItem = new Object[2];
            newItem[0] = pb;
            newItem[1] = new Integer(quantity);
            cart.add(newItem);
        }
        
        //otherwise we check if the product is in the cart
        
        else{
            boolean found = false;
            Object tmpArr[] = null;
            Iterator iter = cart.iterator();
            while(iter.hasNext()){
                tmpArr = (Object[])iter.next();
                
                if( ((ProductBean)tmpArr[0]).getId() == pb.getId() ){
                    //yes, we find the product we want
                    Integer tmpAntal = (Integer)tmpArr[1];
		    tmpArr[1]=new Integer(tmpAntal.intValue() + quantity); 
		    found= true;
                }
            }
            
            //the product we want to add is not in the cart
            if(!found){
                newItem = new Object[2];
                newItem[0] = pb;
                newItem[1] = new Integer(quantity);
                cart.add(newItem);
            }
        }
    }
    
    public void removeProduct(int id, int quantity){
        //to remove the product 
        if(!cart.isEmpty()){
            Iterator iter = cart.iterator();
            Object tmpArr[];
            
            while(iter.hasNext()){
                //find the element we need to remove
                tmpArr = (Object[])iter.next();
                
                if( ((ProductBean)tmpArr[0]).getId() == id ){
                    //this is the item we need to remove
                    
                    Integer tmpAntal = (Integer)tmpArr[1];

		    // if all copies removed, remove the book
		    // from the cart

                    if(tmpAntal.intValue()<=quantity){
                        iter.remove();
                    }
                    else{

			// else reduce quantity

                        tmpArr[1]=new Integer(tmpAntal.intValue()-quantity);
                    }
                    
                }
            }
        }
    }
    
    //output the XML format result
    
    public String getXml(){
        StringBuffer buff = new StringBuffer();
        
        Iterator iter = cart.iterator();
        Object objBuff[] = null;
        buff.append("<shoppingcart>");
        
        while(iter.hasNext()){
            objBuff =(Object[])iter.next();
            buff.append("<order>");
            buff.append(((ProductBean)objBuff[0]).getXml());
            buff.append("<quantity>");
            buff.append(((Integer)objBuff[1]).intValue());
            buff.append("</quantity>");
            buff.append("</order>");            
        }
        buff.append("</shoppingcart>");
        return buff.toString();
    }
    
    //empty the cart
    
    public void clear(){
        cart.clear();
    }
    
    public Collection getCart(){
        return cart;
    }
}
