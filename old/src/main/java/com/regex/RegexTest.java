package com.regex;

public class RegexTest {

    public static void main(String[] args) {

        String str = "aaa\"bbb\"ccc\"ddd\"eee";

        System.out.println("��ʱĬ��Ϊ̰��ƥ��ģʽ, ������ str==> aaa@eee");
        str = str.replaceAll("\"(.*)\"", "@");
        System.out.println(str);

        String str1 = "aaa\"bbb\"ccc\"ddd\"eee";
        System.out.println("��ʱΪ��ǿƥ��ģʽ, ������ str==> aaa@ccc@eee");
        str = str1.replaceAll("\"(.*?)\"", "@");
        System.out.println(str);

    }

}
