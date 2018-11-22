package com.snap.kapacitor;

public class KapacitorUtil {
	
	public static String toSign(String operator) {
		String sign = operator;
		if (operator.equals("less than")) {
			sign = "<";
		} else if (operator.equals("greater than")) {
			sign = ">";
		} else if (operator.equals("equals")) {
			sign = "=";
		} else if (operator.equals("not equals")) {
			sign = "!=";
		}
		System.out.println("Sign " + operator + " " + sign);
		return sign;
	}
}
