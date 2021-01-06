package com.czz;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author czz
 * @create 2021-01-05 20:59
 **/
public class CheckItemProvider {

    public static void main(String[] args) throws IOException {

        new ClassPathXmlApplicationContext("classpath:spring-service.xml");
        System.in.read();
    }
}
