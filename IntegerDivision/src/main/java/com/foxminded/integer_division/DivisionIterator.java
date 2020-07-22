package com.foxminded.integer_division;

import java.util.NoSuchElementException;
import java.util.Stack;

public class DivisionIterator {
	private Stack<Integer> digitsOfRootDividend;

	private int rootDividend;
	private int rootDivisor;

	private int reminder;

	private int spaceCountBeforeReminder;

	private DivisionRawRepresentation rawRepresentation;

	private boolean isRoot = true;

	DivisionIterator(int dividend, int divisor) {

		if (divisor == 0) {
			throw new IllegalArgumentException("Divisor can't be zero");
		}

		this.rootDividend = dividend;
		this.rootDivisor = divisor;

		this.rawRepresentation = new DivisionRawRepresentation("");

		this.digitsOfRootDividend = getDigitsOfNumber(this.rootDividend);
	}


	public boolean hasNextDivisionStep() {
		return !digitsOfRootDividend.isEmpty();
	}

	public void nextDivisionStep() {

		if (!hasNextDivisionStep()) {
			throw new NoSuchElementException("No more division steps");
		}

		int dividend = getNumberFromStack();

		rawRepresentation = rawRepresentation.concat(new DivisionRawRepresentation(isRoot, hasNextDivisionStep(),
				rootDividend, rootDivisor, dividend, spaceCountBeforeReminder));

		reminder = dividend % rootDivisor;

		spaceCountBeforeReminder = spaceCountBeforeReminder
				+ getSpaceCountForTermAccordingToDividend(dividend, reminder);

		if (hasNextDivisionStep()) {
			spaceCountBeforeReminder--;
			isRoot = false;
		}
	}

	@Override
	public String toString() {
		return rawRepresentation.toString();
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
}
