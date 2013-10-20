/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tags;

import beans.ProfileBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import static javax.servlet.jsp.tagext.Tag.EVAL_PAGE;
import static javax.servlet.jsp.tagext.Tag.SKIP_BODY;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author gxlzlihao
 */
public class ProfileTag extends TagSupport {

	private String url = null;

	public void setUrl(String _url) {
        url = _url;
      }

	public int doStartTag() throws JspException {
	    try {

		// get access to the session and to the request
 
		JspWriter out = pageContext.getOut();
		System.out.println("URL = " + url);
		HttpSession sess = pageContext.getSession();
		HttpServletRequest request = 
		    (HttpServletRequest) pageContext.getRequest();

		// get the username and store it in the session

		String user = request.getRemoteUser();
		sess.setAttribute("currentUser",user);

		// create a profile bean and start populate it
		// store it in the request

		ProfileBean pb = new ProfileBean(url);
		pb.populate(user);
		request.setAttribute("profile", pb);
	    } catch (Exception e) {
		throw new JspException(
				       e.getMessage());
	    }
	    return SKIP_BODY;
	}
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
}
