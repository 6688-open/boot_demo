package com.dj.boot.test.timer;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest  {

    public static void main(String[] args) {
        //Java 中定时器
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("武汉 加油");
            }
        }, 1, 1000);
    }
}
