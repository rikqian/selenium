package com.imooly_at.bs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by qianqiang on 14-9-28.
 * Basic BS login method, used to be revoked by other test cases.
 */
public class loginBS {
    public static WebDriver driver;
    private StringBuffer verificationErrors = new StringBuffer();

    public static void loginBS(String baseUrl) {

        System.setProperty("webdriver.firefox.bin", "D:/ProgramFiles/Mozilla Firefox/firefox.exe");
        //加载firebug插件
        File file = new File("E:/Selenium/workspace/firebug-1.12.8.xpi");
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        try {
            firefoxProfile.addExtension(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        firefoxProfile.setPreference("extensions.firebug.currentVersion", "1.12.8");

        driver = new FirefoxDriver(firefoxProfile);

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl);

        //登录

        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("f商家at_20141105105012");

        driver.findElement(By.id("password")).sendKeys("123456");

        driver.findElement(By.className("btn-success")).click();
    }

    public static void main(String[] args) {
        String baseUrl = "http://192.168.10.43:8071";
        loginBS(baseUrl);
    }
}
