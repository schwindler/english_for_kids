package uz.greenwhite.lib;

/*
// http://www.java2s.com/Code/Java/Data-Type/Comparetwocharsequences.htm
 */
public class CharSequenceUtil {
    /**
     * Tests if s starts with t, ignoring the case of the characters
     *
     * @param s
     * @param t
     * @return <code>true</code> if s.toLowerCase().equals( t.toLowerCase() ),
     * but more efficiently
     */
    public static boolean startsWithIgnoreCase(CharSequence s, CharSequence t) {
        if (s.length() < t.length()) {
            return false;
        }

        for (int i = 0; i < t.length(); i++) {
            char slc = Character.toLowerCase(s.charAt(i));
            char tlc = Character.toLowerCase(t.charAt(i));
            if (slc != tlc) {
                return false;
            }
        }
        return true;
    }

    /**
     * See {@link String#compareToIgnoreCase(String)}
     *
     * @param s
     * @param t
     * @return See {@link String#compareToIgnoreCase(String)}
     */
    public static int compareToIgnoreCase(CharSequence s, CharSequence t) {
        int i = 0;

        while (i < s.length() && i < t.length()) {
            char a = Character.toLowerCase(s.charAt(i));
            char b = Character.toLowerCase(t.charAt(i));

            int diff = a - b;

            if (diff != 0) {
                return diff;
            }

            i++;
        }

        return s.length() - t.length();
    }

    /**
     * See {@link String#compareTo(String)}
     *
     * @param s
     * @param t
     * @return See {@link String#compareTo(String)}
     */
    public static int compareTo(CharSequence s, CharSequence t) {
        int i = 0;

        while (i < s.length() && i < t.length()) {
            char a = s.charAt(i);
            char b = t.charAt(i);

            int diff = a - b;

            if (diff != 0) {
                return diff;
            }

            i++;
        }

        return s.length() - t.length();
    }

    public static boolean containsIgnoreCase(final CharSequence str, final CharSequence searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        final int len = searchStr.length();
        final int max = str.length() - len;
        for (int i = 0; i <= max; i++) {
            if (regionMatches(str, true, i, searchStr, 0, len)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Green implementation of regionMatches.
     *
     * @param cs         the {@code CharSequence} to be processed
     * @param ignoreCase whether or not to be case insensitive
     * @param thisStart  the index to start on the {@code cs} CharSequence
     * @param substring  the {@code CharSequence} to be looked for
     * @param start      the index to start on the {@code substring} CharSequence
     * @param length     character length of the region
     * @return whether the region matched
     */
    static boolean regionMatches(final CharSequence cs, final boolean ignoreCase, final int thisStart,
                                 final CharSequence substring, final int start, final int length) {
        if (cs instanceof String && substring instanceof String) {
            return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
        } else {
            int index1 = thisStart;
            int index2 = start;
            int tmpLen = length;

            while (tmpLen-- > 0) {
                char c1 = cs.charAt(index1++);
                char c2 = substring.charAt(index2++);

                if (c1 == c2) {
                    continue;
                }

                if (!ignoreCase) {
                    return false;
                }

                // The same check as in String.regionMatches():
                if (Character.toUpperCase(c1) != Character.toUpperCase(c2)
                        && Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
                    return false;
                }
            }

            return true;
        }
    }
}
