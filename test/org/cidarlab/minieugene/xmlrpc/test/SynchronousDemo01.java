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
        this.config.setServerURL(new URL("http://cidar.bu.edu/miniEugene-XmlRpc/xmlrpc"));
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
