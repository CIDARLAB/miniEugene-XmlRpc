package org.cidarlab.minieugene.xmlrpc;

import java.util.Arrays;
import java.util.List;

import org.cidarlab.minieugene.MiniEugene;
import org.cidarlab.minieugene.dom.Component;

public class MiniEugeneXmlRpc {

	/**
	 * 
	 * @param rules
	 * @param N
	 * @param NR_OF_SOLUTIONS
	 * @return
	 * @throws Exception
	 */
	public String[][] solve(Object[] rules, Integer N, Integer NR_OF_SOLUTIONS) 
			throws Exception {

		if(null != rules) {
			MiniEugene me = new MiniEugene();				
			try {
				/*
				 * convert the Object[] to a String[]
				 * 
				 * I know... we could do this better... :)
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
			 * 
			 * again... we could do this more efficient...
			 */
			return this.toStringMatrix(me.getSolutions());
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param script
	 * @return
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
