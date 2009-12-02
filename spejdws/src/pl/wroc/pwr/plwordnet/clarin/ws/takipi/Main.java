package pl.wroc.pwr.plwordnet.clarin.ws.takipi;

import java.rmi.RemoteException;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TakipiProxy p = new TakipiProxy();
		try {
			TaggerResponse r =	p.tag("Tekst do przetestowania.", DocumentFormat.TXT, true);
			String token = r.getMsg();
			System.out.println("Got token: " + token);
			
			Thread.sleep(5000);
			
			r = p.getResult(token);
			
			System.out.println("Results: " + r.getMsg());
			
		} catch (Operation_faultMsg e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
