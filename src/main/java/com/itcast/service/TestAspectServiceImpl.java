package com.itcast.service;

import java.util.Date;
import org.springframework.stereotype.Service;
@Service
public class TestAspectServiceImpl {
    public void test() throws InterruptedException {
        Thread.sleep(10000);
        System.out.println("========" + new Date().getTime() + "===========");
    }
}