package com.whitewoodcity;

import java.util.ArrayList;
import java.util.List;

public class Test {


    public static void main(String[] args) {
        List<String> list=new ArrayList<>();
        list.add("item1");
        list.add("item2");
        list.add("item3");
        list.add("item4");
        list.add("item5");
        list.stream().filter(str->!str.equals("item3"))
                .forEach(s -> {
                    System.out.println(s);
                });

        list.forEach(System.out::println);
    }
}
