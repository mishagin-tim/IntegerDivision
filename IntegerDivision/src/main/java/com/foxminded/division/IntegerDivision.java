package com.foxminded.division;

public class IntegerDivision {
	private static final String VALUE_TEMPLATE = "%d";
	private static final String NEW_LINE = "\n";
	private static final String DELIMITER = "|";
	private static final String UNDERSCORE = "_";

	private String rawRepresentation = "";
	boolean isRoot = true;

	private DivisionIterator divisionIterator;

	IntegerDivision(int dividend, int divisor) {
		this.divisionIterator = new DivisionIterator(dividend, divisor);
		divide();
	}

	private void divide() {
		while (divisionIterator.hasNextDivisionStep()) {
			divisionIterator.nextDivisionStep();

			int rootDividend = divisionIterator.getRootDividend();
			int rootDivisor = divisionIterator.getRootDivisor();
			int rootQuotient = divisionIterator.getRootQuotient();

			int dividend = divisionIterator.getDividend();
			int minuend = divisionIterator.getMinuend();

			int spaceCountBeforeMinuend = divisionIterator.getSpaceCountBeforeMinuend();
			int spaceCountBeforeDashes = divisionIterator.getSpaceCountBeforeDashes();
			int spaceCountBeforeReminder = divisionIterator.getSpaceCountBeforeReminder();
			int spaceCountAfterReminder = divisionIterator.getSpaceCountAfterReminder();

			addUnderscore();

			if (isRoot) {
				add(rootDividend);
				addDelimiter();
				add(rootDivisor);
				addNewLine();
				addSpaces(spaceCountBeforeMinuend);
				add(minuend);
				addSpaces(spaceCountAfterReminder);
				addDelimiter();
				addDashesBetween(rootDivisor, rootQuotient);
				addNewLine();
				addSpaces(spaceCountBeforeDashes);
				addDashesBetween(dividend, minuend);
				addSpaces(spaceCountAfterReminder);
				addDelimiter();
				add(rootQuotient);

				isRoot = false;
			} else {
				add(dividend);
				addNewLine();
				addSpaces(spaceCountBeforeMinuend);
				add(minuend);
				addNewLine();
				addSpaces(spaceCountBeforeDashes);
				addDashesBetween(dividend, minuend);
			}

			addNewLine();
			addSpaces(spaceCountBeforeReminder);
		}

		int reminder = divisionIterator.getReminder();
		add(reminder);
	}

	@Override
	public String toString() {
		return rawRepresentation;
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

	private int getDigitCount(int num) {
		return String.valueOf(num).length();
	}

	private void add(int valueToAdd) {
		rawRepresentation = rawRepresentation + String.format(VALUE_TEMPLATE, valueToAdd);
	}

	private void addSpaces(int spaceCount) {
		rawRepresentation = rawRepresentation + generateStringWithChar(spaceCount, ' ');
	}

	private void addDashesBetween(int term1, int term2) {
		rawRepresentation = rawRepresentation + getDashesBetweenTerms(term1, term2);
	}

	private void addNewLine() {
		rawRepresentation = rawRepresentation + NEW_LINE;
	}

	private void addDelimiter() {
		rawRepresentation = rawRepresentation + DELIMITER;
	}

	private void addUnderscore() {
		rawRepresentation = rawRepresentation + UNDERSCORE;
	}
}
