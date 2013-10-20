/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import beans.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author gxlzlihao
 */
@WebServlet(name = "Shop", urlPatterns = {"/shop"})
public class ShopServlet extends HttpServlet {
    
    private static String welcome_page = null;
    private static String display_page = null;
    private static String login_page = null;
    private static String profile_page = null;
    private static String jdbc_url = null;
    private static String detail_page = null;
    private static String newuser_page = null;
    private static String checkout_page = null;
    private static String bye_page = null;
    private static String thankyou_page = null;
    private static String redirect_page = null;
    private ProductListBean productList = null;
    
    /*
     * to initialize the servlet
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        welcome_page = config.getInitParameter("WELCOME_PAGE");
        display_page = config.getInitParameter("DISPLAY_PAGE");
        login_page = config.getInitParameter("LOGIN_PAGE");
        profile_page = config.getInitParameter("PROFILE_PAGE");
        detail_page = config.getInitParameter("DETAIL_PAGE");
        newuser_page = config.getInitParameter("NEWUSER_PAGE");
        checkout_page = config.getInitParameter("CHECKOUT_PAGE");
        bye_page = config.getInitParameter("BYE_PAGE");
        thankyou_page = config.getInitParameter("THANKYOU_PAGE");
        redirect_page = config.getInitParameter("REDIRECT_CHECKOUT_PAGE");
        jdbc_url = config.getInitParameter("JDBC_URL");
        
        //to get a product list from the database using a bean
        try{
            productList = new ProductListBean(jdbc_url);
        }
        catch(Exception e){
            throw new ServletException(e);
        }
        
        ServletContext sc = getServletContext();
        sc.setAttribute("productList",productList);
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
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        ShoppingBean shoppingCart = getCart(request);
        messageBean mb = new messageBean(jdbc_url);
        HttpSession sess = request.getSession();
        RequestDispatcher rd = null;
        sess.setAttribute("currentUser", request.getRemoteUser());
        sess.setAttribute("jdbc_url", jdbc_url);
        sess.setAttribute("message_bean",mb);
        
        //to check if we need to open the debug mode
        String debug = request.getParameter("debug");
	if(debug != null && debug.equals("on"))
	    sess.setAttribute("debug","on");
	else if (debug != null && debug.equals("off"))
	    sess.removeAttribute("debug");
        
        //to dispatch the aciton command
        
        if(request.getParameter("action") == null ||
                request.getParameter("action").equals("display")){
            //enter the products display page
            rd = request.getRequestDispatcher(display_page);
            rd.forward(request, response);
        }
        else if(request.getParameter("action").equals("add")){
            //add a new product into the user`s cart
            if(request.getParameter("quantity") != null && request.getParameter("productid") != null){
              
                //
                ProductBean pb = null;
                
                pb = productList.getById(Integer.parseInt(request.getParameter("productid")));
                
                if(pb == null)
                    throw new ServletException("the product you want to buy is not in the stock!");
                else{
                    try{
                        int num = Integer.parseInt(request.getParameter("quantity"));
                        if(num < 0)
                            throw new NumberFormatException("Invalid Quantity Specified");
                        else{
                            
                            shoppingCart.addProduct(pb, num);
                        }
                    }
                    catch (NumberFormatException e) {
                        throw new ServletException("Invalid quantity specified!");
                    }
                }
                
                rd = request.getRequestDispatcher(display_page);
                rd.forward(request, response);
            }
        }
        else if(request.getParameter("action").equals("remove")){
            //to remove the item from the shopping cart
            if(request.getParameter("quantity") != null && request.getParameter("productid") != null){
                 //
                 ProductBean pb = null;
                 int product_id = Integer.parseInt(request.getParameter("productid"));
                 pb = productList.getById(product_id);
                 
                 if(pb == null)
                     throw new ServletException("the product you want to remove is not in the stock!");
                 else{
                     int num = Integer.parseInt(request.getParameter("quantity"));
                     if(num < 0)
                         throw new NumberFormatException("Invalid Quantity Specified");
                     else{
                     
                         shoppingCart.removeProduct(product_id, num);
                         
                     }
                 }
            }
            
            rd = request.getRequestDispatcher(display_page);
            rd.forward(request, response);
        }
        else if(request.getParameter("action").equals("newuser")){
            //to create a new user`s profile
            ProfileBean pb = new ProfileBean(jdbc_url);
            
            try{
                //get the roles from the profile bean
                HashMap<String,Boolean> roles = pb.getRoles();
                sess.setAttribute("roles", roles);
            }
            catch(Exception e) {
                throw new ServletException("Error loading profile bean!", e);
            }
            
            //redirect the request to the creating new user page
            sess.setAttribute("profile", pb);
            
            rd = request.getRequestDispatcher(newuser_page);
            rd.forward(request,response);
        }
        else if(request.getParameter("action").equals("checkout")){
            if(sess.getAttribute("currentUser") != null){
            //enter the log in page to verify the user information
            
            ProfileBean p = new ProfileBean(jdbc_url);
            //sess.setAttribute("JDBC_URL", jdbc_url);
	    try {
               p.populate((String)sess.getAttribute("currentUser"));
            }
            catch(Exception e) {
               throw new ServletException("Error loading profile", e);
            }
               sess.setAttribute("profile", p);
            }
            
            response.sendRedirect(redirect_page);
            //rd = request.getRequestDispatcher(checkout_page);
            //rd.forward(request, response);
        }
        else if(request.getParameter("action").equals("logout")){
            //logout the current user and forward to the bye page
            sess.invalidate();
            rd = request.getRequestDispatcher(bye_page);
            rd.forward(request, response);
        }
        else if(request.getParameter("action").equals("profile")) {
	    HashMap<String,Boolean> role = null;

	    // create a profile object, fill it in from the database
	    // also store all user roles in the map "role"

	    ProfileBean p = new ProfileBean(jdbc_url);
	    try {
		p.populate((String)sess.getAttribute("currentUser"));
		role = p.getRoles();
            }
            catch(Exception e) {
                throw new ServletException("Error loading profile", e);
            }
	    sess.setAttribute("profile", p);

	    // flag all roles that the user is associated with

	    Set<String> k = role.keySet();
	    Iterator<String> i = k.iterator();
	    while (i.hasNext()) {
		String st = i.next();
		if(request.isUserInRole(st)) role.put(st,true);
	    }
	    p.setRole(role);
	    sess.setAttribute("roles",role);
	    rd = request.getRequestDispatcher(profile_page);
	    rd.forward(request, response);
            
        } 
        else if(request.getParameter("action").equals("profilechange") ||
                request.getParameter("action").equals("usercreate")){
            
            ProfileBean pb = (ProfileBean)sess.getAttribute("profile");
	    String u;
	    if (request.getParameter("action").equals("profilechange")) 
		u = (String)sess.getAttribute("currentUser");
	    else 
		u = request.getParameter("user");

	    // get all data needed

	    String p1 = request.getParameter("password");
	    String p2 = request.getParameter("password2");
	    String name = request.getParameter("name");
	    String street = request.getParameter("street");
	    String zip = request.getParameter("zip");
	    String city = request.getParameter("city");
	    String country = request.getParameter("country");
	    
	    pb.setUser_name(u);
	    pb.setUser_password(p1);
	    pb.setName(name);
	    pb.setAddress(street);
	    pb.setZip(zip);
	    pb.setCity(city);
	    pb.setCountry(country);   
	    HashMap<String, Boolean> r =
                (HashMap<String,Boolean>) sess.getAttribute("roles");
	    Set<String> k = r.keySet();
	    Iterator<String> i = k.iterator();
	    while (i.hasNext()) {
                String st = i.next();
                String res = request.getParameter(st); 
                if (res != null) r.put(st, true);
                else r.put(st,false);
	    }           
	    pb.setRole(r);

	    // if this a new user, try to add him to the database

	    if (request.getParameter("action").equals("usercreate")) {
		boolean b;

		// make sure the the username is not used already

		try {
                    b = pb.testUser(u);
		}
		catch(Exception e) {
                    throw new ServletException("Error loading user table", e);
		}
		if(b) { 
                    sess.setAttribute("passwordInvalid",
				      "User name already in use");
   		    rd = request.getRequestDispatcher(newuser_page);
		    rd.forward(request, response);

		    // note that a return is needed here because forward
		    // will not cause our servlet to stop execution, just
		    // forward the request processing

		    return; 
		}
	    }
	    
	    // now we know that we have a valid user name
	    // validate all data, 

	    boolean b = profileValidate(request,sess);
	    if (!b && request.getParameter("action").equals("profilechange")) {
		rd = request.getRequestDispatcher(profile_page);
		rd.forward(request, response);
	    }
	    else if (!b) {
		rd = request.getRequestDispatcher(newuser_page);
		rd.forward(request, response);
	    }

	    // validated OK,  update the database

	    else {
		
    	        ProfileUpdateBean pu = new ProfileUpdateBean(jdbc_url);
                if (request.getParameter("action").equals("profilechange")) {
		    try {
			pu.setProfile(pb);
		    }
		    catch(Exception e){
			throw new ServletException("Error saving profile", e);
		    }
		    rd = request.getRequestDispatcher(display_page);
		    rd.forward(request, response);
		}
		else {
		    try {
			pu.setUser(pb);
		    }
		    catch(Exception e){
			throw new ServletException("Error saving profile", e);
		    }
		    response.sendRedirect(checkout_page);
		} 
	    }
            
        }
        else if(request.getParameter("action").equals("detail")){
            //enter the detail page to see the detail information of each book.
            if(request.getParameter("productid") != null){
                //
                ProductBean pp = productList.getById(
			   Integer.parseInt(request.getParameter("productid")));
		request.setAttribute("product", pp);
            }
            else{
                throw new ServletException("no bookid when viewing detail page");
            }
            
            //enter the detail page
            rd = request.getRequestDispatcher(detail_page);
            rd.forward(request, response);
        }
        else if(request.getParameter("action").equals("save")){
            //this action means that a new order has been produced 
            
            // if we have a shoppingcart, verify that we have
	    // valid userdata, then create an orderbean and
	    // save the order in the database

	    if (shoppingCart != null &&
		request.getParameter("shipping_name") != null && 
                request.getParameter("paid") != null &&
		request.getParameter("shipping_city") != null && 
		request.getParameter("shipping_zipcode") != null && 
		request.getParameter("shipping_address") != null){

                OrderBean ob = new OrderBean(
                        jdbc_url,
                        shoppingCart,
                        request.getParameter("shipping_name").trim(),
                        (String)sess.getAttribute("currentUser"),
                        Integer.parseInt(request.getParameter("paid")),
                        request.getParameter("shipping_address").trim(),
                        request.getParameter("shipping_zipcode").trim(),
                        request.getParameter("shipping_city").trim()
                        );
		try{
		    ob.saveOrder();
		}
		catch(Exception e){
		    throw new ServletException("Error saving order", e);
		}
	    }
	    else{
		throw new ServletException(
		       "Not all parameters are present or no " + 
		       " shopping cart when saving book");
	    }
            rd = request.getRequestDispatcher(thankyou_page);
            rd.forward(request,response);
        }
        else if(request.getParameter("action").equals("clear_messages")){
            //clear all the messages for the current user
            if(request.getParameter("user_id")!=null){
                String user_id = request.getParameter("user_id");
                boolean clear_res = false;
                try{
                    clear_res = mb.clearMessages(user_id);
                }catch(Exception e){
                    throw new ServletException(e);
                }
                if(clear_res){
                    //succeed clearing the messages
                    rd = request.getRequestDispatcher(display_page);
                    rd.forward(request, response);
                }
                
            }
        }
        
        try {
            /* TODO output your page here. You may use following sample code. */
            /*out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ShopServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ShopServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");*/
        } finally {            
            out.close();
        }
    }
    
    //validate user`s profile
    
    private boolean profileValidate(HttpServletRequest request, 
				    HttpSession sess) {

	// use the attribute "passwordInvalid" as error messages

	sess.setAttribute("passwordInvalid", null);
	String u;

	// get all data

	if (request.getParameter("action").equals("profilechange")) 
	    u = (String)sess.getAttribute("currentUser");
	else 
	    u = request.getParameter("user");
	String p1 = request.getParameter("password");
	String p2 = request.getParameter("password2");
	String name = request.getParameter("name");
	String street = request.getParameter("street");
	String zip = request.getParameter("zip");
	String city = request.getParameter("city");
	String country = request.getParameter("country");
	HashMap<String,Boolean> r =
	    (HashMap<String,Boolean>) sess.getAttribute("roles");
	Set<String> k = r.keySet();
	int count=0;
	Iterator<String> i = k.iterator();
	while (i.hasNext()) {
	    String st = request.getParameter(i.next());
	    if(st != null) count++;
	}           
	
	// validate

	if(count == 0) {
	    sess.setAttribute("passwordInvalid",
			      "You must select at least one role");
	    return false;
	}
	else if( u == null || u.length() < 1) {
	    sess.setAttribute("passwordInvalid",
			      "User name must not be empty, retry!");
	    return false;
	    
	}
	if(!request.isUserInRole("admin") && 
	   request.getParameter("admin") != null) {
	    sess.setAttribute("passwordInvalid",
			      "You must be in role admin to set role admin");
	    return false;
	}
	if(p1 == null || p2 == null || p1.length() < 1) {
	    sess.setAttribute("passwordInvalid",
			      "Password must not be empty, retry!");
	    return false;
	    
	}
	else if (!(p1.equals(p2))){
	    sess.setAttribute("passwordInvalid",
			      "Passwords do not match, retry!");
	    return false;
	}
	else if (name == null || name.length() < 1){
	    sess.setAttribute("passwordInvalid",
			      "Name must not be empty, retry!");
	    return false;
	}
	else if (street == null || street.length() < 1){
	    sess.setAttribute("passwordInvalid",
			      "Street must no be empty, retry!");
	    return false;
	}
	else if (zip == null || zip.length() < 1){
	    sess.setAttribute("passwordInvalid",
			      "Zip code must not be empty, retry!");
	    return false;
	}
	else if (city == null || city.length() < 1){
	    sess.setAttribute("passwordInvalid",
			      "City must not be empty, retry!");
	    return false;
	}
	else if (country == null || country.length() < 1){
	    sess.setAttribute("passwordInvalid",
			      "County must not be empty, retry!");
	    return false;
	}
        
	// validation OK

	return true;
    }
    
    private ShoppingBean getCart(HttpServletRequest request){
        HttpSession se = null;
        se=request.getSession();
        ShoppingBean sb =null;
        sb = (ShoppingBean)se.getAttribute("shoppingCart");
        if(sb==null){
            sb = new ShoppingBean();
            se.setAttribute("shoppingCart",sb);
        }

        return sb;
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "The servlet for the web based shop!";
    }// </editor-fold>
}
