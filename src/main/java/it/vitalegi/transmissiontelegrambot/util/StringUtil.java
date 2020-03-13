package it.vitalegi.transmissiontelegrambot.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StringUtil {

	public static boolean isNotNull(String value) {
		return !isNullOrEmpty(value);
	}

	public static boolean isNullOrEmpty(String value) {
		return value == null || value.equals("");
	}

	public static List<String> tokenize(String text) {
		List<String> tokenized = new ArrayList<>();
		try (Scanner scan = new Scanner(text)) {
			scan.forEachRemaining(tokenized::add);
		}
		return tokenized;
	}
}
