package com.imooly_at.tools;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by qianqiang on 15-1-16.
 * Simulate as a normal user to create amount of order in a browser.
 */
public class createOrders {
    public WebDriver driver;


    @Test
    public void addOrders() throws Exception {

        System.setProperty("webdriver.firefox.bin", "D:/ProgramFiles/Mozilla Firefox/firefox.exe");
        //加载firebug插件

        File file = new File("E:/Selenium/workspace/firebug-1.12.8.xpi");
        FirefoxProfile firefoxProfile = new FirefoxProfile();
/*

        try {
            firefoxProfile.addExtension(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        firefoxProfile.setPreference("extensions.firebug.currentVersion", "1.12.8");
*/

        driver = new FirefoxDriver(firefoxProfile);

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("http://192.168.10.34:9080/login.html?service=http://192.168.10.34:8081/");
        driver.findElement(By.id("username")).sendKeys("13114648685");
        driver.findElement(By.id("password")).sendKeys("123456");
        driver.findElement(By.className("login_bt")).click();


        for (int x = 0; x < 50; x++) {

            driver.get("http://192.168.10.34:8081/item-51e35a8545a040fe9948ba968373199b.html");


            //选择规格创建订单

            String p0, p1;
            WebElement WEF = driver.findElement(By.className("promptBox"));

            List<WebElement> WEFList = driver.findElements(By.className("prop"));
            Random r = new Random();
            for (int n = 0; n < 3; n++) {
                //WEFList.get(n).findElements(By.xpath("//a")).get(r.nextInt(3)).click();
                String temp = "//div[dl[@id='errorProp']]/dl[" + (n + 1) + "]//a";
                driver.findElements(By.xpath(temp)).get(r.nextInt(3)).click();
            }

            p0 = (driver.findElement(By.id("viprmb")).getText().split("¥"))[1];

            driver.findElement(By.className("buy_btn")).click();
            driver.findElement(By.id("submitorder_1")).click();

            changeWindows.change(driver);

/*

            p1 = (driver.findElement(By.cssSelector("font[style='color:#ff5901;']")).getText().split("￥"))[1];
            if (p0.equals(p1)) {
                System.out.println(driver.findElement(By.cssSelector("font[style='color:#86c12e;']")).getText());
            } else {
                System.out.println((driver.findElement(By.cssSelector("font[style='color:#86c12e;']")).getText()) + " ERROR");
            }
*/

            //driver.switchTo().window(driver.getCurrentUrl()).close();
            driver.close();
//        changeWindows.change(driver);
            //切换至原窗口
            Set<String> handles = driver.getWindowHandles();
            Iterator<String> it = handles.iterator();
            while (it.hasNext()) {
                WebDriver window = driver.switchTo().window(it.next());//切换到新窗口
            }
            //driver.getCurrentUrl();
        }


    }
}
