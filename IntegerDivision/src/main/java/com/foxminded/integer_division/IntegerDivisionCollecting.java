package com.foxminded.integer_division;

import java.util.NoSuchElementException;
import java.util.Stack;

public class IntegerDivisionCollecting {

	public DivisionBuffer divide(int dividend, int divisor) {
		DivisionBuffer divisionBuffer = new DivisionBuffer(dividend, divisor);

		while (divisionBuffer.hasNextDivisionStep()) {
			proceedDivision(divisionBuffer);
		}

		return divisionBuffer;
	}

	private void proceedDivision(DivisionBuffer divisionBuffer) {
		divisionBuffer.nextDivisionStep();

		int dividend = divisionBuffer.getDividend();
		int divisor = divisionBuffer.getDivisor();
		divisionBuffer.setQuotient(dividend / divisor);
		divisionBuffer.setReminder(dividend % divisor);

		int quotient = divisionBuffer.getQuotient();
		divisionBuffer.setMinuend(quotient * divisor);
		
		boolean root = divisionBuffer.isRoot();
		int minuend = divisionBuffer.getMinuend();
		int reminder = divisionBuffer.getReminder();
		int spaceCountBeforeReminder = 0;

		if (root) {
			divisionBuffer.setSpacesBeforeMinuend(getSpaceCountForTermAccordingToDividend(dividend, minuend));

			divisionBuffer.setSpacesBeforeReminder(getSpaceCountForTermAccordingToDividend(dividend, reminder));

			spaceCountBeforeReminder = divisionBuffer.getSpacesBeforeReminder();

			int rootDividend = divisionBuffer.getRootDividend();
			divisionBuffer.setSpacesAfterReminder(
					getSpaceCountForTermAccordingToDividend(rootDividend, reminder) - spaceCountBeforeReminder);
		
		} else {
			int spaceCountBeforeMinuendAfterPreviousDivision = divisionBuffer.getSpacesBeforeMinuend();
			divisionBuffer.setSpacesBeforeMinuend(spaceCountBeforeMinuendAfterPreviousDivision
					+ getSpaceCountForTermAccordingToDividend(dividend, minuend));

			int spaceCountBeforeReminderAfterPreviousDivision = divisionBuffer.getSpacesBeforeReminder();
			divisionBuffer.setSpacesBeforeReminder(spaceCountBeforeReminderAfterPreviousDivision
					+ getSpaceCountForTermAccordingToDividend(dividend, reminder));
			spaceCountBeforeReminder = divisionBuffer.getSpacesBeforeReminder();
		}
		
		if (!isLastStepOfDivision(divisionBuffer)) {
			spaceCountBeforeReminder--;
			divisionBuffer.setSpacesBeforeReminder(spaceCountBeforeReminder);
		}
		
		divisionBuffer.setRawRepresentation(getRawRepresentation(divisionBuffer));
		divisionBuffer.setRoot(false);
	}
	
	private int getSpaceCountForTermAccordingToDividend(int dividend, int term) {
		return getDigitCount(dividend) + 1 - getDigitCount(term);
	}

	private int getDigitCount(int num) {
		int digitCount = 0;

		do {
			digitCount++;
			num /= 10;
		} while (num > 0);

		return digitCount;
	}


	private boolean isLastStepOfDivision(DivisionBuffer divisionBuffer) {
		int rootDividend = divisionBuffer.getRootDividend();
		int spaceCountBeforeReminder = divisionBuffer.getSpacesBeforeReminder();
		int reminder = divisionBuffer.getReminder();
		
		return (getDigitCount(rootDividend) + 1 - spaceCountBeforeReminder
				- getDigitCount(reminder) == 0);
	}

	private String getRawRepresentation(DivisionBuffer divisionBuffer) {
		int dividend = divisionBuffer.getDividend();
		int divisor = divisionBuffer.getDivisor();
		int minuend = divisionBuffer.getMinuend();
		int reminder = divisionBuffer.getReminder();
		int rootQuotient = divisionBuffer.getRootQuotient();
		
		String dividendTemplate;
		String divisorTemplate = String.format("|%d%n", divisor);
		String minuendTemplate = String.format("%d", minuend);
		String quotientTemplate = String.format("|%d%n", rootQuotient);
		String reminderTemplate = String.format("%d", reminder);
		
		int spaceCountBeforeMinuend = divisionBuffer.getSpacesBeforeMinuend();
		String tabsBeforeMinuend = generateStringWithChar(spaceCountBeforeMinuend, ' ');
		
		String dashesBetweenDivisorAndQuotient = getDashesBetweenTerms(divisor, rootQuotient);
		
		String dashesBetweenMinuendAndReminder = getDashesBetweenTerms(dividend, minuend);
		
		int spaceCountAfterReminder = divisionBuffer.getSpacesAfterReminder();
		String tabsAfter = generateStringWithChar(spaceCountAfterReminder, ' ');
		
		int spaceCountBeforeReminder = divisionBuffer.getSpacesBeforeReminder();
		String tabsBeforeReminder = generateStringWithChar(spaceCountBeforeReminder, ' ');
		
		boolean root = divisionBuffer.isRoot();
		int rootDividend = divisionBuffer.getRootDividend();
		String resultTemplate = divisionBuffer.toString();
		
		if (root) {
			dividendTemplate = String.format("_%d", rootDividend);
			resultTemplate = resultTemplate + dividendTemplate + divisorTemplate + tabsBeforeMinuend + minuendTemplate
					+ tabsAfter + "|"
					+ dashesBetweenDivisorAndQuotient + "\n " + dashesBetweenMinuendAndReminder + tabsAfter
					+ quotientTemplate + tabsBeforeReminder;
		} else {
			dividendTemplate = String.format("_%d", dividend);
			resultTemplate = resultTemplate + dividendTemplate + "\n" + tabsBeforeMinuend + minuendTemplate + "\n"
					+ tabsBeforeMinuend
					+ dashesBetweenMinuendAndReminder + "\n" + tabsBeforeReminder;
		}
		
		if (isLastStepOfDivision(divisionBuffer)) {
			resultTemplate = resultTemplate + reminderTemplate;
		}

		return resultTemplate;
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

class DivisionBuffer {
	private Stack<Integer> digitsOfRootDividend;

	private int rootDividend;
	private int rootQuotient;

	private int dividend;
	private int divisor;
	private int quotient;
	private int minuend;
	private int reminder;
	
	private int spacesBeforeMinuend;
	private int spacesBeforeReminder;
	private int spacesAfterReminder;
	
	private boolean root;
	
	private String rawRepresentation;

	public DivisionBuffer(int dividend, int divisor) {
		this.root = true;
		this.rootDividend = dividend;
		this.rootQuotient = dividend / divisor;
		this.divisor = divisor;
		this.rawRepresentation = "";
		this.digitsOfRootDividend = getDigitsOfNumber(this.rootDividend);
	}

	public boolean hasNextDivisionStep() {
		return !digitsOfRootDividend.isEmpty();
	}

	public void nextDivisionStep() {
		if (!hasNextDivisionStep()) {
			throw new NoSuchElementException("No more division steps");
		}

		this.dividend = getNumberFromStack();
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

		while (!digitsOfRootDividend.isEmpty() && temp < this.divisor) {
			temp = temp * 10 + digitsOfRootDividend.pop();
		}

		return temp;
	}

	@Override
	public String toString() {
		return rawRepresentation;
	}

	int getRootDividend() {
		return rootDividend;
	}

	void setRootDividend(int rootDividend) {
		this.rootDividend = rootDividend;
	}

	int getRootQuotient() {
		return rootQuotient;
	}

	void setRootQuotient(int rootQuotient) {
		this.rootQuotient = rootQuotient;
	}

	int getDividend() {
		return dividend;
	}

	void setDividend(int dividend) {
		this.dividend = dividend;
	}

	int getDivisor() {
		return divisor;
	}

	void setDivisor(int divisor) {
		this.divisor = divisor;
	}

	int getQuotient() {
		return quotient;
	}

	void setQuotient(int quotient) {
		this.quotient = quotient;
	}

	int getMinuend() {
		return minuend;
	}

	void setMinuend(int minuend) {
		this.minuend = minuend;
	}

	int getReminder() {
		return reminder;
	}

	void setReminder(int reminder) {
		this.reminder = reminder;
	}

	int getSpacesBeforeMinuend() {
		return spacesBeforeMinuend;
	}

	void setSpacesBeforeMinuend(int spacesBeforeMinuend) {
		this.spacesBeforeMinuend = spacesBeforeMinuend;
	}

	int getSpacesBeforeReminder() {
		return spacesBeforeReminder;
	}

	void setSpacesBeforeReminder(int spacesBeforeReminder) {
		this.spacesBeforeReminder = spacesBeforeReminder;
	}

	int getSpacesAfterReminder() {
		return spacesAfterReminder;
	}

	void setSpacesAfterReminder(int spacesAfterReminder) {
		this.spacesAfterReminder = spacesAfterReminder;
	}

	boolean isRoot() {
		return root;
	}

	void setRoot(boolean root) {
		this.root = root;
	}

	String getRawRepresentation() {
		return rawRepresentation;
	}

	void setRawRepresentation(String rawRepresentation) {
		this.rawRepresentation = rawRepresentation;
	}
}