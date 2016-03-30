package com.imooly_at.test;


import com.imooly_at.tools.screenshotListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class debug {

    public static void main(String[] args) throws IOException, SQLException {

        //如果火狐浏览器没有默认安装在C盘，需要制定其路径
        //System.setProperty("webdriver.firefox.bin", "D:\ProgramFiles\Mozilla Firefox\firefox.exe"); 
        System.setProperty("webdriver.firefox.bin", "D:/ProgramFiles/Mozilla Firefox/firefox.exe");
        //加载firebug插件
        File file = new File("E:/Selenium/workspace/firebug-1.12.8.xpi");
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.addExtension(file);
        firefoxProfile.setPreference("extensions.firebug.currentVersion", "1.12.8");

        WebDriverEventListener eventListener = new screenshotListener();

        //实现错误监听
        WebDriver driver = new EventFiringWebDriver(new FirefoxDriver(firefoxProfile)).register(eventListener);
        String baseUrl = "http://192.168.10.33:8087";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        //driver.
        driver.get(baseUrl);

        //登录
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("admin");

        driver.findElement(By.id("passwords")).sendKeys("123456");

        driver.findElement(By.className("btn-success")).click();
        //设置10秒  
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


    }

}