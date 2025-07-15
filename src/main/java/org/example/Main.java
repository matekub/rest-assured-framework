package org.example;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        map.putIfAbsent("key4", "value4");
        map.remove("key2", "value4");
        map.replace("key1", "value11");
        map.replace("key2", "value32", "value22");
        System.out.println("====");
        for(Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println("====");
        for(String key : map.keySet()) {
            System.out.println(key + " : " + map.get(key));
        }
        System.out.println("====");
        for(String value : map.values()) {
            System.out.println(value);
        }
    }
}