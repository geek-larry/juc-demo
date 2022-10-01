package com.jade.spinlock.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class OrderService {

    @SneakyThrows
    public List<String> call(List<String> l) {
        CountDownLatch latch = new CountDownLatch(l.size());
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(1000);
        ExecutorService executor = new ThreadPoolExecutor(5, 5, 10L, TimeUnit.SECONDS, queue, new ThreadPoolExecutor.CallerRunsPolicy());
        List<Future<String>> list = new ArrayList<>();
        List<String> res = new ArrayList<>();
        for (int i = 0; i < l.size(); i++) {
            CallableDemo demo = new CallableDemo(latch, i);
            Future<String> submit = executor.submit(demo);
            list.add(submit);
        }
        latch.await();
        list.forEach(s -> {
            String s1;
            try {
                s1 = s.get();
                res.add(s1);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            System.out.println(s1);
        });
        return res;
    }

    @SneakyThrows
    public List<String> run(List<String> l) {
        CountDownLatch latch = new CountDownLatch(l.size());
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<String> res = new ArrayList<>();
        for (String s : l) {
            Runnable run = () -> {
                res.add(s);
                latch.countDown();
            };
            executor.execute(run);
        }
        latch.await();
        executor.shutdown();
        return res;
    }
}
