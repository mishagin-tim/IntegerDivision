package com.foxminded.integer_division;

import java.util.NoSuchElementException;
import java.util.Stack;

public class IntegerDivider {

	public DivisionIterator divide(int dividend, int divisor) {
		DivisionIterator divisionIterator = new DivisionIterator(dividend, divisor);

		while (divisionIterator.hasNextDivisionStep()) {
			divisionIterator.nextDivisionStep();
		}

		return divisionIterator;
	}
}

class DivisionIterator {
	
	private static final String VALUE_TEMPLATE = "%d";
	
	private static final String NEW_LINE = "\n";
	private static final String DELIMITER = "|";
	private static final String DASH = "_";

	private Stack<Integer> digitsOfRootDividend;

	private int rootDividend;
	private int rootQuotient;
	private int rootDivisor;

	private int reminder;

	private int spaceCountBeforeMinuend;
	private int spaceCountBeforeReminder;
	private int spaceCountAfterReminder;
	private int spaceCountBeforeDashesBetweenMinuendAndReminder;

	private boolean isRoot;

	private String rawRepresentation;

	DivisionIterator(int dividend, int divisor) {
		this.rootDividend = dividend;
		this.rootDivisor = divisor;
		this.rootQuotient = this.rootDividend / this.rootDivisor;

		this.rawRepresentation = "";

		this.digitsOfRootDividend = getDigitsOfNumber(this.rootDividend);

		this.isRoot = true;
	}

	@Override
	public String toString() {
		return rawRepresentation;
	}

	public boolean hasNextDivisionStep() {
		return !digitsOfRootDividend.isEmpty();
	}

	public void nextDivisionStep() {

		if (!hasNextDivisionStep()) {
			throw new NoSuchElementException("No more division steps");
		}
		
		int dividend = getNumberFromStack();
		int quotient = dividend / rootDivisor;
		int minuend = quotient * rootDivisor;
		reminder = dividend % rootDivisor;

		spaceCountBeforeReminder = spaceCountBeforeReminder
				+ getSpaceCountForTermAccordingToDividend(dividend, reminder);
		
		spaceCountBeforeMinuend = spaceCountBeforeMinuend
				+ getSpaceCountForTermAccordingToDividend(dividend, minuend);

		spaceCountBeforeDashesBetweenMinuendAndReminder++;

		spaceCountAfterReminder = getSpaceCountForTermAccordingToDividend(rootDividend, reminder)
				- spaceCountBeforeReminder;

		if (isRoot) {
			addDash();
			addDividend(rootDividend);
			addDelimiter();
			addDivisor(rootDivisor);
			addNewLine();
			addSpacesBeforeMinuend();
			addMinuend(minuend);
			addSpacesAfterReminder();
			addDelimiter();
			addDashesBetweenDivisorAndQuotient(rootDivisor, rootQuotient);
			addNewLine();
			addSpacesBeforeDashesBetweenMinuendAndReminder();
			addDashesBetweenMinuendAndReminder(dividend, minuend);
			addSpacesAfterReminder();
			addDelimiter();
			addQuotient(rootQuotient);
			addNewLine();

			if (hasNextDivisionStep()) {
				spaceCountBeforeReminder--;
				addSpacesBeforeReminder();
			} else {
				addSpacesBeforeReminder();
				addReminder(reminder);
			}

		} else {
			addDash();
			addDividend(dividend);
			addNewLine();
			addSpacesBeforeMinuend();
			addMinuend(minuend);
			addNewLine();
			addSpacesBeforeMinuend();
			addDashesBetweenMinuendAndReminder(dividend, minuend);
			addNewLine();
			
			if (hasNextDivisionStep()) {
				spaceCountBeforeReminder--;
				addSpacesBeforeReminder();
			} else {
				addSpacesBeforeReminder();
				addReminder(reminder);
			}
		}
		isRoot = false;
	}

	private void addDividend(int dividend) {
		rawRepresentation = rawRepresentation + String.format(VALUE_TEMPLATE, dividend);
	}

	private void addDivisor(int divisor) {
		rawRepresentation = rawRepresentation + String.format(VALUE_TEMPLATE, divisor);
	}

	private void addMinuend(int minuend) {
		rawRepresentation = rawRepresentation + String.format(VALUE_TEMPLATE, minuend);
	}

	private void addQuotient(int quotient) {
		rawRepresentation = rawRepresentation + String.format(VALUE_TEMPLATE, quotient);
	}

	private void addReminder(int reminder) {
		rawRepresentation = rawRepresentation + String.format(VALUE_TEMPLATE, reminder);
	}

	private void addNewLine() {
		rawRepresentation = rawRepresentation + NEW_LINE;
	}

	private void addDelimiter() {
		rawRepresentation = rawRepresentation + DELIMITER;
	}

	private void addDash() {
		rawRepresentation = rawRepresentation + DASH;
	}

	private void addSpacesBeforeMinuend() {
		rawRepresentation = rawRepresentation + generateStringWithChar(spaceCountBeforeMinuend, ' ');
	}

	private void addSpacesBeforeReminder() {
		rawRepresentation = rawRepresentation + generateStringWithChar(spaceCountBeforeReminder, ' ');
	}

	private void addSpacesBeforeDashesBetweenMinuendAndReminder() {
		rawRepresentation = rawRepresentation
				+ generateStringWithChar(spaceCountBeforeDashesBetweenMinuendAndReminder, ' ');
	}

	private void addSpacesAfterReminder() {
		rawRepresentation = rawRepresentation + generateStringWithChar(spaceCountAfterReminder, ' ');
	}

	private void addDashesBetweenDivisorAndQuotient(int divisor, int quotient) {
		rawRepresentation = rawRepresentation
				+ getDashesBetweenTerms(divisor, quotient);
	}

	private void addDashesBetweenMinuendAndReminder(int dividend, int minuend) {
		rawRepresentation = rawRepresentation
				+ getDashesBetweenTerms(dividend, minuend);
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
		return getDigitsOfNumber(num).size();
	}

	private Stack<Integer> getDigitsOfNumber(int number) {
		Stack<Integer> digits = new Stack<>();

		do {
			digits.add(number % 10);
			number /= 10;
		} while (number > 0);

		return digits;
	}

	private String getDashesBetweenTerms(int term1, int term2) {
		int maxBetweenTerms = Math.max(term1, term2);
		int digitCount = getDigitCount(maxBetweenTerms);

		return generateStringWithChar(digitCount, '-');
	}

	private String generateStringWithChar(int count, char c) {
		StringBuilder builder = new StringBuilder("");

		for (int i = 0; i < count; i++) {
			builder.append(c);
		}

		return builder.toString();
	}
}
