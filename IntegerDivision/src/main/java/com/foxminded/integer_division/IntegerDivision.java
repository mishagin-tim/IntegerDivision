package com.foxminded.integer_division;

public class IntegerDivision {

	public void divide(int dividend, int divisor) {

		int quotient = dividend / divisor;
		int reminder = dividend % divisor;
		int minuend = divisor * quotient;
		
		String template = getTemplate(dividend, divisor, minuend, quotient);

		System.out.print(String.format(template, dividend, divisor, divisor * quotient, quotient,
				reminder));
	}

	private String getTemplate(int dividend, int divisor, int minuend, int quotient) {
		String headTemplate = "_%d|%d\n";
		String minuendTemplate = "%d";
		String quotientTemplate = "|%d\n";
		String reminderTemplate = "%d";

		String resultTemplate = "";

		int maxBetweenDividenAndMinuend = Math.max(dividend, minuend);
		String templateMaxDividendMinuend = "";

		if (maxBetweenDividenAndMinuend < 10) {
			templateMaxDividendMinuend = "\n -" + quotientTemplate + " " + reminderTemplate;
		} else if (maxBetweenDividenAndMinuend < 100) {
			templateMaxDividendMinuend = "\n --" + quotientTemplate + "  " + reminderTemplate;
		} else if (maxBetweenDividenAndMinuend < 1000) {
			templateMaxDividendMinuend = "\n ---" + quotientTemplate + "   " + reminderTemplate;
		}
		
		int maxBetweenDivisorAndQuotient = Math.max(divisor, quotient);
		String templateMaxDivisorQuotient = "";
		
		if (maxBetweenDivisorAndQuotient < 10) {
			templateMaxDivisorQuotient = "|-";
		} else if (maxBetweenDivisorAndQuotient < 100) {
			templateMaxDivisorQuotient = "|--";
		} else if (maxBetweenDivisorAndQuotient < 1000) {
			templateMaxDivisorQuotient = "|---";
		}

		int tabsCount = getDigitCount(dividend) + 1 - getDigitCount(minuend);
		String tabs = generateStringWithSpaces(tabsCount);

		resultTemplate = headTemplate + tabs + minuendTemplate + templateMaxDivisorQuotient
				+ templateMaxDividendMinuend;

		return resultTemplate;
	}

	private int getDigitCount(int num) {
		int digitCount = 0;

		do {
			digitCount++;
			num /= 10;
		} while (num > 0);

		return digitCount;
	}

	private String generateStringWithSpaces(int count) {
		String result = "";

		for (int i = 0; i < count; i++) {
			result += " ";
		}

		return result;
	}
}
