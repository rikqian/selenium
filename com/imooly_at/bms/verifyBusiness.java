package com.imooly_at.bms;

import com.imooly_at.tools.db;
import com.imooly_at.tools.fileopt;
import com.imooly_at.tools.resolvePopup;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.ResultSet;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

/**
 * Created by qianqiang on 14-9-25.
 * Test the Verification of a new business.
 * Input: the business name and business id in file ROOT/business.txt
 */
public class verifyBusiness {
    private WebDriver driver;
    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        loginBMS.loginBMS();

    }

    @Test
    public void verifyBusiness() throws Exception {
        driver = loginBMS.driver;
        WebDriverWait wait = new WebDriverWait(driver, 3L);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //进入审核页面
        driver.findElement(By.className("icon-signal")).click();

        driver.findElement(By.linkText("待审核")).click();
        //读取商家名称 获得businessid
        String bn;
        String bid = null;
        String file = "./business.txt";
        bn = fileopt.readFile(file).split(",")[0];
        bid = fileopt.readFile(file).split(",")[1];


        db dblink = new db();
        ResultSet rs;
/*
        rs = dblink.executeQuery("select id from mb_business t where name = '"+bn+"'");
        try {
            while (rs.next()) {
                bid = rs.getString("id");
            }
        }
        catch (Exception e) {
        e.printStackTrace();
        }
*/

        /*driver.findElement(By.partialLinkText("checkbusinessinfo&bid="+bid)).click();*/

        /*翻页查找待审核商家*/

        while (resolvePopup.isElementPresent(driver, By.cssSelector("a[data-original-title='审核'][href*='" + bid + "']")) == false) {
            driver.findElement(By.linkText("下一页")).click();
        }

        driver.findElement(By.cssSelector("a[data-original-title='审核'][href*='" + bid + "']")).click();

        while (driver.getCurrentUrl().indexOf("checkbusinessinfo") < 0) {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        }

        //编辑商家分类
        //联盟分类
        //if(driver.findElement(By.xpath("//a[span='商城分类']")).isDisplayed())


        driver.findElement(By.xpath("//ul[li[a='商家账号']]/li[a='商家分类']/a")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[span[strong='娱乐休闲']]/div")));
        driver.findElement(By.xpath("//li[span[strong='娱乐休闲']]/div")).click(); //展开分类选择框

        //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[span='温泉']/input")));
        //driver.findElement(By.xpath("//ul[@style='display: block;'][li[span='温泉']]"))


        //！！！提供等待方法，判断元素是否可见。
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//ul[li[span='温泉']]"))));


        //getScreenshot.getScreenshot(driver, "verifyBusiness" + Thread.currentThread().getStackTrace()[1].getMethodName());

        driver.findElement(By.xpath("//li[span='温泉']/input")).click();

        driver.findElement(By.xpath("//form[div[@id='classblock']]/button[@id='queding']")).click(); //保存联盟分类

        resolvePopup.accept(driver);

        //商城分类
        driver.findElement(By.xpath("//ul[li[a='商家账号']]/li[a='商家分类']/a")).click();
        js.executeScript("document.getElementById('tabletype').removeAttribute('style');");
        Select sc = new Select(driver.findElement(By.id("tabletype")));
        sc.selectByVisibleText("商城分类");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[span[strong='手机数码']]/div")));
        driver.findElement(By.xpath("//li[span[strong='手机数码']]/div")).click(); //展开分类选择框

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//ul[li[span='电玩']]"))));
        driver.findElement(By.xpath("//li[span='电玩']/input")).click();

        driver.findElement(By.xpath("//form[div[@id='classblock']]/button[@id='queding']")).click(); //保存商城分类

        resolvePopup.accept(driver);

        //编辑商家账号
        driver.findElement(By.linkText("商家账号")).click();
        driver.findElement(By.id("account")).clear();
        driver.findElement(By.id("account")).sendKeys(bn);

        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("123456");
        driver.findElement(By.id("aname")).clear();
        driver.findElement(By.id("aname")).sendKeys(bn.substring(6));


        //选择角色 去除隐藏属性

        js.executeScript("document.getElementById('roleid').removeAttribute('style');");
        //driver.findElement(By.id("role")).click();
        Select s = new Select(driver.findElement(By.id("roleid")));
        s.selectByVisibleText("联盟商城主账号");

        //保存
        driver.findElement(By.cssSelector("div.tab-pane.active button[id='queding']")).click();
        while (driver.findElement(By.id("popup_ok")).isDisplayed()) {
            driver.findElement(By.id("popup_ok")).click();
        }


        //编辑商家联系人
        driver.findElement(By.linkText("商家联系人")).click();

        driver.findElement(By.id("cname")).clear();
        driver.findElement(By.id("cname")).sendKeys(bn.substring(6));

        driver.findElement(By.id("phonenumber")).clear();
        driver.findElement(By.id("phonenumber")).sendKeys("18114648685");

        driver.findElement(By.id("mail")).clear();
        driver.findElement(By.id("mail")).sendKeys("rikqian@163.com");

        //保存
        driver.findElement(By.cssSelector("div.tab-pane.active button[id='queding']")).click();
        while (driver.findElement(By.id("popup_ok")).isDisplayed()) {
            driver.findElement(By.id("popup_ok")).click();
        }

        //通过
        /*solution1*/
        driver.findElement(By.cssSelector("input[type='button'][value='通过']")).click();
        /*solution2*/
        //driver.findElement(By.cssSelector("a[href*='"+bid+"']")).click();

        while (driver.findElement(By.id("popup_container")).isDisplayed()) {
            driver.findElement(By.id("popup_ok")).click();
        }


        //判断状态 "+bn+"
        /*需要等待 通过验证 操作完成后（判断弹出框），再检查状态，否则会遇到数据库值未更新就检查状态，导致结果错误*/
        int bstatus = 0;
        rs = dblink.executeQuery("select status from mb_business t where name = '" + bn + "'");
        try {
            while (rs.next()) {
                bstatus = rs.getInt("status");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(1, bstatus);

        dblink.close();
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
