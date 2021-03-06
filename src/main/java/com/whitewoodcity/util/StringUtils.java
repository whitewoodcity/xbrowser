package com.whitewoodcity.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String join(Collection<String> l, String separator) {
        StringBuffer sb = new StringBuffer();

        for (Iterator it = l.iterator(); it.hasNext(); sb.append((String) it.next())) {
            if (sb.length() != 0) {
                sb.append(separator);
            }
        }

        return sb.toString();
    }

    public static String replace(String originalString, Map<String, String> tokens) {

        String patternString = "(" + join(tokens.keySet(), "|") + ")";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(originalString);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, tokens.get(matcher.group(1)));
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    private static final Pattern charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*\"?([^\\s;\"]*)");

    /**
     * Parse out a charset from a content type header.
     *
     * @param contentType
     *            e.g. "text/html; charset=EUC-JP"
     * @return "EUC-JP", or null if not found. Charset is trimmed and
     *         uppercased.
     */
    public static String getCharsetFromContentType(String contentType) {
        if (contentType == null)
            return null;

        Matcher m = charsetPattern.matcher(contentType);
        if (m.find()) {
            return m.group(1).trim().toUpperCase();
        }
        return null;
    }
}
