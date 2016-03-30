package com.imooly_at.bms;

import com.imooly_at.tools.db;
import com.imooly_at.tools.fileopt;
import com.imooly_at.tools.resolvePopup;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by qianqiang on 14-11-4.
 * Test create/edit/delete the good postage
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testPostage {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    static String dateStr = sdf.format(new Date());
    WebDriver driver = loginBMS.driver;
    String gpname = "atGoodparam" + dateStr;

    db db = new db();


    @Before
    public void setUp() throws Exception {
        loginBMS.loginBMS();
        //WebDriver driver = loginBMS.driver;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateStr = sdf.format(new Date());
        WebDriver driver = loginBMS.driver;
        String gpname = "atGoodparam" + dateStr;


    }

    @Test
    public void createP() throws Exception {

        WebDriver driver = loginBMS.driver;

        driver.findElement(By.className("icon-th")).click();
        driver.findElement(By.linkText("商品邮费")).click();


        driver.findElement(By.xpath("//a[span='添加邮费模板']/span[1]")).click();

        /*获取商家名称，并选择*/
        String bn;
        String file = "./business.txt";
        bn = fileopt.readFile(file);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('choosebusiness').removeAttribute('style');");
        Select s = new Select(driver.findElement(By.id("choosebusiness")));
        s.selectByVisibleText("QQ百货X");

        driver.findElement(By.xpath("//div[button='添加']/button[1]")).click();

        /*切换window，进入商品邮费模板信息录入页面*/
        String currentWindow = driver.getWindowHandle();//获取当前窗口句柄
        Set<String> handles = driver.getWindowHandles();//获取所有窗口句柄
        Iterator<String> it = handles.iterator();
        while (it.hasNext()) {
            if (currentWindow == it.next()) {
                continue;
            }
            WebDriver window = driver.switchTo().window(it.next());//切换到新窗口
            //window.close();//关闭新窗口
        }

        //模板名称
        driver.findElement(By.id("tempName")).sendKeys(gpname);

        //快递方式
        driver.findElement(By.xpath("//div[label='选择快递方式']/div/input[last()]")).click();
        driver.findElement(By.xpath("//div[label='选择快递方式']/div/input[1]")).click();

        //出发地
        s = new Select(driver.findElement(By.id("Province")));
        s.selectByIndex((int) (Math.random() * 30));
        //s.selectByVisibleText("上海市");

        //目的地及邮费
        driver.findElement(By.xpath("//div[button='添加地区']/button[1]")).click();
        int t = (int) (Math.random() * 7);
        if (t == 0) {
            t++;
        }
        driver.findElement(By.xpath("//div[@class='Area-list']/ul[" + t + "]/li[1]")).click();

        //debuginfo
        // driver.findElement(By.xpath("//div[@class='Area-list']/ul["+ (t+1) +"]/li[1]")).click();

        driver.findElement(By.xpath("//button[@onclick='submitArea()']")).click();


        //切换至table所在form
        List<WebElement> tarea = driver.findElements(By.xpath("//table[@id='ptable']/tr"));
        for (int i = 0; i < tarea.size(); i++) {
            driver.findElement(By.xpath("//table[@id='ptable']/tr[" + (i + 1) + "]//input[@id='prices']")).clear();
            driver.findElement(By.xpath("//table[@id='ptable']/tr[" + (i + 1) + "]//input[@id='prices']")).sendKeys("1.01");
            //driver.findElement(By.xpath("//table[@id='ptable']/tr[1]/th[@id='prices']"))
        }

        driver.findElement(By.id("save_btn")).click();

        //处理弹出窗口
        driver.switchTo().alert().accept();


        /*切换至原始窗口*/
        driver.switchTo().window(currentWindow);
        driver.findElement(By.id("successbtn")).click();


        //注销driver
        driver.close();
    }

    @Test
    public void editP() throws Exception {
        WebDriver driver = loginBMS.driver;

        driver.findElement(By.className("icon-th")).click();
        driver.findElement(By.linkText("商品邮费")).click();

        driver.findElement(By.id("tempname")).sendKeys(gpname);
        driver.findElement(By.xpath("//li[button='查询']/button")).click();

        driver.findElement(By.cssSelector("a[data-original-title='修改'][class='tip-top']")).click();


        /*切换window，进入商品邮费模板信息录入页面*/
        String currentWindow = driver.getWindowHandle();//获取当前窗口句柄
        Set<String> handles = driver.getWindowHandles();//获取所有窗口句柄
        Iterator<String> it = handles.iterator();
        while (it.hasNext()) {
            if (currentWindow == it.next()) {
                continue;
            }
            WebDriver window = driver.switchTo().window(it.next());//切换到新窗口
            //window.close();//关闭新窗口
        }


        //切换至table所在form,编辑金额
        List<WebElement> tprice = driver.findElements(By.xpath("//table[@id='ptable']/tbody//input[@id='prices']"));
        for (int i = 0; i < tprice.size(); i++) {
            tprice.get(i).clear();
            tprice.get(i).sendKeys("2.01");
            //driver.findElement(By.xpath("//table[@id='ptable']/tr[1]/th[@id='prices']"))
        }

        driver.findElement(By.id("save_btn")).click();

        resolvePopup.accept(driver);


        /*切换至原始窗口*/
        driver.switchTo().window(currentWindow);
        driver.findElement(By.id("successbtn")).click();


        //注销driver
        driver.close();


    }


    @Test
    public void removeP() throws Exception {

        WebDriver driver = loginBMS.driver;

        driver.findElement(By.className("icon-th")).click();
        driver.findElement(By.linkText("商品邮费")).click();

        driver.findElement(By.id("tempname")).sendKeys(gpname);
        driver.findElement(By.xpath("//li[button='查询']/button")).click();

        driver.findElement(By.cssSelector("a[data-original-title='删除'][class='tip-top']")).click();

        //resolvePopup.accept(driver);
        driver.switchTo().alert().accept();

        driver.close();

    }


    @After
    public void teardown() {

        db.close();
    }

}
