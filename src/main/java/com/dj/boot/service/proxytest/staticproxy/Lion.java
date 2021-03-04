package com.dj.boot.service.proxytest.staticproxy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 狮子 Lion  实现了猫科动物接口Cat， 并实现了具体的行为。作为委托类实现
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lion implements Cat {

    private String name;
    private int runningSpeed;




    @Override
    public String eatFood(String foodName) {
        String eat = this.name + " Lion eat food. foodName = " + foodName;
        System.out.println(eat);
        return eat;
    }

    @Override
    public boolean running() {
        System.out.println(this.name + " Lion is running . Speed :" + this.runningSpeed);
        return false;
    }
}
