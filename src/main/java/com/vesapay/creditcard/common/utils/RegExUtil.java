package com.vesapay.creditcard.common.utils;

import java.util.Optional;
import java.util.regex.Pattern;

public class RegExUtil {

    private static final String ALPHA_NUMERIC_WITH_SPECIAL_CHAR_REGEX = "^[a-zA-Z0-9]+[a-zA-Z0-9#& _.@,-]*$";

    private static final Pattern ALPHA_NUMERIC_WITH_SPECIAL_CHAR_REGEX_MATCHER = Pattern.compile(ALPHA_NUMERIC_WITH_SPECIAL_CHAR_REGEX);
    public static boolean isAlphaNumericWithSpecialChar(String value) {
        return isValidRegEx(value, ALPHA_NUMERIC_WITH_SPECIAL_CHAR_REGEX_MATCHER);
    }

    private static final String NUMERIC_REGEX = "^[0-9]+$";
    private static final Pattern NUMERIC_REGEX_MATCHER = Pattern.compile(NUMERIC_REGEX);

    public static boolean isNumeric(String value) {
        return isValidRegEx(value, NUMERIC_REGEX_MATCHER);
    }

    private static boolean isValidRegEx(String value, Pattern regExPattern) {
        return Optional.ofNullable(value)
                .map(v -> regExPattern.matcher(value).matches())
                .orElse(false);
    }
}
