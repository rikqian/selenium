package com.imooly_at.test;

import java.util.Random;

/**
 * Created by qianqiang on 15-1-16.
 */
public class testRandom {

    public static void main(String[] args) {
        int max = 20;
        int min = 10;
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int s = random.nextInt(max) % (max - min + 1) + min;
            int m = random.nextInt(3);
            System.out.println(m);
        }
    }
}
