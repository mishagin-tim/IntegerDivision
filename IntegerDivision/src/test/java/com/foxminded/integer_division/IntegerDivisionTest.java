package com.foxminded.integer_division;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IntegerDivisionTest {
	private final ByteArrayOutputStream systemOutContent = new ByteArrayOutputStream();
	private final PrintStream originalSystemOut = System.out;

	private IntegerDivision divider;

	/* Handle standard output stream with ByteArrayOutputStream */
	@BeforeEach
	void setUpStreams() {
		System.setOut(new PrintStream(systemOutContent));
		divider = new IntegerDivision();
	}

	/* Restore standard output stream */
	@AfterEach
	void restoreStreams() {
		System.setOut(originalSystemOut);
	}

	@Test
	void testOneStepDivision() {
		assertEquals("_3|12\n 0|--\n -|0\n 3", getDivisionResult(3, 12));
		assertEquals("_5|3\n 3|-\n -|1\n 2", getDivisionResult(5, 3));
		assertEquals("_11|3\n  9|-\n --|3\n  2", getDivisionResult(11, 3));
		assertEquals("_26|12\n 24|--\n --|2\n  2", getDivisionResult(26, 12));
		
		assertEquals("_101|25\n 100|--\n ---|4\n   1", getDivisionResult(101, 25));
		assertEquals("_101|25\n 100|--\n ---|4\n   1", getDivisionResult(543, 500));
	}

	private String getDivisionResult(int dividend, int divisor) {
		systemOutContent.reset();

		divider.divide(dividend, divisor);

		return systemOutContent.toString();
	}
}
