package com.jade.spinlock.controller;

import com.jade.spinlock.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TestController {

    private final OrderService orderService;

    @GetMapping("t1")
    public List<String> test01(){
        List<String> l = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            l.add("test"+i);
        }
        List<String> run = orderService.run(l);
        orderService.call(l);
        return run;
    }
}
