package com.foxminded.integer_division;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.commons.text.StringEscapeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IntegerDivisionTest {
	private final ByteArrayOutputStream systemOutContent = new ByteArrayOutputStream();
	private final PrintStream originalSystemOut = System.out;

	private IntegerDivision divider;

	/* For testing console output */
	@BeforeEach
	void setUpStreams() {
		System.setOut(new PrintStream(systemOutContent));
		divider = new IntegerDivision();
	}

	/* For testing console output */
	@AfterEach
	void restoreStreams() {
		System.setOut(originalSystemOut);
	}

	@Test
	void testDivision() {
		divider.divide(10, 2);
		String test = StringEscapeUtils.escapeJava("_10|2\n 10|-\n --|5\n  0");
		assertEquals(test, systemOutContent.toString());
	}
}
