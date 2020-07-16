package com.foxminded.integer_division;

public class IntegerDivision {

	public void divide(int dividend, int divisor) {

		int quotient = dividend / divisor;
		int reminder = dividend % divisor;
		int minuend = divisor * quotient;

		System.out.print(String.format(getTemplate(dividend, minuend), dividend, divisor, divisor * quotient, quotient,
				reminder));
	}

	private String getTemplate(int dividend, int minuend) {
		String headTemplate = "_%d|%d\n";
		String minuendTemplate = "%d|-\n";
		String quotientTemplate = "|%d\n";
		String reminderTemplate = "%d";

		String resultTemplate = "";

		if (dividend < 10 && minuend < 10) {
			resultTemplate = headTemplate + " " + minuendTemplate + " -" + quotientTemplate + " " + reminderTemplate;
		} else if (dividend < 10 && minuend >= 10) {
			resultTemplate = headTemplate + " " + minuendTemplate + " --" + quotientTemplate + "  " + reminderTemplate;
		} else if (dividend >= 10 && minuend < 10) {
			resultTemplate = headTemplate + "  " + minuendTemplate + " --" + quotientTemplate + "  " + reminderTemplate;
		} else if (dividend >= 10 && minuend >= 10) {
			resultTemplate = headTemplate + " " + minuendTemplate + " --" + quotientTemplate + "  " + reminderTemplate;
		}

		return resultTemplate;
	}
}
