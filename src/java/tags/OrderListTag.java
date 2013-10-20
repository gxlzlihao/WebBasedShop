/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tags;

import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.InputSource;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author gxlzlihao
 */
public class OrderListTag extends BodyTagSupport {

    private String xmlSource;

    /**
     * Creates new instance of tag handler
     */
    public OrderListTag() {
        super();
    }

    ////////////////////////////////////////////////////////////////
    ///                                                          ///
    ///   User methods.                                          ///
    ///                                                          ///
    ///   Modify these methods to customize your tag handler.    ///
    ///                                                          ///
    ////////////////////////////////////////////////////////////////
    /**
     * Method called from doStartTag(). Fill in this method to perform other
     * operations from doStartTag().
     */
    private void otherDoStartTagOperations() {
        // TODO: code that performs other operations in doStartTag
        //       should be placed here.
        //       It will be called after initializing variables, 
        //       finding the parent, setting IDREFs, etc, and 
        //       before calling theBodyShouldBeEvaluated(). 
        //
        //       For example, to print something out to the JSP, use the following:
        //
        //   try {
        //       JspWriter out = pageContext.getOut();
        //       out.println("something");
        //   } catch (IOException ex) {
        //       // do something
        //   }
    }

    /**
     * Method called from doEndTag() Fill in this method to perform other
     * operations from doEndTag().
     */
    private void otherDoEndTagOperations() {
        // TODO: code that performs other operations in doEndTag
        //       should be placed here.
        //       It will be called after initializing variables,
        //       finding the parent, setting IDREFs, etc, and
        //       before calling shouldEvaluateRestOfPageAfterEndTag().
    }

    /**
     * Fill in this method to process the body content of the tag. You only need
     * to do this if the tag's BodyContent property is set to "JSP" or
     * "tagdependent." If the tag's bodyContent is set to "empty," then this
     * method will not be called.
     */
    private void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException {
        // TODO: insert code to write html before writing the body content.
        // e.g.:
        //
        // out.println("<strong>" + attribute_1 + "</strong>");
        // out.println("   <blockquote>");

        //transfer the xml string to html string output
        StringReader read = new StringReader(xmlSource);
        InputSource is = new InputSource(read);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);
            NodeList list = doc.getElementsByTagName("OrderInfo");

            for (int i = 0; i < list.getLength(); i++) {
                Node currentNodeInfo = list.item(i);
                out.println("<h3>Order</h3>");
                out.println("<form action=\"admin?action=processOrder\" method=\"post\">");
                out.println("<table border=\"0\">\n"
                        + "                <tr bgcolor=\"#FFDC75\">\n"
                        + "                    <td>OrderId</td>\n"
                        + "                    <td>UserId</td>\n"
                        + "                    <td>UserName</td>\n"
                        + "                    <td>OrderDate</td>\n"
                        + "                    <td>Shipping Address</td>\n"
                        + "                    <td>Shipping Zipcode</td>\n"
                        + "                    <td>Shipping City</td>\n"
                        + "                </tr>");
                out.println("<tr bgcolor=\"silver\">");
                //output the order information
                Node order = currentNodeInfo.getFirstChild();
                Node originalOrderInfo = order.getFirstChild();
                Node orderInfo = originalOrderInfo;
                String orderId = null;
                String[] standards = {"order_id", "user_id", "user_name", "order_date", "shipping_address", "shipping_zipcode", "shipping_city"};
                for (int n = 0; n < standards.length; n++) {
                    while (orderInfo != null) {
                        //
                        if (orderInfo.getNodeName().equals(standards[n])) {
                            out.println("<td>");
                            out.println(orderInfo.getTextContent());
                            if(standards[n].equals("order_id"))
                                orderId=orderInfo.getTextContent();
                            out.println("</td>");
                            break;
                        }
                        orderInfo = orderInfo.getNextSibling();
                    }
                    orderInfo = originalOrderInfo;
                }
                out.println("</tr>");
                out.println("</table>");
                out.println("<input type=\"hidden\" value="+orderId+" name=\"orderId\">");
                out.println("<input type=\"submit\" value=\"process\"/>");
                out.println("</form>");
                //output the product list of the current order
                out.println("<h4>Product List</h4>");
                out.println("<table border=\"0\">\n"
                        + "            <tr bgcolor=\"#FFDC75\">\n"
                        + "                <td>Product Id</td>\n"
                        + "                <td>Product Name</td>\n"
                        + "                <td>Product Price</td>\n"
                        + "                <td>Quantity</td>\n"
                        + "            </tr>");
                Node productRecord = order.getNextSibling().getFirstChild();
                while (productRecord != null) {
                    Node product = productRecord.getFirstChild();
                    if (product.getNodeName().equals("product")) {
                        //output this product`s information
                        Node ori = product.getFirstChild();
                        Node tt = ori;
                        out.println("<tr bgcolor=\"silver\" cellspacing=\"0\">");
                        String[] standards2 = {"id", "name", "price"};
                        for (int m = 0; m < standards2.length; m++) {
                            while (tt != null) {
                                if (tt.getNodeName().equals(standards2[m])) {
                                    out.println("<td>");
                                    out.println(tt.getTextContent());
                                    out.println("</td>");
                                    break;
                                }
                                tt = tt.getNextSibling();
                            }
                            tt = ori;
                        }
                        Node quantity = product.getNextSibling();
                        if (quantity.getNodeName().equals("quantity")) {
                            out.println("<td>");
                            out.println(quantity.getTextContent());
                            out.println("</td>");
                        }
                        out.println("</tr>");
                    }
                    productRecord = productRecord.getNextSibling();
                }
                out.println("</table><br>");
            }


        } catch (ParserConfigurationException ex) {
            Logger.getLogger(OrderListTag.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (SAXException ex) {
            Logger.getLogger(OrderListTag.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        // write the body content (after processing by the JSP engine) on the output Writer
        bodyContent.writeOut(out);

        // Or else get the body content as a string and process it, e.g.:
        //     String bodyStr = bodyContent.getString();
        //     String result = yourProcessingMethod(bodyStr);
        //     out.println(result);

        // TODO: insert code to write html after writing the body content.
        // e.g.:
        //
        // out.println("   </blockquote>");


        // clear the body content for the next time through.
        bodyContent.clearBody();
    }

    ////////////////////////////////////////////////////////////////
    ///                                                          ///
    ///   Tag Handler interface methods.                         ///
    ///                                                          ///
    ///   Do not modify these methods; instead, modify the       ///
    ///   methods that they call.                                ///
    ///                                                          ///
    ////////////////////////////////////////////////////////////////
    /**
     * This method is called when the JSP engine encounters the start tag, after
     * the attributes are processed. Scripting variables (if any) have their
     * values set here.
     *
     * @return EVAL_BODY_BUFFERED if the JSP engine should evaluate the tag
     * body, otherwise return SKIP_BODY. This method is automatically generated.
     * Do not modify this method. Instead, modify the methods that this method
     * calls.
     */
    @Override
    public int doStartTag() throws JspException {
        otherDoStartTagOperations();

        if (theBodyShouldBeEvaluated()) {
            return EVAL_BODY_BUFFERED;
        } else {
            return SKIP_BODY;
        }
    }

    /**
     * This method is called after the JSP engine finished processing the tag.
     *
     * @return EVAL_PAGE if the JSP engine should continue evaluating the JSP
     * page, otherwise return SKIP_PAGE. This method is automatically generated.
     * Do not modify this method. Instead, modify the methods that this method
     * calls.
     */
    @Override
    public int doEndTag() throws JspException {
        otherDoEndTagOperations();

        if (shouldEvaluateRestOfPageAfterEndTag()) {
            return EVAL_PAGE;
        } else {
            return SKIP_PAGE;
        }
    }

    /**
     * This method is called after the JSP engine processes the body content of
     * the tag.
     *
     * @return EVAL_BODY_AGAIN if the JSP engine should evaluate the tag body
     * again, otherwise return SKIP_BODY. This method is automatically
     * generated. Do not modify this method. Instead, modify the methods that
     * this method calls.
     */
    @Override
    public int doAfterBody() throws JspException {
        try {
            // This code is generated for tags whose bodyContent is "JSP"
            BodyContent bodyCont = getBodyContent();
            JspWriter out = bodyCont.getEnclosingWriter();

            writeTagBodyContent(out, bodyCont);
        } catch (Exception ex) {
            handleBodyContentException(ex);
        }

        if (theBodyShouldBeEvaluatedAgain()) {
            return EVAL_BODY_AGAIN;
        } else {
            return SKIP_BODY;
        }
    }

    /**
     * Handles exception from processing the body content.
     */
    private void handleBodyContentException(Exception ex) throws JspException {
        // Since the doAfterBody method is guarded, place exception handing code here.
        throw new JspException("Error in OrderListTag tag", ex);
    }

    /**
     * Fill in this method to determine if the rest of the JSP page should be
     * generated after this tag is finished. Called from doEndTag().
     */
    private boolean shouldEvaluateRestOfPageAfterEndTag() {
        // TODO: code that determines whether the rest of the page
        //       should be evaluated after the tag is processed
        //       should be placed here.
        //       Called from the doEndTag() method.
        //
        return true;
    }

    /**
     * Fill in this method to determine if the tag body should be evaluated
     * again after evaluating the body. Use this method to create an iterating
     * tag. Called from doAfterBody().
     */
    private boolean theBodyShouldBeEvaluatedAgain() {
        // TODO: code that determines whether the tag body should be
        //       evaluated again after processing the tag
        //       should be placed here.
        //       You can use this method to create iterating tags.
        //       Called from the doAfterBody() method.
        //
        return false;
    }

    private boolean theBodyShouldBeEvaluated() {
        // TODO: code that determines whether the body should be
        //       evaluated should be placed here.
        //       Called from the doStartTag() method.
        return true;
    }

    public void setXmlSource(String xmlSource) {
        this.xmlSource = xmlSource;
    }
}
