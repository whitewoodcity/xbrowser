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
}
