package ipipan.spejdws;

import pl.wroc.pwr.plwordnet.clarin.ws.takipi.DocumentFormat;
import pl.wroc.pwr.plwordnet.clarin.ws.takipi.TaggerResponse;
import pl.wroc.pwr.plwordnet.clarin.ws.takipi.TakipiProxy;

public class TakipiClient {
	private static final int TIMEOUT_SECS = 15;
	private static final int SLEEP_MSECS = 300;
	
	public static final int STATUS_PROCESSED = 1;
	public static final int STATUS_QUEUED = 2;
	public static final int STATUS_PROCESSING = 3;
	public static final int STATUS_REJECTED = 4;
	public static final int STATUS_DUMPED = 5;

	public String callTakipi(String text, DocumentFormat format, boolean useGuesser) {
	    try {
	    	TakipiProxy proxy = new TakipiProxy();
	    	TaggerResponse resp = proxy.tag(text, format, useGuesser);
	    	
		    int status = resp.getStatus();
		    if (status != STATUS_QUEUED) {
		    	throw new RuntimeException("Takipi did not accept the request.");
		    }
		    String token = resp.getMsg();
		    System.err.println("Takipi token: " + token);
		    
		    int cycleNumber = 0;
		    
		    while (status == STATUS_QUEUED || status == STATUS_PROCESSING) {
		    	Thread.sleep(SLEEP_MSECS);
			    resp = proxy.getResult(token);
			    status = resp.getStatus();
			    cycleNumber++;
			    
			    if (cycleNumber * SLEEP_MSECS >= TIMEOUT_SECS * 1000) {
			    	throw new RuntimeException("Timeout waiting for takipi.");
			    }
		    }
		    
		    if (status == STATUS_PROCESSED) {
		    	return resp.getMsg();
		    } else {
		    	throw new RuntimeException("Takipi returned error (status=" + status + ").");
		    }
		} catch (Exception e) {
			throw new RuntimeException("Tagging failed", e);
		}
	}
}
