/*
 * Copyright (c) 2014, Boston University
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or 
 * without modification, are permitted provided that the following 
 * conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright 
 *    notice, this list of conditions and the following disclaimer.
 *    
 * 2. Redistributions in binary form must reproduce the above copyright 
 *    notice, this list of conditions and the following disclaimer in 
 *    the documentation and/or other materials provided with the distribution.
 *    
 * 3. Neither the name of the copyright holder nor the names of its 
 *    contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission.
 *    
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS 
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT 
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR 
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT 
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED 
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.cidarlab.minieugene.xmlrpc.test;

import java.net.URL;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

/**
 * Here we demonstrate how to invoke the miniEugene XML-RPC Web service 
 * using the Apache XML-RPC library. 
 * 
 * We invoke the miniEugene in a synchronous way. Hence, this process 
 * is blocked until the XML-RPC web service responds.
 * 
 * @author Ernst Oberortner
 */
public class SynchronousDemo01 {

    // The XML-RPC client
	private XmlRpcClient client;

	// The configuration of the XML-RPC client
	private XmlRpcClientConfigImpl config;
	
	
	public SynchronousDemo01() 
		throws Exception {

		/*
		 * we instantiate a configuration for the XML-RPC client
		 */
        this.config = new XmlRpcClientConfigImpl();

        /*
         * we configure the URL of the miniEugene XML-RPC Web service
         */
        this.config.setServerURL(new URL("http://cidar.bu.edu/miniEugeneXmlRpc/xmlrpc"));
//        this.config.setServerURL(new URL("http://localhost:8080/xmlrpc"));
        
		/*
		 * then we instantiate the XML-RPC Client
		 * and configure it 
		 */
		this.client = new XmlRpcClient(); 
        this.client.setConfig(config);
	}
	
	public void invokeMiniEugeneXMLRPC() 
			throws Exception {
		
		/*
		 * String[] array of design-specific rules
		 */
		String[] constraints = {
				"CONTAINS a", "CONTAINS b", "CONTAINS c", 
				"CONTAINS d", "CONTAINS e", "CONTAINS f", 
				"CONTAINS g", "CONTAINS h", "CONTAINS i",
				"CONTAINS j", "CONTAINS k", "CONTAINS l", 
				"a BEFORE b", "b BEFORE c", "c BEFORE d",
				"d BEFORE e", "e BEFORE f", "f BEFORE g",
				"g BEFORE h", "h BEFORE i", "i BEFORE j",
				"j BEFORE k", "k BEFORE l"};

        /*
         * SYNCHRONOUS Web Service Invocation
         */
        this.synchronousCall(constraints);
	}
	
	/**
	 * The synchronousCall method invokes the miniEugene XML-RPC 
	 * web service in a synchronous way. That is, we the client 
	 * is blocked until the web service response. 
	 * 
	 * @param rules  ... a set of miniEugene constraints
	 */
	private void synchronousCall(String[] constraints) 
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
        		new Object[]{constraints, 12, 100});
        
        
        if(null != solutions) {
        	/*
        	 * if there are solutions,
        	 * then we process them
        	 */
	        this.processSolutions(solutions);
        }
	}
	
	/**
	 * The processSolutions/1 method takes as input a 
	 * set of miniEugene solutions (represented as Java object) and 
	 * processes them by printing them to the console (using System.out).
	 * 
	 * @param solutions   ... a set of solutions
	 */
	private void processSolutions(Object solutions) {
    	
		Object[] sols = (Object[])solutions;
    	
		System.out.println("# of solutions: "+sols.length);
    	
    	// we iterate over the solutions 
    	for(int i=0; i<sols.length; i++) {
    		Object[] solution = (Object[])sols[i];

    		// and print every solution
    		for(int j=0; j<solution.length; j++) {
    			System.out.print(solution[j]);
    			if(j < solution.length - 1)
    				System.out.print(", ");
    		}
    		
    		System.out.println();
    	}
	}

	/**
	 * The main() method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new SynchronousDemo01().invokeMiniEugeneXMLRPC();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
