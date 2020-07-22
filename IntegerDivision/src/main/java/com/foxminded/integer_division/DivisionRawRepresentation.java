package com.foxminded.integer_division;

public class DivisionRawRepresentation {
	private static final String VALUE_TEMPLATE = "%d";
	private static final String NEW_LINE = "\n";
	private static final String DELIMITER = "|";
	private static final String UNDERSCORE = "_";

	private String rawRepresentation = "";

	public DivisionRawRepresentation(String rawRepresentation) {
		this.rawRepresentation = rawRepresentation;
	}

	DivisionRawRepresentation(boolean isRoot, boolean hasNextDivisionStep, int rootDividend, int rootDivisor,
			int dividend,
			int spaceCountBeforeReminder) {

		int rootQuotient = rootDividend / rootDivisor;
		int quotient = dividend / rootDivisor;
		int minuend = quotient * rootDivisor;
		int reminder = dividend % rootDivisor;

		int spaceCountBeforeMinuend = spaceCountBeforeReminder
				+ getSpaceCountForTermAccordingToDividend(dividend, minuend);
		int spaceCountBeforeDashes = spaceCountBeforeReminder
				+ getSpaceCountForTermAccordingToDividend(dividend, dividend);
		spaceCountBeforeReminder = spaceCountBeforeReminder
				+ getSpaceCountForTermAccordingToDividend(dividend, dividend % rootDivisor);

		int spaceCountAfterReminder = getSpaceCountForTermAccordingToDividend(rootDividend, dividend % rootDivisor)
				- spaceCountBeforeReminder;

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

		if (hasNextDivisionStep) {
			spaceCountBeforeReminder--;
			addSpaces(spaceCountBeforeReminder);
		} else {
			addSpaces(spaceCountBeforeReminder);
			add(reminder);
		}
	}

	public DivisionRawRepresentation concat(DivisionRawRepresentation divisionRawRepresentation) {
		return new DivisionRawRepresentation(rawRepresentation + divisionRawRepresentation.toString());
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

	private int getSpaceCountForTermAccordingToDividend(int dividend, int term) {
		return getDigitCount(dividend) + 1 - getDigitCount(term);
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
