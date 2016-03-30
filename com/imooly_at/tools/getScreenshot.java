package com.imooly_at.tools;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by qianqiang on 14-12-24.
 * Basic method to get a screenshot, used to get more info from screenshot while debugging the test cases
 * @author qianqiang
 * @version 0.01
 *
 */
public class getScreenshot {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    /*Test constructor */
    public getScreenshot(WebDriver driver) {

        try {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File("e:/Selenium/x.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
    * Summary: Used to get one screenshot once invoking this method;
    * @param driver: The class webdriver, used to invoke the method TakesScreenshot.getScreenshotAs;
    * @param classandmethodinfo: Use the class/method name as the prefix of the pic name;
    * */
    public static void getScreenshot(WebDriver driver, String classandmethodinfo) {
        String dateStr = sdf.format(new Date());
        try {
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File("e:/Selenium/errorpic/" + classandmethodinfo + "_" + dateStr + ".png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
