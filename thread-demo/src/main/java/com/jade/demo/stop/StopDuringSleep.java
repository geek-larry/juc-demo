package com.jade.demo.stop;

/**
 * @description:
 * @date: 2022/10/11
 **/
public class StopDuringSleep {

    public static void main(String[] args) throws InterruptedException {

        Runnable runnable = () -> {

            int num = 0;

            try {

                while (!Thread.currentThread().isInterrupted() && num <= 1000) {

                    System.out.println(num);

                    num++;

                    Thread.sleep(1000000);

                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();

            }

        };

        Thread thread = new Thread(runnable);

        thread.start();

        Thread.sleep(60);

        thread.interrupt();

    }

}
