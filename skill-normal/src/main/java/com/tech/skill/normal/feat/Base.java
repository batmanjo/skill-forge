package com.tech.skill.normal.feat;

/**
 * @author yanmiao.wu
 * @create 2023-12-06 11:06
 */
public class Base {
    public static void main(String[] args) {
        Object obj = "hello";

        //jdk 8
        if(obj instanceof String){
            System.out.println(obj);
            System.out.println(((String) obj).length());
        }
        //jdk 17
        if(obj instanceof String t){
            System.out.println(t);
            System.out.println(t.length());
        }
    }

}
