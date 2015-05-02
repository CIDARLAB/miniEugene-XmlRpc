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

package org.cidarlab.minieugene.xmlrpc;

import java.util.List;

import org.cidarlab.minieugene.MiniEugene;
import org.cidarlab.minieugene.dom.Component;

public class MiniEugeneXmlRpc {

	private static final int MAX_NR_OF_SOLUTIONS = 50000;
	
	/**
	 * The solve/3 method takes as input a set of rules, the max. length of the desired 
	 * design, and the number of desired solutions.
	 * 
	 * The solve/3 method automatically generates a well-formed miniEugene script.
	 * 
	 * The solve/3 method returns a String matrix (String[][]) containing NR_OF_SOLUTIONS 
	 * rule-compliant designs. Every row in the String matrix represents one solution. 
	 * Every column in a row denotes one component of a solution, represented as String which refers 
	 * to the specified name of the component. If the first character of the row column is '-', 
	 * then the component is reverse oriented. 
	 * 
	 * @param rules             an Object array (Object[]) of miniEugene rules
	 * @param N                 the max. length of the desired design
	 * @param NR_OF_SOLUTIONS   the desired number of solutions
	 * @return a String matrix (String[][]) containing all rule-compliant designs
	 * @throws Exception
	 */
	public String[][] solve(Object[] rules, Integer N, Integer NR_OF_SOLUTIONS) 
			throws Exception {

		if(null != rules) {
			MiniEugene me = new MiniEugene();				
			try {
				/*
				 * convert the Object[] to a String[]
				 */
				String[] constraints = new String[rules.length];
				for(int i=0; i<rules.length; i++) {
					constraints[i] = new String(rules[i].toString());
				}

				/*
				 * solve the problem
				 */
				if(NR_OF_SOLUTIONS > MAX_NR_OF_SOLUTIONS) {
					me.solve(constraints, N.intValue(), MAX_NR_OF_SOLUTIONS);
				} else {
					me.solve(constraints, N.intValue(), NR_OF_SOLUTIONS.intValue());
				}
			} catch(Exception e) {
				throw new Exception(e.getMessage());
			}
			
			/*
			 * return the solutions as a String[][]
			 */
			return this.toStringMatrix(me.getSolutions());
		}
		
		return null;
	}
	
	/**
	 * The solve/2 method takes as input a set of rules and the max. length of the desired 
	 * design.
	 * 
	 * The solve/2 method automatically generates a well-formed miniEugene script.
	 * 
	 * The solve/2 method returns a String matrix (String[][]) containing all 
	 * rule-compliant designs. Every row in the String matrix represents one solution. 
	 * Every column in a row denotes one component of a solution, represented as String which refers 
	 * to the specified name of the component. If the first character of the row column is '-', 
	 * then the component is reverse oriented. 
	 * 
	 * @param rules             an Object array (Object[]) of miniEugene rules
	 * @param N                 the max. length of the desired design
	 * @param NR_OF_SOLUTIONS   the desired number of solutions
	 * @return a String matrix (String[][]) containing all rule-compliant designs
	 * @throws Exception
	 */
	public String[][] solve(Object[] rules, Integer N) 
			throws Exception {
		
		return solve(rules, N, MAX_NR_OF_SOLUTIONS);

//		if(null != rules) {
//			MiniEugene me = new MiniEugene();				
//			try {
//				/*
//				 * convert the Object[] to a String[]
//				 */
//				String[] constraints = new String[rules.length];
//				for(int i=0; i<rules.length; i++) {
//					constraints[i] = new String(rules[i].toString());
//				}
//
//				
//				/*
//				 * solve the problem
//				 */
//				me.solve(constraints, N.intValue(), MAX_NR_OF_SOLUTIONS);
//			} catch(Exception e) {
//				throw new Exception(e.getMessage());
//			}
//			
//			/*
//			 * return the solutions as a String[][]
//			 */
//			return this.toStringMatrix(me.getSolutions());
//		}
//		
//		return null;
	}

	/**
	 * The solve/1 method takes as input a miniEugene script. For further information on 
	 * well-formed miniEugene scripts, please refer to the ACS SynBio Technical Note:
	 * E. Oberortner, D. Densmore. miniEugene: Constraint-based Design of Synthetic Biological Systems.
	 * 
	 * The solve/3 method returns a String matrix (String[][]) containing all 
	 * rule-compliant designs. Every row in the String matrix represents one solution. 
	 * Every column in a row denotes one component of a solution, represented as String which refers 
	 * to the specified name of the component. If the first character of the row column is '-', 
	 * then the component is reverse oriented. 

	 * @param script The miniEugene Script
	 * @return a String matrix (String[][]) containing all rule-compliant designs
	 * @throws Exception
	 */
	public String[][] solve(String script) 
			throws Exception {
		
		if(null != script && !script.isEmpty()) {
			MiniEugene me = new MiniEugene();				
			try {
				/*
				 * execute the script
				 */
				me.solve(script, MAX_NR_OF_SOLUTIONS);
			} catch(Exception e) {
				throw new Exception(e.getMessage());
			}
			
			String[][] result = this.toStringMatrix(me.getSolutions());
//			for(int i=0; i<result.length; i++) {
//				System.out.println(Arrays.toString(result[i]));
//			}
			return result;
		}
		return null;
	}
	

	private String[][] toStringMatrix(List<Component[]> solutions) {
		String[][] response = new String[solutions.size()][solutions.get(0).length];
		int l = 0;
		for(Component[] solution : solutions) {
			
			for(int k=0; k<solution.length; k++) {
				if(!solution[k].isForward()) {
					response[l][k] = "-"+solution[k].getName();
				} else {
					response[l][k] = solution[k].getName();
				}
			}
			
			l++;				
		}
		
		return response;
	}
}
