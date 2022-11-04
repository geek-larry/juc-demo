package com.jade.demo.thread_local.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description
 * @auther guangxush
 * @create 2019/8/31
 */

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Score {

    private Long id;

    private String uid;

    private String classId;

    private String className;

    private Double score;

    private Date date;
}
