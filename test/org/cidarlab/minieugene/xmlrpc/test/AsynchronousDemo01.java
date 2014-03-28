package org.cidarlab.minieugene.xmlrpc.test;

import java.net.URL;

import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.AsyncCallback;

public class AsynchronousDemo01 {

	private XmlRpcClientConfigImpl config;
	
	public AsynchronousDemo01() 
		throws Exception {

        // send the string to the EugeneWS
        this.config = new XmlRpcClientConfigImpl();

        this.config.setServerURL(new URL("http://cidar.bu.edu:8080/miniEugene-XmlRpc/xmlrpc"));
	}
	
	public void test() 
			throws Exception {
		
		String[] rules = {
				"CONTAINS a", "CONTAINS b", "CONTAINS c", 
				"CONTAINS d", "CONTAINS e", "CONTAINS f", 
				"CONTAINS g", "CONTAINS h", "CONTAINS i",
				"CONTAINS j", "CONTAINS k", "CONTAINS l", 
				"a BEFORE b", "b BEFORE c", "c BEFORE d",
				"d BEFORE e", "e BEFORE f", "f BEFORE g",
				"g BEFORE h", "h BEFORE i", "i BEFORE j",
				"j BEFORE k", "k BEFORE l"};

		XmlRpcClient client = new XmlRpcClient(); 
        client.setConfig(config);

        
        /*
         * SYNCHRONOUS
         */
        this.synchronousCall(client, rules);


        
        /*
         * ASYNCHRONOUS
         */
        this.asynchronousCall(client, rules);
	}
	
	/**
	 * 
	 * @param client
	 * @param rules
	 */
	private void synchronousCall(XmlRpcClient client, String[] rules) {
		try {
	        Object result = client.execute(
	        		"MiniEugeneXmlRpc.solve", 
	        		new Object[]{rules, 6, -1});
	        
	        if(null != result) {
	        	Object[] solutions = (Object[])result;
	        	System.out.println("# of solutions: "+solutions.length);
	        	
	        	for(int i=0; i<solutions.length; i++) {
	        		Object[] solution = (Object[])solutions[i];
	        		
	        		for(int j=0; j<solution.length; j++) {
	        			System.out.print(solution[j]+", ");
	        		}
	        		System.out.println();
	        	}
	        }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * 
	 * @param client
	 * @param rules
	 */
	private void asynchronousCall(XmlRpcClient client, String[] rules) {
		try {
			System.out.println("calling...");
	        client.executeAsync(
	        		"MiniEugeneXmlRpc.solve", 
	        		new Object[]{rules, 6, -1}, 
	        		new AsyncCallback() {
	
						@Override
						public void handleError(XmlRpcRequest request, Throwable t) {
							// TODO Auto-generated method stub
							
						}
	
						@Override
						public void handleResult(XmlRpcRequest request, Object result) {
							System.out.println("[handleResult]");
							
					        if(null != result) {
					        	Object[] solutions = (Object[])result;
					        	System.out.println("# of solutions: "+solutions.length);
					        	
					        	for(int i=0; i<solutions.length; i++) {
					        		Object[] solution = (Object[])solutions[i];
					        		
					        		for(int j=0; j<solution.length; j++) {
					        			System.out.print(solution[j]+", ");
					        		}
					        		System.out.println();
					        	}
					        }
						}
       		});
	        System.out.println("do...");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			new AsynchronousDemo01().test();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
