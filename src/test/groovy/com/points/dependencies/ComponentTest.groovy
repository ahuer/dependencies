package com.points.dependencies

import static org.junit.Assert.*
import org.junit.Test

class ComponentTest {
	
	@Test
	public void testNameEqual() {
		def comp1 = new Component("first")
		comp1.addDependency("program")
		def comp2 = new Component("first")
		assertEquals(true, comp1.equals(comp2))
	}
	
	@Test
	public void testNotEqual() {
		def comp1 = new Component("first")
		def comp2 = new Component("second")
		assertEquals(false, comp1.equals(comp2))
	}

}
