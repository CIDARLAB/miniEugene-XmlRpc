package org.cidarlab.minieugene.xmlrpc;

import java.util.Arrays;
import java.util.List;

import org.cidarlab.minieugene.MiniEugene;
import org.cidarlab.minieugene.dom.Component;

public class MiniEugeneXmlRpc {

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
				me.solve(constraints, N.intValue(), NR_OF_SOLUTIONS.intValue());
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
				me.solve(constraints, N.intValue());
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
				me.solve(script);
			} catch(Exception e) {
				throw new Exception(e.getMessage());
			}
			
			String[][] result = this.toStringMatrix(me.getSolutions());
			for(int i=0; i<result.length; i++) {
				System.out.println(Arrays.toString(result[i]));
			}
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
