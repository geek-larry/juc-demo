package com.jade.demo.safe;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

/**
 * @description:
 * @date: 2022/10/16
 **/
public class CollectionTest {

    public static void main(String[] args) {
//        List<String> data = new ArrayList<>();
//        List<String> data = new Vector<>();
//        List<String> data = Collections.synchronizedList(new ArrayList<>());
        List<String> data = new CopyOnWriteArrayList<>();
        IntStream.range(0, 30).forEach(
                i -> new Thread(() -> data.add(String.valueOf(i)), String.valueOf(i)).start()
        );
        Map<String, String> data1 = new ConcurrentHashMap<>();
        IntStream.range(0, 30).forEach(
                i -> new Thread(() -> data1.put(String.valueOf(i), String.valueOf(i)), String.valueOf(i)).start()
        );
        System.out.println(data);
        System.out.println(data1);
    }
}
