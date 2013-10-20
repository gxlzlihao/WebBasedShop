/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import beans.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.swing.JOptionPane;

/**
 *
 * @author gxlzlihao
 */
@WebServlet(name = "AdminServlet", urlPatterns = {"/admin"})
public class AdminServlet extends HttpServlet {

    private String jdbc_url = null;
    private String display_admin_page = null;
    private String login_admin_page = null;
    private String product_profile_admin_page = null;
    private String new_item_admin_page = null;
    private String item_delete_admin_page = null;
    private String redirect_admin_page = null;
    private String product_info_admin_page = null;
    private ProductListBean productList = null;
    private ItemListBean itemList = null;
    private OrderListBean orderList = null;
    
    private Collection newItemList = null;
    private Collection newProductList = null;

    /*
     * to initialize the servlet
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        jdbc_url = config.getInitParameter("JDBC_URL");
        display_admin_page = config.getInitParameter("DISPLAY_ADMIN_PAGE");
        login_admin_page = config.getInitParameter("LOGIN_ADMIN_PAGE");
        product_profile_admin_page = config.getInitParameter("NEW_PRODUCT_ADMIN_PAGE");
        new_item_admin_page = config.getInitParameter("NEW_ITEM_ADMIN_PAGE");
        item_delete_admin_page = config.getInitParameter("ITEM_DELETE_PAGE");
        redirect_admin_page = config.getInitParameter("REDIRECT_ADMIN_PAGE");
        product_info_admin_page = config.getInitParameter("PRODUCT_INFO_ADMIN_PAGE");
        
        newItemList = new ArrayList();
        newProductList = new ArrayList();

        //to get a product list from the database using a bean
        try {
            productList = new ProductListBean(jdbc_url);
            itemList = new ItemListBean(jdbc_url);
            orderList = new OrderListBean(jdbc_url);
            
        } catch (Exception e) {
            throw new ServletException(e);
        }

        ServletContext sc = getServletContext();
        sc.setAttribute("productList",productList);
        sc.setAttribute("itemList", itemList);
        sc.setAttribute("orderList", orderList);
        sc.setAttribute("jdbc_url", jdbc_url);
    }

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        //the servlet for the administrator

        HttpSession sess = request.getSession();
        RequestDispatcher rd;
        AdminDBUtilBean dbu = new AdminDBUtilBean(jdbc_url);
        //ProductInfoBean pib = new ProductInfoBean(jdbc_url);

        //to check if we need to open the debug mode
        String debug = request.getParameter("debug");
        if (debug != null && debug.equals("on")) {
            sess.setAttribute("debug", "on");
        } else if (debug != null && debug.equals("off")) {
            sess.removeAttribute("debug");
        }

        if (request.getParameter("action").equals("index")){
            sess.setAttribute("AuthenticationInvalid","");
            rd = request.getRequestDispatcher(login_admin_page);
            rd.forward(request, response);
        }
        else if(request.getParameter("action").equals("display")){
            //go back to display admin page
            rd = request.getRequestDispatcher(display_admin_page);
            rd.forward(request, response);
        }
        else if (request.getParameter("action").equals("login")) {
            //the authenticate the administrator`s information
            
            if (!request.getParameter("admin_username").isEmpty()
                    && !request.getParameter("admin_password").isEmpty()) {
                try {
                    String autres = (dbu.authenticateAdmin(request.getParameter("admin_username"),
                            request.getParameter("admin_password")));
                    if (autres.equals("true")) {
                        //the administrator has been authenticated
                        String currentAdmin = request.getParameter("admin_username");
                        sess.setAttribute("currentAdmin", currentAdmin);

                        rd = request.getRequestDispatcher(display_admin_page);
                        rd.forward(request, response);
                    } else {
                        //the authentication fails
                        sess.setAttribute("AuthenticationInvalid", autres);
                        rd = request.getRequestDispatcher(login_admin_page);
                        rd.forward(request, response);
                    }
                } catch (Exception e) {
                    throw new ServletException(e);
                }
            }
        }
        else if(request.getParameter("action").equals("new_product")){
            //to add new product to the database, and dispatch to product_profile page
            try{
                sess.setAttribute("newProduct", true);
                //sess.setAttribute("newProductInfo", pib);
                
                rd = request.getRequestDispatcher(product_profile_admin_page);
                rd.forward(request, response);
            } catch (Exception e) {
                throw new ServletException(e);
            }
        }
        else if(request.getParameter("action").equals("create_new_product")){
            //to add the new products into the database
            String new_product_name = request.getParameter("new_product_name");
            String[] itemsQuantity = request.getParameterValues("quantity");
            String[] itemsIds = request.getParameterValues("itemsid");
            String[] singlePrices = request.getParameterValues("singlePrice");
            float total = (float) 0.0;
            Map<Integer, Integer> itemsNum = new HashMap<Integer, Integer>();
            
            if(itemsQuantity.length != itemsIds.length || 
                    itemsQuantity.length != singlePrices.length ||
                    itemsIds.length != singlePrices.length){
                throw new ServletException("the lengths are not equal");
            }
            
            //to eliminate the items which have quantity zero
            for(int i=0;i<itemsQuantity.length;i++){
                int number = Integer.parseInt(itemsQuantity[i]);
                int iid = Integer.parseInt(itemsIds[i]);
                float singlePrice = Float.valueOf(singlePrices[i]);
                if(number!=0){
                    //eliminate the current record
                    itemsNum.put(iid, number);
                    total += number*singlePrice;
                }
            }
            
            //to add the new product into the database
            if(!dbu.addNewProduct(new_product_name, total, itemsNum)){
                //database operation fails
                sess.setAttribute("productInforInvalid","Database operation fails");
                rd = request.getRequestDispatcher(product_profile_admin_page);
                rd.forward(request, response);
            }
            
            sess.setAttribute("productInforInvalid", null);
            productList.refresh();
            rd = request.getRequestDispatcher(display_admin_page);
            rd.forward(request, response);
            
        }
        else if(request.getParameter("action").equals("remove_product")){
            //remove the product you want
            if(request.getParameter("productid")!=null){
                //
                int pid = Integer.parseInt(request.getParameter("productid"));
                if(!dbu.removeProduct(pid)){
                    //this product has been ordered by some customer
                    JOptionPane.showMessageDialog(null, "This product has been booked by some customer, so the delete operation fails", 
                            "Delete Information", JOptionPane.INFORMATION_MESSAGE);
                } //throw new ServletException("Something wrong happens when deleting product");
                
                productList.refresh();
                rd = request.getRequestDispatcher(display_admin_page);
                rd.forward(request, response);
            }
            else
                throw new ServletException("productid should not be empty");
        }
        else if(request.getParameter("action").equals("product_detail")){
            //to display the product`s information
            if(request.getParameter("productid")!=null){
                //
                int pid = Integer.parseInt(request.getParameter("productid"));
                sess.setAttribute("pid", pid);
                
                rd = request.getRequestDispatcher(product_info_admin_page);
                rd.forward(request, response);
            }
        }
        else if(request.getParameter("action").equals("new_item")){
            //to add a new item to the database
            try{
                //newItemList is an arraylist
                sess.setAttribute("newItemList",newItemList);
                
                rd = request.getRequestDispatcher(new_item_admin_page);
                rd.forward(request, response);
                
            } catch (Exception e) {
                throw new ServletException(e);
            }
        }
        else if(request.getParameter("action").equals("add_new_item")){
            //add a new item to the newItemList collection
            ArrayList itemArray = (ArrayList) sess.getAttribute("newItemList");
            
            String _name = request.getParameter("new_item_name");
            float _price = Float.valueOf(request.getParameter("new_item_price"));
            int _stock = Integer.valueOf(request.getParameter("new_item_stock"));
            
            ItemBean ib = new ItemBean();
            ib.setName(_name);
            ib.setPrice(_price);
            ib.setStock(_stock);
            
            itemArray.add(ib);
            sess.setAttribute("newItemList", itemArray);
            
            //go back to the 
            rd = request.getRequestDispatcher(new_item_admin_page);
            rd.forward(request, response);
        }
        else if(request.getParameter("action").equals("create_new_items")){
            //submit all the new items
            ArrayList itemArray = (ArrayList) sess.getAttribute("newItemList");
            ItemBean ib = null;
            
            if(!itemArray.isEmpty()){
                Iterator iter = itemArray.iterator();
                while(iter.hasNext()){
                    ib = (ItemBean) iter.next();
                    
                    //call the function in the AdminDBUtilBean
                    if(!dbu.addNewItem(ib))
                        throw new ServletException("Database updation fails");
                }
            }
            
            itemArray.clear();
            sess.setAttribute("newItemList", itemArray);
            itemList.refresh();
            
            //need to refresh the display page
            rd = request.getRequestDispatcher(display_admin_page);
            rd.forward(request, response);
        }
        else if(request.getParameter("action").equals("delete_item")){
            //delete items
            rd = request.getRequestDispatcher(item_delete_admin_page);
            rd.forward(request, response);
        }
        else if(request.getParameter("action").equals("remove")){
            //remove corresponding items
            if(request.getParameter("itemid")!=null){
                int itemId = Integer.parseInt(request.getParameter("itemid"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                if(dbu.removeItem(itemId, quantity)){
                    //delete succeeds
                    sess.setAttribute("itemDeleteResult", "Succeed deleting the item");
                    itemList.refresh();
                    
                    response.sendRedirect(redirect_admin_page);
                    /*rd = request.getRequestDispatcher(item_delete_admin_page);
                    rd.forward(request, response);*/
                }else{
                    //delete fails
                    sess.setAttribute("itemDeleteResult", "Fail to delete the item");
                    itemList.refresh();
                    response.sendRedirect(redirect_admin_page);
                    /*rd = request.getRequestDispatcher(item_delete_admin_page);
                    rd.forward(request, response);*/
                }
                 
            }
        }
        else if(request.getParameter("action").equals("add_item_quantity")){
            if(request.getParameter("itemid")!=null){
            int itemid = Integer.parseInt(request.getParameter("itemid"));
            int quantity = Integer.parseInt(request.getParameter("add_quantity"));
            
            dbu.addItemQuantity(itemid, quantity);
            
            itemList.refresh();
            rd = request.getRequestDispatcher(display_admin_page);
            rd.forward(request, response);
            }
        }
        else if(request.getParameter("action").equals("totally_delete_item")){
            //totally delete the item from database
            if(request.getParameter("itemid")!=null){
                int itemid = Integer.parseInt(request.getParameter("itemid"));
                
                dbu.totallyDeleteItem(itemid);
                
                itemList.refresh();
                rd = request.getRequestDispatcher(display_admin_page);
                rd.forward(request, response);
            }
        }
        else if(request.getParameter("action").equals("itemRemoveDone")){
            //item remove done
            itemList.refresh();
            rd = request.getRequestDispatcher(display_admin_page);
            rd.forward(request, response);
        }
        else if(request.getParameter("action").equals("processOrder")){
            if(request.getParameter("orderId")!=null){
                //process the order and delete the record from the database
                
                //you can do something else here
                int orderId = Integer.parseInt(request.getParameter("orderId"));
                if(dbu.processOrder(orderId))
                    dbu.removeOrder(orderId);
                orderList.refresh();
                itemList.refresh();
                rd = request.getRequestDispatcher(display_admin_page);
                rd.forward(request, response);
            }
            else
                throw new ServletException("The order id can not be empty!");
        }
        else if(request.getParameter("action").equals("view_orders")){
            //
            orderList.refresh();
            rd = request.getRequestDispatcher("/orders_admin.jsp");
            rd.forward(request, response);
        }
        else if(request.getParameter("action").equals("client_message")){
            if(request.getParameter("message")!=null){
                //to send a message to the client
                
                rd = request.getRequestDispatcher(display_admin_page);
                rd.forward(request, response);
            }
            else
                throw new ServletException("the message can not be empty");
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            processRequest(request, response);
        }catch (ServletException se){
            throw se;
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            processRequest(request, response);
        }catch (ServletException se){
            throw se;
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "The administrator for the servlet";
    }// </editor-fold>
}
