package com.jade.demo.juc;

import java.util.Collection;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @description:
 * @date: 2022/10/16
 **/
public class SemaphoreTest1 {

    public static void main(String[] args) {
        // 定义许可证数量
        final MySemaphore semaphore = new MySemaphore(1);

        IntStream.range(0, 4).forEach(i -> {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "开始");
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "获取许可证");
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(Thread.currentThread().getName() + "释放许可证");
                    semaphore.release();
                }
                System.out.println(Thread.currentThread().getName() + "结束");
            }, "thread" + (i + 1)).start();
        });

        while (true) {
            if (semaphore.hasQueuedThreads()) {
                System.out.println("等待线程数量：" + semaphore.getQueueLength());
                Collection<Thread> queuedThreads = semaphore.getQueuedThreads();
                System.out.println("等待线程：" + queuedThreads.stream().map(Thread::getName).collect(Collectors.joining(",")));
            }
        }
    }

    static class MySemaphore extends Semaphore {

        private static final long serialVersionUID = -2595494765642942297L;

        public MySemaphore(int permits) {
            super(permits);
        }

        public MySemaphore(int permits, boolean fair) {
            super(permits, fair);
        }

        public Collection<Thread> getQueuedThreads() {
            return super.getQueuedThreads();
        }
    }
}
