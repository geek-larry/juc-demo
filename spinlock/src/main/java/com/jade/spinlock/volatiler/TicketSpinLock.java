package com.jade.spinlock.volatiler;

import java.util.concurrent.atomic.AtomicInteger;

// 排队自旋锁
public class TicketSpinLock {

    //服务序号，不需要cas，因为释放锁的只有一个线程，serviceNum++的环境是天生安全的
    private volatile int serviceNum = 0;
    //排队序号，cas
    private AtomicInteger ticketNum = new AtomicInteger(0);
    //记录当前线程的排队号，主要的作用是为了实现可重入，防止多次取号
    private ThreadLocal<Integer> threadOwnerTicketNum = new ThreadLocal<>();
    //state不作为锁状态标志，只代表锁重入的次数
    protected volatile int state = 0;
    private volatile Thread exclusiveOwnerThread;

    public void lock() {
        final Thread current = Thread.currentThread();
        Integer myTicketNum = threadOwnerTicketNum.get();
        if (myTicketNum == null) {
            myTicketNum = ticketNum.getAndIncrement();
            threadOwnerTicketNum.set(myTicketNum);
            while (serviceNum != myTicketNum) {}
            //若拿的排队号刚好等于服务序号，说明可以获取锁，即获取锁成功
            setExclusiveOwnerThread(current);
            state ++ ;
            System.out.println(String.format("ticket lock ok, thread=%s;state=%d;serviceNum=%d;next-ticketNum=%d;", current.getName(), getState(), serviceNum, ticketNum.get()));
            return;
        }
        //若已经取号，判断当前持锁线程是当前线程(重入性)
        if (current == getExclusiveOwnerThread()) {
            //重入
            state++;
            System.out.println(String.format("ticket re lock ok, thread=%s;state=%d;serviceNum=%d;next-ticketNum=%d;", current.getName(), getState(), serviceNum, ticketNum.get()));
            return;
        }
    }
    public void unlock() {
        if (Thread.currentThread() != getExclusiveOwnerThread())
            //不是当前线程，不能unLock 抛异常
            throw new IllegalMonitorStateException();
        state--;
        if (state == 0) {
            //完全释放锁，owner+1
            //服务序号是线性安全的，无需cas
            threadOwnerTicketNum.remove();
            setExclusiveOwnerThread(null);
            serviceNum ++;
            System.out.printf("ticket un lock ok, thread=%s;next-serviceNum=%d;ticketNum=%d;%n", Thread.currentThread().getName(), serviceNum, ticketNum.get());
        }
    }

    public int getState() {
        return state;
    }

    public Thread getExclusiveOwnerThread() {
        return exclusiveOwnerThread;
    }

    public void setExclusiveOwnerThread(Thread exclusiveOwnerThread) {
        this.exclusiveOwnerThread = exclusiveOwnerThread;
    }
}
