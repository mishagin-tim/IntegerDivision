package com.foxminded.integer_division;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IntegerDivisionTest {
	private final ByteArrayOutputStream systemOutContent = new ByteArrayOutputStream();
	private final PrintStream originalSystemOut = System.out;
	private IntegerDivider divider;
	/* Handle standard output stream with ByteArrayOutputStream */
	@BeforeEach
	void setUpStreams() {
		System.setOut(new PrintStream(systemOutContent));
		this.divider = new IntegerDivider();
	}

	/* Restore standard output stream */
	@AfterEach
	void restoreStreams() {
		System.setOut(originalSystemOut);
	}

	@Test
	void divideShouldThrowIllegalArgumentExceptionWhenDivisorIsZero() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			getDivisionResult(123, 0);
		});
		assertEquals(exception.getMessage(), "Divisor can't be zero");
	}

	@Test
	void testOneStepDivision() {

		assertEquals("_1|1\n" + 
					 " 1|-\n" + 
					 " -|1\n" + 
					 " 0", getDivisionResult(1, 1));

		assertEquals("_3|12\n" + 
					 " 0|--\n" + 
					 " -|0\n" + 
					 " 3", getDivisionResult(3, 12));
		
		assertEquals("_5|3\n" + 
					 " 3|-\n" + 
					 " -|1\n" + 
					 " 2", getDivisionResult(5, 3));

		assertEquals("_11|3\n" + 
					 "  9|-\n" + 
					 " --|3\n" + 
					 "  2", getDivisionResult(11, 3));
		
		assertEquals("_26|12\n" + 
					 " 24|--\n" + 
					 " --|2\n" + 
					 "  2", getDivisionResult(26, 12));

		assertEquals("_543|500\n" + 
					 " 500|---\n" + 
					 " ---|1\n" + 
					 "  43", getDivisionResult(543, 500));
	}

	@Test
	void testTwoStepDivision() {
		assertEquals("_145|5\n" + 
					 " 10 |--\n" + 
					 " -- |29\n" + 
					 " _45\n" + 
					 "  45\n" + 
					 "  --\n" + 
					 "   0", getDivisionResult(145, 5));

		assertEquals("_144|12\n" + 
					 " 12 |--\n" + 
					 " -- |12\n" + 
					 " _24\n" + 
					 "  24\n" + 
					 "  --\n" + 
					 "   0", getDivisionResult(144, 12));
		
		assertEquals("_1024|32\n" + 
					 "  96 |--\n" + 
					 " --- |32\n" + 
					 "  _64\n" + 
					 "   64\n" + 
					 "   --\n" + 
					 "    0", getDivisionResult(1024, 32));
	}

	@Test
	void testMultipleStepDivision() {
		assertEquals("_1245|5\n" + 
					 " 10  |---\n" + 
					 " --  |249\n" + 
					 " _24\n" + 
					 "  20\n" + 
					 "  --\n" + 
					 "  _45\n" + 
					 "   45\n" + 
					 "   --\n" + 
					 "    0",
				getDivisionResult(1245, 5));
		
		assertEquals("_135151512|241\n" + 
					 " 1205     |------\n" +
					 " ----     |560794\n" + 
					 " _1465\n" + 
					 "  1446\n" + 
					 "  ----\n" + 
					 "   _1915\n" + 
					 "    1687\n" + 
					 "    ----\n" + 
					 "    _2281\n" + 
					 "     2169\n" + 
					 "     ----\n" + 
					 "     _1122\n" + 
					 "       964\n" + 
					 "      ----\n" + 
					 "       158", getDivisionResult(135151512, 241));

		assertEquals("_8742965|1334\n" + 
					 " 8004   |----\n" +
					 " ----   |6553\n" + 
					 " _7389\n" + 
					 "  6670\n" + 
					 "  ----\n" + 
					 "  _7196\n" + 
					 "   6670\n" + 
					 "   ----\n" + 
					 "   _5265\n" + 
					 "    4002\n" + 
					 "    ----\n" + 
					 "    1263", getDivisionResult(8742965, 1334));

		assertEquals("_123456789|7\n" + 
					 "  7       |--------\n" + 
					 " --       |17636684\n" + 
					 " _53\n" + 
					 "  49\n" + 
					 "  --\n" + 
					 "  _44\n" + 
					 "   42\n" + 
					 "   --\n" + 
					 "   _25\n" + 
					 "    21\n" + 
					 "    --\n" + 
					 "    _46\n" + 
					 "     42\n" + 
					 "     --\n" + 
					 "     _47\n" + 
					 "      42\n" + 
					 "      --\n" + 
					 "      _58\n" + 
					 "       56\n" + 
					 "       --\n" + 
					 "       _29\n" + 
					 "        28\n" + 
					 "        --\n" + 
					 "         1", getDivisionResult(123456789, 7));
	}

	private String getDivisionResult(int dividend, int divisor) {
		systemOutContent.reset();

		// DivisionBuffer divisionBuffer = divider.divide(dividend, divisor);
		System.out.print(divider.divide(dividend, divisor));

		return systemOutContent.toString();
	}
}
