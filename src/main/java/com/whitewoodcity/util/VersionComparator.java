package com.whitewoodcity.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VersionComparator implements Comparator<String> {

    public int compare(String version1, String version2) {
        // Split version into parts
        String parts1[] = getVersionParts(version1),
                parts2[] = getVersionParts(version2);

        // Go through common prefix left to right, first part which is higher indicates
        // higher version (4.2.1 > 4.2.0 > 3.9.9)
        for (int i = 0 ; i < Math.min(parts1.length, parts2.length); i++) {
            int partComparison = compareVersionPart(parts1[i], parts2[i]);
            if (partComparison != 0){
                return partComparison;
            }
        }

        // Common prefix is the same; longer value means higher version
        // (3.2.1 > 3.2)
        if (parts1.length > parts2.length) {
            return 1;
        } else if (parts1.length < parts2.length) {
            return -1;
        } else {
            return 0;
        }

    }

    protected String[] getVersionParts(String version) {
        return version.split("\\.");
    }

    protected int compareVersionPart(String part1, String part2) {
        int versionPart1 = Integer.parseInt(part1),
                versionPart2 = Integer.parseInt(part2);

        if (versionPart1 > versionPart2) {
            return 1;
        } else if (versionPart1 < versionPart2) {
            return -1;
        } else {
            return 0;
        }
    }

    public static void main(String[] args){
        VersionComparator comparator = new VersionComparator();
        List<String> list = new ArrayList();
        list.add("4.3.2");
        list.add("12.3.2");
        list.add("4.3.");
        list.add("4.0");
        Collections.sort(list, comparator);
        System.out.println(list);
    }

}