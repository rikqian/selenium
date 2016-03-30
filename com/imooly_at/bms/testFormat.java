package com.imooly_at.bms;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.imooly_at.tools.mongodb.getdb;

/**
 * Created by qianqiang on 14-10-31.
 * Test the create/edit/delete for good format.
 */

public class testFormat {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    String dateStr = sdf.format(new Date());
    WebDriver driver = loginBMS.driver;
    String gfname = "atformat" + dateStr;
    String gfvalue = "atV1|atV2|atV3|atV4|atV5|atV6|atV7|atV8";

    @Before
    public void setUp() throws Exception {
        loginBMS.loginBMS();
        //WebDriver driver = loginBMS.driver;

    }

    @Test
    public void createFormat() throws Exception {

        WebDriver driver = loginBMS.driver;

        driver.findElement(By.className("icon-th")).click();
        driver.findElement(By.linkText("商品规格")).click();

        /*添加商品规格
        * 先定位到元素a,再选择其子元素 span
        * */
        driver.findElement(By.cssSelector("a[href='/index.php?m=eshop&f=ftypeadd'] span")).click();


        //添加商品规格信息

        driver.findElement(By.id("resume")).sendKeys(gfname);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('classify').removeAttribute('style');");
        Select s = new Select(driver.findElement(By.id("classify")));
        s.selectByVisibleText("手机数码");

        //等待元素出现
        WebDriverWait wait = new WebDriverWait(driver, 3L);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[option='手机配件']")));

        js.executeScript("document.getElementById('s2id_autogen1').nextSibling.removeAttribute('style');");
        //通过Xpath定位上级，再定位第二个select元素。
        s = new Select(driver.findElement(By.xpath("//div[@id='classid']/select[2]")));
        s.selectByVisibleText("手机配件");

        //等待元素出现
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[option='电池']")));

        js.executeScript("document.getElementById('s2id_autogen3').nextSibling.removeAttribute('style');");
        s = new Select(driver.findElement(By.xpath("//div[@id='classid']/select[3]")));
        s.selectByVisibleText("电池");


        /*选择商家*/
        //不选择，默认为公用商家


        /*录入规格值*/

        driver.findElement(By.id("ftvalue")).clear();
        driver.findElement(By.id("ftvalue")).sendKeys(gfvalue);


        /*保存规格*/
        driver.findElement(By.cssSelector("button[onclick='return clicksubmit()']")).click();


        /*关闭确认提示*/
        driver.findElement(By.id("popup_ok")).click();



    /*测试编辑*/

        //通过搜索获取已创建规格
        driver.findElement(By.id("format")).clear();
        driver.findElement(By.id("format")).sendKeys(gfname);

        driver.findElement(By.xpath("//li[button='查询']")).click();


        //通过xpath获取编辑按钮
        //driver.findElement(By.xpath("//tr[td='电脑CPU']/td[last()]/a[1]")).click();
        driver.findElement(By.xpath("//tr[td='" + gfname + "']/td[last()]/a[1]")).click();

        /*编辑规格值*/
        driver.findElement(By.id("ftvalue")).sendKeys("|1atV1|1atV2");

        /*保存*/
        driver.findElement(By.xpath("//div[button='保存']/button[1]")).click();

        /*关闭确认提示*/
        driver.findElement(By.id("popup_ok")).click();




    /*删除*/

        //通过搜索获取已创建规格
        driver.findElement(By.id("format")).clear();
        driver.findElement(By.id("format")).sendKeys(gfname);

        driver.findElement(By.xpath("//li[button='查询']")).click();


        //通过xpath获取删除按钮
        driver.findElement(By.xpath("//tr[td='" + gfname + "']/td[last()]/a[last()]")).click();

        //确认windows弹框
        driver.switchTo().alert().accept();

        /*关闭确认提示*/
        //防止元素缓存错误“Element not found in the cache”，使用新方法定位确认提示信息。


        driver.findElement(By.xpath("//div[div='删除成功']//input[@id='popup_ok']")).click();


    /*数据库检查*/
        DBCollection db = getdb("formattype");
        DBObject dbobj = db.findOne(new BasicDBObject("resume", gfname), new BasicDBObject("delstatus", true));
        Long gdels = (Long) dbobj.get("delstatus");
        Long tgdels = 1L;
        Assert.assertEquals(tgdels, gdels);


        /*关闭driver*/
        driver.close();
    }


}
