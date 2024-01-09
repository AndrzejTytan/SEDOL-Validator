package pl.danwys;

import java.util.Map;

import static java.util.Map.entry;

import java.util.regex.*;

public class SedolValidator {
    private static final Pattern SEDOL_HEAD_REGEX = Pattern.compile("^[^AEIOU]{6}");
    private static final Pattern SEDOL_TAIL_REGEX = Pattern.compile("^[0-9]");
    private static final int SEDOL_LEGAL_LENGTH = 7;
    private static final int[] POSITION_WEIGHTINGS = {1, 3, 1, 7, 3, 9, 1};
    private static final Map<Character, Integer> LETTER_VALUES = Map.ofEntries(
            entry('B', 11),
            entry('C', 12),
            entry('D', 13),
            entry('F', 15),
            entry('G', 16),
            entry('H', 17),
            entry('J', 19),
            entry('K', 20),
            entry('L', 21),
            entry('M', 22),
            entry('N', 23),
            entry('P', 25),
            entry('Q', 26),
            entry('R', 27),
            entry('S', 28),
            entry('T', 29),
            entry('V', 31),
            entry('W', 32),
            entry('X', 33),
            entry('Y', 34),
            entry('Z', 35)
    );

    private SedolValidator() {
    }

    public static boolean validate(String sedol) {
        String sedolHead = sedol.substring(0, 6);
        String sedolTail = sedol.substring(6, 7);
        return validateLength(sedol) && //short circuit - returns false immediately if first expression is false
                validateHead(sedolHead) &&
                validateTail(sedolTail) &&
                calculateWeightedSum(sedol) % 10 == 0;
    }

    private static boolean validateLength(String sedol) {
        return sedol.length() == SEDOL_LEGAL_LENGTH;
    }

    private static boolean validateHead(String sedolHead) {
        return SEDOL_HEAD_REGEX.matcher(sedolHead).find();
    }

    private static boolean validateTail(String sedolTail) {
        return SEDOL_TAIL_REGEX.matcher(sedolTail).find();
    }

    private static int calculateWeightedSum(String sedol) {
        char[] sedolCharArr = sedol.toCharArray();
        int sum = 0;
        for (int i = 0; i < sedolCharArr.length; i++) {
            char c = sedolCharArr[i];
            int weight = POSITION_WEIGHTINGS[i];
            if (Character.isDigit(c)) {
                sum += Integer.parseInt(String.valueOf(c)) * weight;
            } else if (Character.isLetter(c)) {
                sum += LETTER_VALUES.get(c) * weight;
            } else {
                throw new RuntimeException("Illegal character in sedol.");
            }
        }
        return sum;
    }
}
