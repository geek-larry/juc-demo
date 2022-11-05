package com.jade.queue.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Slf4j
public class ThreadPoolService implements Runnable {

    private String businessNo;

    public ThreadPoolService(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    @Override
    public void run() {

        //线程阻塞
        try {
            Thread.sleep(1000);
            log.info("多线程已经处理订单插入系统，订单号：" + businessNo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
