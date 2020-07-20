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

	/* Handle standard output stream with ByteArrayOutputStream */
	@BeforeEach
	void setUpStreams() {
		System.setOut(new PrintStream(systemOutContent));
	}

	/* Restore standard output stream */
	@AfterEach
	void restoreStreams() {
		System.setOut(originalSystemOut);
	}

	void testOneStepDivision() {
		assertEquals("_3|12\n 0|--\n -|0\n 3", getDivisionResult(3, 12));
		assertEquals("_5|3\n 3|-\n -|1\n 2", getDivisionResult(5, 3));
		assertEquals("_11|3\n  9|-\n --|3\n  2", getDivisionResult(11, 3));
		assertEquals("_26|12\n 24|--\n --|2\n  2", getDivisionResult(26, 12));
		
		assertEquals("_101|25\n 100|--\n ---|4\n   1", getDivisionResult(101, 25));
		assertEquals("_543|500\n 500|---\n ---|1\n  43", getDivisionResult(543, 500));
		assertEquals("_43|500\n  0|---\n --|0\n 43", getDivisionResult(43, 500));
		assertEquals("_60|50\n 50|--\n --|1\n 10", getDivisionResult(60, 50));
	}

	@Test
	void testTwoStepDivision() {
		assertEquals("_145|5\n 10 |--\n -- |29\n _45\n  45\n  --\n   0", getDivisionResult(145, 5));
		assertEquals("_144|12\n 12 |--\n -- |12\n _24\n  24\n  --\n   0", getDivisionResult(144, 12));
		assertEquals("_1024|32\n  96 |--\n --- |32\n  _64\n   64\n   --\n    0", getDivisionResult(1024, 32));
	}

	@Test
	void testThreeStepDivision() {
		assertEquals("_1245|5\n 10  |---\n --  |249\n _24\n  20\n  --\n  _45\n   45\n   --\n    0",
				getDivisionResult(1245, 5));
	}

	private String getDivisionResult(int dividend, int divisor) {
		systemOutContent.reset();

		System.out.print(new IntegerDivision(dividend, divisor));

		return systemOutContent.toString();
	}
}
