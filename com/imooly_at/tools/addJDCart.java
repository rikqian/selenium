package com.imooly_at.tools;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by qianqiang on 14-12-17.
 * Add amount of goods into cart in the website www.jd.com.
 */
public class addJDCart {
    public static WebDriver driver;
    private StringBuffer verificationErrors = new StringBuffer();


    public static void main(String[] args) {
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

        String baseUrl = "http://www.jd.com";
        driver.get(baseUrl);
        //driver.get("http://www.amazon.cn/s/ref=nb_sb_noss_1?__mk_zh_CN=%E4%BA%9A%E9%A9%AC%E9%80%8A%E7%BD%91%E7%AB%99&url=search-alias%3Daps&field-keywords=iphone");

        //http://gate.jd.com/InitCart.aspx?pid=1309359730&pcount=1&ptype=1
        for (int i = 0; i < 49; i++) {
            driver.get("http://gate.jd.com/InitCart.aspx?pid=" + (1309449730 + i) + "&pcount=1&ptype=1");
        }

        /*
        String currentWindow = "";
        WebDriver window = null;
        for(int i = 0;i<1000;i++){

            driver.findElement(By.name("submit.addToCart")).click();

            currentWindow = driver.getWindowHandle();
            window = driver.switchTo().window(currentWindow);
            window.close();
            driver.switchTo().window(driver.getWindowHandle());

        }
*/
        System.out.println("END");
    }
}
