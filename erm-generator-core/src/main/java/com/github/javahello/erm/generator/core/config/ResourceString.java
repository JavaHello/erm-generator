package com.github.javahello.erm.generator.core.config;

import java.text.MessageFormat;
import java.util.*;

public class ResourceString {

	public static final String KEY_DEFAULT_VALUE_EMPTY_STRING = "label.empty.string";

	public static final String KEY_DEFAULT_VALUE_CURRENT_DATE_TIME = "label.current.date.time";

	private static final ResourceBundle resource = ResourceBundle
			.getBundle("com.github.javahello.erm.ERMGenerator");

	private static final ResourceBundle resourceEn = ResourceBundle.getBundle(
			"com.github.javahello.erm.ERMGenerator", Locale.ROOT);

	private static final ResourceBundle resourceZh = ResourceBundle.getBundle(
			"com.github.javahello.erm.ERMGenerator", Locale.CHINA);

	public static String getResourceString(String key) {
		return getResourceString(key, null);
	}

	public static String normalize(String key, String value) {
		if (equals(key, value)) {
			return getResourceString(key);
		}

		return value;
	}

	public static boolean equals(String key, String value) {
		if (value == null) {
			return false;
		}

		if (value.equals(resourceEn.getString(key))) {
			return true;
		} else if (value.equals(resourceZh.getString(key))) {
			return true;
		}

		return false;
	}

	public static String getResourceString(String key, String[] args) {
		try {
			String string = resource.getString(key);
			string = MessageFormat.format(string, args);
			// string = string.replaceAll("\\\\r\\\\n", "\r\n");

			return string;
		} catch (MissingResourceException e) {
			return key;
		}
	}

	public static Map<String, String> getResources(String prefix) {
		Map<String, String> props = new TreeMap<String, String>(
				Collections.reverseOrder());
		Enumeration<String> keys = resource.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			if (key.startsWith(prefix)) {
				props.put(key, resource.getString(key));
			}
		}
		return props;
	}
}
