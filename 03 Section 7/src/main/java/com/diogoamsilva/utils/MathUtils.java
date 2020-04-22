package com.diogoamsilva.utils;

public class MathUtils {
	
	public static Double convertToDouble(String number) {
		if(number == null) return 0D;
		number = number.replaceAll(",", ".");
		if(isNumeric(number)) {
			return Double.parseDouble(number);
		}
		return 0D;
	}

	public static boolean isNumeric(String number) {
		if(number == null) return false;
		number = number.replaceAll(",", ".");
		return number.matches("[-+]?[0-9]*\\.?[0-9]+");
	}
}
