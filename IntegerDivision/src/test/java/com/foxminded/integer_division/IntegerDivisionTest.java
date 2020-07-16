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
		assertEquals("_10|2\n 10|-\n --|5\n  0", getDivisionResult(10, 2));
		assertEquals("_15|5\n 15|-\n --|3\n  0", getDivisionResult(15, 5));
		assertEquals("_7|3\n 6|-\n --|2\n  1", getDivisionResult(7, 3));
		assertEquals("_1|1\n 1|-\n --|1\n  0", getDivisionResult(1, 1));
	}

	@Test
	void testOneStepDivisionWhenDividendIsLessThanDivisor() {
		assertEquals("_12|13\n  0|-\n --|0\n 12", getDivisionResult(12, 13));
	}

	private String getDivisionResult(int dividend, int divisor) {
		systemOutContent.reset();

		divider.divide(dividend, divisor);

		return systemOutContent.toString();
	}
}
