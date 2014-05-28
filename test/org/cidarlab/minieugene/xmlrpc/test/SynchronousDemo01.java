package org.cidarlab.minieugene.xmlrpc.test;

import java.net.URL;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

/**
 * Here we demonstrate how to invoke the miniEugene XML-RPC Web service 
 * using the Apache XML-RPC library. 
 * 
 * We invoke the miniEugene in a synchronous way.
 * 
 * @author Ernst Oberortner
 */
public class SynchronousDemo01 {

	private XmlRpcClientConfigImpl config;
	
	public SynchronousDemo01() 
		throws Exception {

        this.config = new XmlRpcClientConfigImpl();

        /*
         * that's the URL of the miniEugene XML-RPC Web service
         */
        this.config.setServerURL(new URL("http://cidar.bu.edu:8080/miniEugene-XmlRpc/xmlrpc"));
//        this.config.setServerURL(new URL("http://localhost:8080/miniEugene-XmlRpc/xmlrpc"));
	}
	
	public void test() 
			throws Exception {
		
		/*
		 * String[] array of design-specific rules
		 */
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
         * SYNCHRONOUS Web Service Invocation
         */
        this.synchronousCall(client, rules);
	}
	
	/**
	 * 
	 * @param client
	 * @param rules
	 */
	private void synchronousCall(XmlRpcClient client, String[] rules) 
			throws Exception {
		
		/*
		 * here, we invoke the solve/3 method of 
		 * the miniEugene XML-RPC Web service
		 * 
		 * the length of the design should be 12 
		 * and we request 100 solutions.
		 */
        Object solutions = client.execute(
        		"MiniEugeneXmlRpc.solve", 
        		new Object[]{rules, 12, 100});
        
        
        if(null != solutions) {
        	/*
        	 * if there are solutions,
        	 * then we process them
        	 */
	        this.processSolutions(solutions);
        }
	}
	
	/**
	 * 
	 * @param solutions
	 */
	private void processSolutions(Object solutions) {
    	Object[] sols = (Object[])solutions;
    	System.out.println("# of solutions: "+sols.length);
    	
    	// we just print the solutions to the console
    	for(int i=0; i<sols.length; i++) {
    		Object[] solution = (Object[])sols[i];
    		
    		for(int j=0; j<solution.length; j++) {
    			System.out.print(solution[j]+", ");
    		}
    		
    		System.out.println();
    	}
	}

	/**
	 * MAIN
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new SynchronousDemo01().test();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
