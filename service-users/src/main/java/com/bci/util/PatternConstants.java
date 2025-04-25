package com.bci.util;

import java.util.regex.Pattern;

public class PatternConstants {
	public static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$");
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=(.*[A-Z]))(?=(.*\\d.*\\d))(?=.*[a-z]).{8,12}$");
}
