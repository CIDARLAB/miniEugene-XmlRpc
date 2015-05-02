package org.cidarlab.minieugene.xmlrpc.unittests;

import static org.junit.Assert.*;

import java.util.List;

import org.cidarlab.minieugene.MiniEugene;
import org.cidarlab.minieugene.dom.Component;
import org.junit.Test;

/**
 * 
 * @author Ernst Oberortner
 */
public class NextToTest {

	@Test
	public void test_AnexttoB_N2() {
		String script = "N=2.A nextto B.all_forward.";
		
		try {
			
			MiniEugene me = new MiniEugene();
			
			me.solve(script);
			
			List<Component[]> solutions = me.getSolutions();
			assertTrue(null != solutions);
			assertTrue(solutions.size() == 4);
			// A, A
			// A, B
			// B, A
			// B, B

		} catch(Exception e) {
			e.printStackTrace();
			assertTrue(false);	// no exception allowed
		}
	}
	
	@Test
	public void test_AnexttoB_N3() {
		String script = "N=3.A nextto B.all_forward.";
		
		try {
			
			MiniEugene me = new MiniEugene();
			
			me.solve(script);
			
			List<Component[]> solutions = me.getSolutions();
			assertTrue(null != solutions);
			
			/*
			 * SOLUTIONS:
			 * A A A
			 * A A B  // WRONG
			 * A B A
			 * A B B  // WRONG
			 * 
			 * B A A  // WRONG
			 * B A B
			 * B B A  // WRONG
			 * B B B
			 */
			assertTrue(solutions.size() == 4);

		} catch(Exception e) {
			e.printStackTrace();
			assertTrue(false);	// no exception allowed
		}
	}

	@Test
	public void test_NOT_AnexttoB_N2() {
		String script = "N=2.NOT A nextto B.all_forward.";
		
		try {
			
			MiniEugene me = new MiniEugene();
			
			me.solve(script);
			
			List<Component[]> solutions = me.getSolutions();
			assertTrue(null != solutions);
			assertTrue(solutions.size() == 2);
			// A, A
			// B, B

		} catch(Exception e) {
			e.printStackTrace();
			assertTrue(false);	// no exception allowed
		}
	}

}
