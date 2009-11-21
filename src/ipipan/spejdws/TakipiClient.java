package ipipan.spejdws;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class TakipiClient {
	private static final String DEFAULT_URL = "http://takipi-xmlrpc.appspot.com/xmlrpc/";
	private static final int TIMEOUT_SECS = 15;
	
	public static final int STATUS_PROCESSED = 1;
	public static final int STATUS_QUEUED = 2;
	public static final int STATUS_PROCESSING = 3;
	public static final int STATUS_REJECTED = 4;
	public static final int STATUS_DUMPED = 5;

	private final XmlRpcClient client;
	
	public TakipiClient() throws MalformedURLException {
		this(DEFAULT_URL);
	}
	
	public TakipiClient(String url) throws MalformedURLException {
	    this(buildClient(url));
	}

	public TakipiClient(XmlRpcClient client) {
		this.client = client;
	}

	private static XmlRpcClient buildClient(String url) throws MalformedURLException {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL(url));
	    XmlRpcClient client = new XmlRpcClient();
	    client.setConfig(config);
		return client;
	}
	
	public String callTakipi(String string, Object[] params) {
	    try {
		    Map result = (Map) client.execute("takipi.Tag", params);
		    Integer status = (Integer) result.get("status");
		    if (status != STATUS_QUEUED) {
		    	throw new RuntimeException("Takipi did not accept the request.");
		    }
		    String token = (String) result.get("msg");
		    System.err.println("Takipi token: " + result.toString());
		    
		    int cycleNumber = 0;
		    
		    while (status == STATUS_QUEUED || status == STATUS_PROCESSING) {
		    	Thread.sleep(1000);
			    result = (Map) client.execute("takipi.GetResult", new Object[]{token});
			    status = (Integer) result.get("status");
			    cycleNumber++;
			    
			    if (cycleNumber >= TIMEOUT_SECS) {
			    	throw new RuntimeException("Timeout waiting for takipi.");
			    }
		    }
		    
		    if (status == STATUS_PROCESSED) {
		    	return (String) result.get("msg");
		    } else {
		    	throw new RuntimeException("Takipi returned error (status=" + status + ").");
		    }
		} catch (Exception e) {
			throw new RuntimeException("Tagging failed", e);
		}
	}

}
