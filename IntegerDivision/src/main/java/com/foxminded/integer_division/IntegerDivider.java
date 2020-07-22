package com.foxminded.integer_division;

public class IntegerDivider {

	public DivisionIterator divide(int dividend, int divisor) {
		DivisionIterator divisionIterator = new DivisionIterator(dividend, divisor);

		while (divisionIterator.hasNextDivisionStep()) {
			divisionIterator.nextDivisionStep();
		}

		return divisionIterator;
	}
}

