package com.foxminded.integer_division;

public class IntegerDivision {

	public void divide(int dividend, int divisor) {

		DivisionTermsParameterObject termsPO = new DivisionTermsParameterObject();

		termsPO.dividend = dividend;
		termsPO.divisor = divisor;
		termsPO.quotient = dividend / divisor;
		termsPO.minuend = divisor * termsPO.quotient;
		termsPO.reminder = dividend % divisor;

		System.out.print(getFormatedString(termsPO));
	}

	private String getFormatedString(DivisionTermsParameterObject termsPO) {
		return String.format(getTemplate(termsPO), termsPO.dividend, termsPO.divisor, termsPO.minuend, termsPO.quotient,
				termsPO.reminder);
	}

	private String getTemplate(DivisionTermsParameterObject termsPO) {
		String headTemplate = "_%d|%d\n";
		String minuendTemplate = "%d|";
		String quotientTemplate = "|%d\n";
		String reminderTemplate = "%d";

		String tabsBeforeMinuend = getTabsForTerm(termsPO.dividend, termsPO.minuend);
		String lineBetweenDivisorAndQuotient = getDashesAccordingToTerms(termsPO.divisor, termsPO.quotient);
		String dashesBeforeMinuend = getDashesAccordingToTerms(termsPO.dividend, termsPO.minuend);
		String tabsBeforeReminder = getTabsForTerm(termsPO.dividend, termsPO.reminder);

		return headTemplate + tabsBeforeMinuend + minuendTemplate + lineBetweenDivisorAndQuotient + "\n "
				+ dashesBeforeMinuend + quotientTemplate + tabsBeforeReminder + reminderTemplate;
	}

	private int getDigitCount(int num) {
		int digitCount = 0;

		do {
			digitCount++;
			num /= 10;
		} while (num > 0);

		return digitCount;
	}

	private String generateStringWithChar(int count, char c) {
		StringBuilder builder = new StringBuilder("");

		for (int i = 0; i < count; i++) {
			builder.append(c);
		}

		return builder.toString();
	}

	private String getTabsForTerm(int dividend, int term) {
		int spaceCount = getDigitCount(dividend) + 1 - getDigitCount(term);

		return generateStringWithChar(spaceCount, ' ');
	}

	private String getDashesAccordingToTerms(int term1, int term2) {
		int maxBetweenTerms = Math.max(term1, term2);
		int digitCount = getDigitCount(maxBetweenTerms);

		return generateStringWithChar(digitCount, '-');
	}
}

class DivisionTermsParameterObject {
	int dividend;
	int divisor;
	int minuend;
	int quotient;
	int reminder;
}
