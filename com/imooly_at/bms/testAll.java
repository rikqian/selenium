package com.imooly_at.bms;

import junit.extensions.RepeatedTest;
import junit.framework.JUnit4TestAdapter;
import junit.framework.TestSuite;

/**
 * Created by qianqiang on 14-12-22.
 * Use the class TestSuite to define all the testcases and the order for running.
 */
public class testAll {
    public static TestSuite suite() {
        int i = 10;
        TestSuite suite = new TestSuite(testAll.class.getName());

        suite.addTest(new JUnit4TestAdapter(createGoods.class));   //添加一条测试用例
        suite.addTest(new JUnit4TestAdapter(goodOpt.class));
        //suite.addTest(new RepeatedTest(new JUnit4TestAdapter(createBusiness.class), i)); //添加测试用例，并设置执行次数。


        return suite;

    }
}
