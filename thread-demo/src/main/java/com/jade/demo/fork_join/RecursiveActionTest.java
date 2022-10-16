package com.jade.demo.fork_join;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @description:
 * @date: 2022/10/16
 **/
public class RecursiveActionTest {
    // 定义最小区间为10
    private final static int MAX_THRESHOLD = 10;
    private final static AtomicInteger SUM = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        final ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit(new CalculateRecursiveAction(0, 100));
        forkJoinPool.awaitTermination(2, TimeUnit.SECONDS);
        System.out.println(SUM);
    }

    private static class CalculateRecursiveAction extends RecursiveAction {
        // 起始
        private final int start;
        // 结束
        private final int end;

        private CalculateRecursiveAction(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            // 如果起始和结束范围小于我们定义的区间范围，则直接计算
            if ((end - start) <= MAX_THRESHOLD) {
                SUM.addAndGet(IntStream.rangeClosed(start, end).sum());
            } else {
                // 否则，将范围一分为二，分成两个子任务
                int middle = (end + start) / 2;
                CalculateRecursiveAction leftAction = new CalculateRecursiveAction(start, middle);
                CalculateRecursiveAction rightAction = new CalculateRecursiveAction(middle + 1, end);
                // 执行子任务
                invokeAll(leftAction, rightAction);
                // 没有汇总子任务结果过程，因为没有返回值。
            }
        }
    }
}
