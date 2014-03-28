package org.cidarlab.minieugene.xmlrpc.test;

import java.net.URL;
import java.util.Arrays;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;



public class Tester02 {

	private XmlRpcClientConfigImpl config;
	
	public Tester02() 
		throws Exception {

        // send the string to the EugeneWS
        this.config = new XmlRpcClientConfigImpl();
        this.config.setServerURL(new URL("http://localhost:8080/miniEugene-XmlRpc/xmlrpc"));
        //this.config.setServerURL(new URL("http://cidar1.bu.edu:9080/EugeneXmlRpc/xmlrpc"));
	}
	
	public void test() 
			throws Exception {
		
		String[] rules = {
				"CONTAINS a", 
				"NOTCONTAINS a"};

		XmlRpcClient client = new XmlRpcClient(); 
        client.setConfig(config);

        try {
			Object result = client.execute("EugeneXmlRpc.solve", new Object[]{rules, 12, 1});
	
	        if(null != result) {
	        	//String[][] solutions = (String[][])result;
	        	
	        	Object[] solutions = (Object[])result;
	        	System.out.println(solutions.length);
	        	for(int i=0; i<solutions.length; i++) {
	        		Object[] solution = (Object[])solutions[i];
	        		
	        		System.out.println(Arrays.toString(solution));
	        	}
	        	System.out.println("The result is: "+ result.getClass());
	        }
        } catch(Exception e) {
        	e.printStackTrace();
        }
	}
		
	public static void main(String[] args) {
		try {
			new Tester02().test();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
