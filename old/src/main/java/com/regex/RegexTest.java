package com.regex;

public class RegexTest {

    public static void main(String[] args) {

        String str = "aaa\"bbb\"ccc\"ddd\"eee";

        System.out.println("此时默认为贪婪匹配模式, 输出结果 str==> aaa@eee");
        str = str.replaceAll("\"(.*)\"", "@");
        System.out.println(str);

        String str1 = "aaa\"bbb\"ccc\"ddd\"eee";
        System.out.println("此时为勉强匹配模式, 输出结果 str==> aaa@ccc@eee");
        str = str1.replaceAll("\"(.*?)\"", "@");
        System.out.println(str);

    }

}
