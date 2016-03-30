package com.imooly_at.bms;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;
/**
 * Created by QianQiang on 14-10-20.
 * basic method for login BMS. used to be invoked by other test methods
 */

public class loginBMS {
    public static WebDriver driver;
    private static String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();


    @Test
    public static void loginBMS() throws Exception {
        System.setProperty("webdriver.firefox.bin", "D:/ProgramFiles/Mozilla Firefox/firefox.exe");
        //加载firebug插件
        File file = new File("E:/Selenium/workspace/firebug-1.12.8.xpi");
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.addExtension(file);
        firefoxProfile.setPreference("extensions.firebug.currentVersion", "1.12.8");


        System.setProperty("webdriver.chrome.driver", "D:/ProgramFiles/Chrome/chrome.exe");

        //driver = new ChromeDriver();

        driver = new FirefoxDriver(firefoxProfile);
        baseUrl = "http://192.168.10.33:8087";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl);

    /*新版单点登录系统 更新元素信息 20141017*/
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("qqadmin");

        driver.findElement(By.id("password")).sendKeys("123456");

        driver.findElement(By.className("submit-btn")).click();
        //driver.findElement(By.partialLinkText("index.html")).click();
        //driver.findElement(By.name("submit")).click();

//    driver.findElement(By.linkText("我的魔力")).click();
//    driver.findElement(By.linkText("退出")).click();

//    driver.findElement(By.xpath("/html/body/div[2]/ul/li[3]/a")).click();

    }


    @After
    public void tearDown() throws Exception {
        //注销
        driver.findElement(By.xpath("//span[text()='注销']")).click();
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }


}
