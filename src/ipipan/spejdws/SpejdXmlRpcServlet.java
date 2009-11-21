package ipipan.spejdws;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.xmlrpc.webserver.XmlRpcServlet;

public class SpejdXmlRpcServlet extends XmlRpcServlet {
	private static final long serialVersionUID = -6112262904760417202L;

	private static ServletConfig config = null;

	private static void setConfig(ServletConfig config) {
		assert SpejdXmlRpcServlet.config == null : "Servlet initialized twice?";
		SpejdXmlRpcServlet.config = config;
	}

	public static ServletConfig getConfig() {
		return config;
	}

	@Override
	public void init(ServletConfig pConfig) throws ServletException {
		setConfig(pConfig);
		super.init(pConfig);
	}
}
