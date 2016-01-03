package com.points.filereading

import static org.junit.Assert.*
import org.junit.Test

class FileReadingTest {
	private String filename = "src/test/groovy/testInput.txt"
	
	@Test
	public void testReadingFile() {
		def list = FileReading.readFile(filename)
		assertEquals(20, list.size())
		assertEquals("DEPEND TELNET TCPIP NETCARD", list.get(0))
		assertEquals("END", list.get(list.size() - 1))
	}
}
