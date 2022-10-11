package com.jade.demo.thread_error;

/**
 * @description:
 * @date: 2022/10/11
 **/
public class ThreadNotSafe1 {

    static int i;

    public static void main(String[] args) throws InterruptedException {

        Runnable r = () -> {

            for (int j = 0; j < 10000; j++) {

                i++;

            }

        };

        Thread thread1 = new Thread(r);

        Thread thread2 = new Thread(r);

        thread1.start();

        thread2.start();

        thread1.join();

        thread2.join();

        System.out.println(i);

    }

}
