package expression;

import static org.junit.Assert.*;

import org.junit.Test;

public class Exp {

	@Test
	public void test1() {
		Expression e = new Expression();
		assertFalse(e.isValidExpression("l+t"));
	}
	
	@Test
	public void test2() {
		Expression e = new Expression();
		assertTrue(e.isValidExpression("l-t"));
	}
	
	@Test
	public void test3() {
		Expression e = new Expression();
		assertTrue(e.isValidExpression("-l+t"));
	}
	
	@Test
	public void test4() {
		Expression e = new Expression();
		assertFalse(e.isValidExpression("+l+t"));
	}
	
	@Test
	public void test5() {
		Expression e = new Expression();
		assertFalse(e.isValidExpression("-l-t"));
	}
	
	@Test
	public void test6() {
		Expression e = new Expression();
		assertFalse(e.isValidExpression("-l-t+qa"));
	}

}
