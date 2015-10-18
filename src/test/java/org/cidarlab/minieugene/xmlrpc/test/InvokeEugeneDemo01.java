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

import java.io.File;
import java.net.URL;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.cidarlab.eugene.dom.imp.container.EugeneArray;
import org.cidarlab.eugene.dom.imp.container.EugeneCollection;
import org.cidarlab.eugene.util.FileUtils;

/**
 * Here we demonstrate how to invoke the miniEugene XML-RPC Web service 
 * using the Apache XML-RPC library. 
 * 
 * We invoke the miniEugene in a synchronous way. Hence, this process 
 * is blocked until the XML-RPC web service responds.
 * 
 * @author Ernst Oberortner
 */
public class InvokeEugeneDemo01 {

    // The XML-RPC client
	private XmlRpcClient client;

	// The configuration of the XML-RPC client
	private XmlRpcClientConfigImpl config;
	
	
	public InvokeEugeneDemo01() 
		throws Exception {

		/*
		 * we instantiate a configuration for the XML-RPC client
		 */
        this.config = new XmlRpcClientConfigImpl();

        /*
         * we configure the URL of the miniEugene XML-RPC Web service
         */
        this.config.setServerURL(new URL("http://localhost:8080/xmlrpc"));
//        this.config.setServerURL(new URL("http://cidar.bu.edu/miniEugeneXmlRpc/xmlrpc"));

        /*
         * we enable extensions
         */
        this.config.setEnabledForExtensions(true);
        this.config.setEnabledForExceptions(true);

		/*
		 * then we instantiate the XML-RPC Client
		 * and configure it 
		 */
		this.client = new XmlRpcClient(); 
        this.client.setConfig(config);
	}
	
	public void invokeMiniEugeneXMLRPC() 
			throws Exception {
		
		// read the script from a file
		String script = 
				FileUtils.readFile(new File("./data/design01.eug"));
		
        /*
         * SYNCHRONOUS Web Service Invocation
         */
        this.synchronousCall(script);
	}
	
	/**
	 * The synchronousCall method invokes the miniEugene XML-RPC 
	 * web service in a synchronous way. That is, we the client 
	 * is blocked until the web service response. 
	 * 
	 * @param rules  ... a set of miniEugene constraints
	 */
	private void synchronousCall(String script) 
			throws Exception {
		
		/*
		 * here, we invoke the executeEugene/1 method of 
		 * the miniEugene XML-RPC Web service
		 */
        Object object = client.execute(
        		"MiniEugeneXmlRpc.executeEugene", 
        		new Object[]{script});

        if(null != object) {
        	
        	if(object instanceof EugeneCollection) {
        		EugeneCollection results = 
        				(EugeneCollection)object;
        		
        		EugeneArray result = 
        				(EugeneArray)results.get("result");
        		
        		System.out.println(result.size());
        		
        	}
        	
        }

	}
	
//	/**
//	 * The processSolutions/1 method takes as input a 
//	 * set of miniEugene solutions (represented as Java object) and 
//	 * processes them by printing them to the console (using System.out).
//	 * 
//	 * @param solutions   ... a set of solutions
//	 */
//	private void processSolutions(Object solutions) {
//    	
//		Object[] sols = (Object[])solutions;
//    	
//		System.out.println("# of solutions: "+sols.length);
//    	
//    	// we iterate over the solutions 
//    	for(int i=0; i<sols.length; i++) {
//    		Object[] solution = (Object[])sols[i];
//
//    		// and print every solution
//    		for(int j=0; j<solution.length; j++) {
//    			System.out.print(solution[j]);
//    			if(j < solution.length - 1)
//    				System.out.print(", ");
//    		}
//    		
//    		System.out.println();
//    	}
//	}

	/**
	 * The main() method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new InvokeEugeneDemo01().invokeMiniEugeneXMLRPC();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
