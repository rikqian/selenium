package com.imooly_at.tools;

/**
 * Created by QianQiang on 15-1-26.
 */

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.concurrent.TimeUnit;

/**
 * Created by QianQiang on 14-10-20.
 * basic method for login BMS. used to be invoked by other test methods
 */

public class repeatloginBMS {
    public static WebDriver driver;
    private static String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();


    @Test
    public void loginBMS() throws Exception {
        baseUrl = "http://192.168.10.33:8087";
        System.setProperty("webdriver.firefox.bin", "D:/ProgramFiles/Mozilla Firefox/firefox.exe");
        //加载firebug插件,启动firefox
        //File file = new File("E:/Selenium/workspace/firebug-1.12.8.xpi");
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        //firefoxProfile.addExtension(file);
        firefoxProfile.setPreference("extensions.firebug.currentVersion", "1.12.8");
        driver = new FirefoxDriver(firefoxProfile);

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

/*        driver = new HtmlUnitDriver(true);
        driver.get(baseUrl);*/


        for (int i = 0; i < 100; i++) {


            driver.get(baseUrl);
    /*新版单点登录系统 更新元素信息 20141017*/
            driver.findElement(By.name("username")).clear();
            driver.findElement(By.name("username")).sendKeys("qqadmin");

            driver.findElement(By.id("password")).sendKeys("123456");

            driver.findElement(By.className("submit-btn")).click();

            driver.findElement(By.xpath("//a[span='注销']/span")).click();

            Thread.sleep(1000);

        }

    }


    @After
    public void tearDown() throws Exception {
        //注销
        //driver.findElement(By.xpath("//a[span='注销']/span")).click();
        driver.quit();

    }


}
