package com.foxminded.integer_division;

import java.util.NoSuchElementException;
import java.util.Stack;

public class IntegerDivisionIterator {
	int rootDividend;
	int rootQuotient;
	
	int dividend;
	int divisor;
	int quotient;
	int minuend;
	int reminder;
	
	int spacesBeforeMinuend;
	int spacesBeforeReminder;
	int spacesAfterReminder;
	
	IntegerDivisionIterator next;
	boolean root;

	public IntegerDivisionIterator(int dividend, int divisor) {
		this.rootDividend = dividend;
		this.rootQuotient = dividend / divisor;

		Stack<Integer> stack = getDigitsOfNumber(dividend);

		this.dividend = getNumberFromStack(stack, divisor);
		this.divisor = divisor;
		this.quotient = this.dividend / this.divisor;
		this.reminder = this.dividend % this.divisor;
		this.minuend = this.quotient * this.divisor;
		this.spacesBeforeMinuend = getSpaceCountForTermAccordingToDividend(this.dividend, this.minuend);
		this.spacesBeforeReminder = getSpaceCountForTermAccordingToDividend(this.dividend, this.reminder);

		this.spacesAfterReminder = getSpaceCountForTermAccordingToDividend(this.rootDividend, this.reminder)
				- spacesBeforeReminder;
		this.root = true;

		if (!stack.isEmpty()) {
			this.spacesBeforeReminder--;
			this.next = new IntegerDivisionIterator(this, stack);
		}
	}
	
	private boolean isLastStepOfDivision() {
		return (getDigitCount(rootDividend) + 1 - spacesBeforeReminder
				- getDigitCount(reminder) == 0);
	}

	private IntegerDivisionIterator(IntegerDivisionIterator obj, Stack<Integer> leftDigits) {
		this.rootDividend = obj.rootDividend;
		this.rootQuotient = obj.rootQuotient;
		this.divisor = obj.divisor;

		int temp = obj.reminder;
		while (!leftDigits.isEmpty() && temp < divisor) {
			temp = temp * 10 + leftDigits.pop();
		}

		this.dividend = temp;
		this.quotient = this.dividend / this.divisor;
		this.reminder = this.dividend % this.divisor;
		this.minuend = this.quotient * this.divisor;
		this.spacesBeforeMinuend = obj.spacesBeforeMinuend
				+ getSpaceCountForTermAccordingToDividend(this.dividend, this.minuend);
		this.spacesBeforeReminder = obj.spacesBeforeReminder
				+ getSpaceCountForTermAccordingToDividend(this.dividend, this.reminder);

		this.spacesBeforeReminder = obj.spacesBeforeReminder
				+ getSpaceCountForTermAccordingToDividend(this.dividend, this.reminder);
		this.spacesAfterReminder = getDigitCount(this.rootDividend) - spacesBeforeReminder;
		this.root = false;

		if (getDigitCount(this.rootDividend) + 1 - spacesBeforeReminder - getDigitCount(reminder) > 0) {
			this.spacesBeforeReminder--;
		}

		if (!leftDigits.isEmpty()) {
			this.next = new IntegerDivisionIterator(this, leftDigits);
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

	private int getNumberFromStack(Stack<Integer> digits, int divisor) {
		int temp = 0;
		while (!digits.isEmpty() && temp < divisor) {
			temp = temp * 10 + digits.pop();
		}

		return temp;
	}

	@Override
	public String toString() {
		
		String dividendTemplate;
		String divisorTemplate = String.format("|%d%n", divisor);
		String minuendTemplate = String.format("%d", minuend);
		String quotientTemplate = String.format("|%d%n", this.rootQuotient);
		String reminderTemplate = String.format("%d", reminder);

		String tabsBeforeMinuend = generateStringWithChar(this.spacesBeforeMinuend, ' ');
		String dashesBetweenDivisorAndQuotient = getDashesBetweenTerms(divisor, this.rootQuotient);
		String dashesBetweenMinuendAndReminder = getDashesBetweenTerms(dividend, minuend);
		String tabsAfter = generateStringWithChar(spacesAfterReminder, ' ');
		String tabsBeforeReminder = generateStringWithChar(this.spacesBeforeReminder, ' ');

		String resultTemplate = "";
		
		if (root) {
			dividendTemplate = String.format("_%d", this.rootDividend);
			resultTemplate = dividendTemplate + divisorTemplate + tabsBeforeMinuend + minuendTemplate + tabsAfter
					+ "|" + dashesBetweenDivisorAndQuotient + "\n " + dashesBetweenMinuendAndReminder + tabsAfter + quotientTemplate;
		} else {
			dividendTemplate = String.format("_%d", this.dividend);
			resultTemplate = dividendTemplate + "\n" + tabsBeforeMinuend + minuendTemplate + "\n"
					+ tabsBeforeMinuend + dashesBetweenMinuendAndReminder + "\n";
		}

		if (hasNextDivisionStep()) {
			resultTemplate = resultTemplate + tabsBeforeReminder + nextDivisionStep().toString();
		} else {
			resultTemplate = resultTemplate + tabsBeforeReminder + reminderTemplate;
		}

		return resultTemplate;
	}

	private String generateStringWithChar(int count, char c) {
		StringBuilder builder = new StringBuilder("");

		for (int i = 0; i < count; i++) {
			builder.append(c);
		}

		return builder.toString();
	}

	private String getDashesBetweenTerms(int term1, int term2) {
		int maxBetweenTerms = Math.max(term1, term2);
		int digitCount = getDigitCount(maxBetweenTerms);

		return generateStringWithChar(digitCount, '-');
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

	public boolean hasNextDivisionStep() {
		return this.next != null;
	}

	public IntegerDivisionIterator nextDivisionStep() {
		if (!hasNextDivisionStep()) {
			throw new NoSuchElementException("No more steps of division");
		}
		return this.next;
	}
}
