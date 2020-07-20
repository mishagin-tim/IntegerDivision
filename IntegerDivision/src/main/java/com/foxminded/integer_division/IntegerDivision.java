package com.foxminded.integer_division;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

class IntegerDivision implements Iterator<IntegerDivision> {
	int rootDivident;
	int rootQuotient;
	int dividend;
	int divisor;
	int quotient;
	int minuend;
	int reminder;
	int spacesBeforeMinuend;
	int spacesBeforeReminder;
	int spacesAfterReminder;
	IntegerDivision next = null;
	boolean root;

	public IntegerDivision(int dividend, int divisor) {
		this.rootDivident = dividend;
		this.rootQuotient = dividend / divisor;

		Stack<Integer> stack = getDigitsOfNumber(dividend);

		this.dividend = getNumberFromStack(stack, divisor);
		this.divisor = divisor;
		this.quotient = this.dividend / this.divisor;
		this.reminder = this.dividend % this.divisor;
		this.minuend = this.quotient * this.divisor;
		this.spacesBeforeMinuend = getSpaceCountForTermAccordingToDividend(this.dividend, this.minuend);
		this.spacesBeforeReminder = getSpaceCountForTermAccordingToDividend(this.dividend, this.reminder);
		this.spacesAfterReminder = getSpaceCountForTermAccordingToDividend(this.rootDivident, this.reminder)
				- spacesBeforeReminder;
		this.root = true;

		if (!stack.isEmpty()) {
			this.next = new IntegerDivision(this, stack);
		}
	}

	private IntegerDivision(IntegerDivision obj, Stack<Integer> leftDigits) {
		this.rootDivident = obj.rootDivident;
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
				+ getSpaceCountForTermAccordingToDividend(this.dividend, this.minuend);
		this.spacesAfterReminder = getDigitCount(this.rootDivident) - spacesBeforeReminder;
		this.root = false;

		if (!leftDigits.isEmpty()) {
			this.next = new IntegerDivision(this, leftDigits);
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

		String resultTemplate = "";
		
		if (root) {
			dividendTemplate = String.format("_%d", this.rootDivident);
			resultTemplate = dividendTemplate + divisorTemplate + tabsBeforeMinuend + minuendTemplate + tabsAfter
					+ "|" + dashesBetweenDivisorAndQuotient + "\n " + dashesBetweenMinuendAndReminder + tabsAfter + quotientTemplate;
		} else {
			dividendTemplate = String.format("_%d", this.dividend);
			resultTemplate = dividendTemplate + "\n" + tabsBeforeMinuend + minuendTemplate + "\n"
					+ tabsBeforeMinuend + dashesBetweenMinuendAndReminder + "\n";
		}

		if (hasNext()) {
			String tabsBeforeReminder = generateStringWithChar(this.spacesBeforeReminder - 1, ' ');
			resultTemplate = resultTemplate + tabsBeforeReminder + next().toString();
		} else {
			String tabsBeforeReminder = generateStringWithChar(this.spacesBeforeReminder, ' ');
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

	@Override
	public boolean hasNext() {
		return this.next != null;
	}

	@Override
	public IntegerDivision next() {
		if (!hasNext()) {
			throw new NoSuchElementException("No more steps of division");
		}
		return this.next;
	}
}
