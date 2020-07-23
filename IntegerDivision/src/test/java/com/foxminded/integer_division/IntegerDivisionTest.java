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

	@Test
	void divideShouldThrowIllegalArgumentExceptionWhenDivisorIsZero() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> getDivisionResult(123, 0));
		
		String expected = "Divisor can't be zero";

		String actual = exception.getMessage();

		assertEquals(expected, actual);
	}
	
	@Test
	void integerDivisionShouldReturnOneStepDivisionWithZeroQuotientWhenDividendIsZero() {
		String expected = "_0|123\n" + 
						  " 0|---\n" + 
						  " -|0\n" + 
						  " 0";

		String actual = getDivisionResult(0, 123);

		assertEquals(expected, actual);
	}
	
	@Test
	void integerDivisionShouldReturnOneByOneDivisionRepresentationWhenOneIsDividedByOne() {
		String expected = "_1|1\n" + 
		 		  " 1|-\n" + 
		 		  " -|1\n" + 
		 		  " 0";

		String actual = getDivisionResult(1, 1);

		assertEquals(expected, actual);
	}
	
	@Test
	void IntegerDivisionShouldReturnOneStepDivisionRepresentationWhenDividendIsLessThanDivisor() {
		String expected = "_3|12\n" + 
				   " 0|--\n" + 
				   " -|0\n" + 
				   " 3";

		String actual = getDivisionResult(3, 12);

		assertEquals(expected, actual);
	}
	
	@Test
	void integerDivisionShouldReturnOneStepDivisionRepresentationWhenDivisionIsOneStep() {

		String expected = "_5|3\n" + 
				   " 3|-\n" + 
				   " -|1\n" + 
				   " 2";

		String actual = getDivisionResult(5, 3);

		assertEquals(expected, actual);


		expected = "_26|12\n" + 
				   " 24|--\n" + 
				   " --|2\n" + 
				   "  2";

		actual = getDivisionResult(26, 12);

		assertEquals(expected, actual);
		

		expected = "_543|500\n" + 
				   " 500|---\n" + 
				   " ---|1\n" + 
				   "  43";

		actual = getDivisionResult(543, 500);

		assertEquals(expected, actual);
	}

	@Test
	void integerDivisionShouldReturnTwoStepDivisionRepresentationWhenDivisionIsTwoStep() {
		String expected = "_145|5\n" + 
				 		  " 10 |--\n" + 
				 		  " -- |29\n" + 
				 		  " _45\n" + 
				 		  "  45\n" + 
				 		  "  --\n" + 
				 		  "   0";

		String actual = getDivisionResult(145, 5);

		assertEquals(expected, actual);
		

		expected = "_144|12\n" + 
				   " 12 |--\n" + 
				   " -- |12\n" + 
				   " _24\n" + 
				   "  24\n" + 
				   "  --\n" + 
				   "   0";

		actual = getDivisionResult(144, 12);

		assertEquals(expected, actual);
		

		expected = "_1024|32\n" + 
				   "  96 |--\n" + 
				   " --- |32\n" + 
				   "  _64\n" + 
				   "   64\n" + 
				   "   --\n" + 
				   "    0";

		actual = getDivisionResult(1024, 32);

		assertEquals(expected, actual);
	}

	@Test
	void integerDivisionShouldReturnThreeStepDivisionRepresentationWhenDivisionIsThreeStep() {
		String expected = "_1245|5\n" + 
				   " 10  |---\n" + 
				   " --  |249\n" + 
				   " _24\n" + 
				   "  20\n" + 
				   "  --\n" + 
				   "  _45\n" + 
				   "   45\n" + 
				   "   --\n" + 
				   "    0";

		String actual = getDivisionResult(1245, 5);

		assertEquals(expected, actual);
		

		expected = "_135151512|241\n" + 
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
				   "       158";

		actual = getDivisionResult(135151512, 241);

		assertEquals(expected, actual);
		

		expected = "_123456789|7\n" + 
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
				   "         1";

		actual = getDivisionResult(123456789, 7);

		assertEquals(expected, actual);
	}

	private String getDivisionResult(int dividend, int divisor) {
		systemOutContent.reset();

		System.out.print(new IntegerDivision(dividend, divisor));

		return systemOutContent.toString();
	}
}
