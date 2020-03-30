/**
 *
 */
package com.demo.web.jsp.taglib;

import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Price formating tag (JSP custom tag - format).
 * Brings price into format : $###,###.00
 *
 * @author A.Serbin
 *
 */
public class PriceFormatter extends SimpleTagSupport {

	private static final String FORMAT = "$###,###.00";
	private String price;

	public PriceFormatter() {
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public void doTag() throws JspException, IOException {

		try {
			Long amount = Long.parseLong(price);
			DecimalFormat formatter = new DecimalFormat(FORMAT);
			String formattedPrice = formatter.format(amount);
			getJspContext().getOut().write(formattedPrice);
		} catch (Exception e) {
			e.printStackTrace();
				throw new SkipPageException("Exception in formatting " + price
					+ " with format " + FORMAT);
		}
	}

}
