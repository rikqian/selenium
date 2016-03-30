package com.imooly_at.vs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

public class loginVS {
    private WebDriver driver;

    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.firefox.bin", "D:/ProgramFiles/Mozilla Firefox/firefox.exe");
        //加载firebug插件
        File file = new File("E:/Selenium/workspace/firebug-1.12.8.xpi");
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.addExtension(file);
        firefoxProfile.setPreference("extensions.firebug.currentVersion", "1.12.8");

        driver = new FirefoxDriver(firefoxProfile);

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testloginVS() throws Exception {
        driver.get("http://192.168.10.50:8081/");
        driver.findElement(By.linkText("登录")).click();
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("12771781912");


        driver.findElement(By.id("tPassword")).sendKeys("123456");

        driver.findElement(By.name("submit")).click();

        driver.findElement(By.linkText("我的魔力")).click();
        driver.findElement(By.linkText("退出")).click();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
}
