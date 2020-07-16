package com.foxminded.integer_division;

public class IntegerDivision {

	public void divide(int dividend, int divisor) {
		String template = "_%d|%d\n %d|-\n --|%d\n  %d";
		int quotient = dividend / divisor;
		int reminder = dividend % divisor;

		String result = String.format(template, dividend, divisor, divisor * quotient, quotient, reminder);

		System.out.print(result);
	}
}
