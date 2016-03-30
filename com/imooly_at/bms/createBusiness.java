package com.imooly_at.bms;
/*
* Created by QianQiang on 14-9-24.
* Used for creating normal business(type=4)
* Output: save businessid,businessname in file ROOT/business.txt
* */
import com.imooly_at.tools.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class createBusiness {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private Date d = new Date();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private String dateStr = sdf.format(d);
    String sname = "q_" + dateStr;
    String fname = "QQ_" + dateStr;

    @Before
    public void setUp() throws Exception {


        loginBMS.loginBMS();
    }

    @Test
    public void createBusiness() throws Exception {


        WebDriverEventListener eventListener = new screenshotListener();

        //driver = new EventFiringWebDriver(loginBMS.driver).register(eventListener); //实现监听
        driver = loginBMS.driver;

        //等待元素出现
        WebDriverWait wait = new WebDriverWait(driver, 3L);
        //创建商家
        driver.findElement(By.className("icon-signal")).click();

        driver.findElement(By.linkText("商家管理")).click();

        driver.findElement(By.linkText("添加商家")).click();

        //去除隐藏属性
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("role")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('role').removeAttribute('style');");
        //driver.findElement(By.id("role")).click();
        Select s = new Select(driver.findElement(By.id("role")));
        s.selectByIndex(0);
        s.selectByIndex(1);
    /*
    s.selectByVisibleText("联盟商家");
    s.selectByVisibleText("商城商家");
*/


        driver.findElement(By.id("shortname")).sendKeys(sname);
        driver.findElement(By.id("name")).sendKeys(fname);
        driver.findElement(By.id("discount")).sendKeys("9");


        //去除隐藏属性 省市区及地址
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("provinceid")));
        while (driver.findElement(By.id("provinceid")).isDisplayed() == false) {
            js.executeScript("document.getElementById('provinceid').removeAttribute('style');");
        }

        //driver.findElement(By.id("role")).click();
        Select sp = new Select(driver.findElement(By.id("provinceid")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@id='provinceid'][option='江苏省']")));
        sp.selectByVisibleText("江苏省");


        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cityid")));
        while (driver.findElement(By.id("cityid")).isDisplayed() == false) {
            js.executeScript("document.getElementById('cityid').removeAttribute('style');");
            System.out.println(driver.findElement(By.id("cityid")).isDisplayed());
            getScreenshot.getScreenshot(driver, this.getClass().getName() + "_" + Thread.currentThread().getStackTrace()[1].getMethodName());

        }

        Select sc = new Select(driver.findElement(By.id("cityid")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@id='cityid'][option='苏州市']")));
        sc.selectByVisibleText("苏州市");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("addrareaid")));


        while (driver.findElement(By.id("addrareaid")).isDisplayed() == false) {
            js.executeScript("document.getElementById('addrareaid').removeAttribute('style');");
            System.out.println(driver.findElement(By.id("addrareaid")).isDisplayed());
            getScreenshot.getScreenshot(driver, this.getClass().getName() + "_" + Thread.currentThread().getStackTrace()[1].getMethodName());

        }
        //driver.findElement(By.id("role")).click();
        Select st = new Select(driver.findElement(By.id("addrareaid")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@id='addrareaid'][option='吴中区']")));
        st.selectByVisibleText("吴中区");

        driver.findElement(By.id("addrstreet")).sendKeys("星湖街328号");

        driver.findElement(By.name("resume")).sendKeys("测试商家简介信息 @" + dateStr);


        //保存 xpath有效
        try {
            WebElement w2 = driver.findElement(By.xpath("//button[text()='保存']"));
            w2.click();
            ;
        } catch (Exception e2) {
            e2.printStackTrace();
        }

        resolvePopup.accept(driver);


        //保存 cssselector有效
/*    try
    {
    WebElement w3 = driver.findElement(By.cssSelector("button.btn.btn-success"));
    w3.click();
    }
    catch(Exception e3)
    {
    	e3.printStackTrace();
    }
    System.out.print("test");


    //添加商品分类 已从流程中移除

    /*
    //等待添加分类页面包含添加分类字段 “appendcate”
    //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    while (driver.getCurrentUrl().indexOf("appendcate")<0)
    {
    	driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

*/
        db newjdbc = new db();
        ResultSet rs1, rs2;
/*
    rs1 =  newjdbc.executeQuery("select id from mb_business_classify t where caption = '电玩'");

    String s1 = null;
    try
    {
    while (rs1.next()) {
    s1 = rs1.getString("id");
    };
    }
    catch(Exception e1)
    {
    	e1.printStackTrace();
    };
      */

/*


/*移除隐藏属性 现定位子节点元素，在获取父元素*//*

    js.executeScript("document.getElementById('"+s1+"').parentNode.parentNode.removeAttribute('style');");

    WebElement w1 = driver.findElement(By.cssSelector("input[type='checkbox'][value='"+s1+"']"));
    w1.click();

    //保存分类 防止cache问题导致的无法定位，使用其他方式定位保存按钮
    driver.findElement(By.xpath("//div[@id=sidetree]/input[@value='保存']")).click();
*/


        //简单判断 数据库存在商家数据
        rs2 = newjdbc.executeQuery("select * from mb_business where shortname = '" + sname + "' and name = '" + fname + "'");
        assertNotNull(rs2);

        //输出商家名称和id
        String file = "./business.txt";
        String bid = null;
        while (rs2.next()) {
            bid = rs2.getString("id");
        }
        fileopt.rewriteFile(file, fname + "," + bid);

    }


    @After
    public void tearDown() throws Exception {
        //注销 xpath解决方案
        driver.findElement(By.xpath("//span[text()='注销']")).click();
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }


}
