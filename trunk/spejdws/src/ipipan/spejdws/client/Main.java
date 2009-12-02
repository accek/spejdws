package ipipan.spejdws.client;


public class Main {
	public static void main(String[] args) {
		try {
			SpejdServiceStub service = new SpejdServiceStub();
			System.out.println("Web Service Version: " + service.getVersion().get_return());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
