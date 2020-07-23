package com.foxminded.integer_division;

import java.util.NoSuchElementException;
import java.util.Stack;

public class DivisionIterator {
	private Stack<Integer> digitsOfRootDividend;

	private int rootDividend;
	private int rootDivisor;
	private int rootQuotient;

	private int dividend;
	private int quotient;
	private int minuend;
	private int reminder;

	private int spaceCountBeforeMinuend;
	private int spaceCountBeforeDashes;
	private int spaceCountBeforeReminder;
	private int spaceCountAfterReminder;

	DivisionIterator(int dividend, int divisor) {

		if (divisor == 0) {
			throw new IllegalArgumentException("Divisor can't be zero");
		}

		rootDividend = dividend;
		rootDivisor = divisor;
		rootQuotient = dividend / divisor;

		digitsOfRootDividend = getDigitsOfNumber(this.rootDividend);
	}


	public boolean hasNextDivisionStep() {
		return !digitsOfRootDividend.isEmpty();
	}

	public void nextDivisionStep() {

		if (!hasNextDivisionStep()) {
			throw new NoSuchElementException("No more division steps");
		}

		dividend = getNumberFromStack();
		quotient = dividend / rootDivisor;
		minuend = quotient * rootDivisor;
		reminder = dividend % rootDivisor;

		spaceCountBeforeMinuend = spaceCountBeforeReminder 
				+ getSpaceCountForTermAccordingToDividend(dividend, minuend);
		spaceCountBeforeDashes = spaceCountBeforeReminder 
				+ getSpaceCountForTermAccordingToDividend(dividend, dividend);
		spaceCountBeforeReminder = spaceCountBeforeReminder
				+ getSpaceCountForTermAccordingToDividend(dividend, dividend % rootDivisor);
		spaceCountAfterReminder = getSpaceCountForTermAccordingToDividend(rootDividend, dividend % rootDivisor)
				- spaceCountBeforeReminder;

		if (hasNextDivisionStep()) {
			spaceCountBeforeReminder--;
		}
	}
	
	private Stack<Integer> getDigitsOfNumber(int number) {
		Stack<Integer> digits = new Stack<>();

		do {
			digits.add(number % 10);
			number /= 10;
		} while (number > 0);

		return digits;
	}

	private int getNumberFromStack() {
		int temp = this.reminder;

		while (!digitsOfRootDividend.isEmpty() && temp < this.rootDivisor) {
			temp = temp * 10 + digitsOfRootDividend.pop();
		}

		return temp;
	}

	private int getSpaceCountForTermAccordingToDividend(int dividend, int term) {
		return getDigitCount(dividend) + 1 - getDigitCount(term);
	}

	private int getDigitCount(int num) {
		return String.valueOf(num).length();
	}

	int getRootDividend() {
		return rootDividend;
	}

	int getRootDivisor() {
		return rootDivisor;
	}

	int getRootQuotient() {
		return rootQuotient;
	}

	int getDividend() {
		return dividend;
	}

	int getQuotient() {
		return quotient;
	}

	int getMinuend() {
		return minuend;
	}

	int getReminder() {
		return reminder;
	}

	int getSpaceCountBeforeMinuend() {
		return spaceCountBeforeMinuend;
	}

	int getSpaceCountBeforeDashes() {
		return spaceCountBeforeDashes;
	}

	int getSpaceCountBeforeReminder() {
		return spaceCountBeforeReminder;
	}

	int getSpaceCountAfterReminder() {
		return spaceCountAfterReminder;
	}
}
