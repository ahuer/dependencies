package com.points.dependencies

import static org.junit.Assert.*

import org.junit.Test

import com.points.filereading.FileReading;

class SystemTest {
	private String filename = "src/test/groovy/testInput.txt"
	
	@Test
	public void testInstallRemove() {
		def list = ["INSTALL NETCARD", "REMOVE NETCARD", "END"]
		def system = new System()
		system.processInput(list)
		assertEquals(0, system.getInstalledComponents().size())
		assertEquals(true, system.getPossibleComponents().containsKey("NETCARD"))
	}

	@Test
	public void testFullSystem() {
		def list = FileReading.readFile(filename)
		def system = new System()
		system.processInput(list)
		assertEquals(2, system.getInstalledComponents().size())
		assertEquals(7, system.getPossibleComponents().size())
	}
}
