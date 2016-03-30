package com.imooly_at.bs;

import com.imooly_at.tools.fileopt;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by qianqiang on 14-9-28.
 * Use the BS to create a good.
 */
public class createGoods {

    @Before
    public void setUp() throws Exception {
        String baseUrl = "http://192.168.10.43:8071";
        loginBS.loginBS(baseUrl);
        WebDriver driver = loginBS.driver;
    }

    @Test
    public void createGoods() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateStr = sdf.format(new Date());

        WebDriver driver = loginBS.driver;

        driver.findElement(By.linkText("商品管理")).click();
        driver.findElement(By.cssSelector("a[href*='/CommodityManagement/AddCommodity']")).click();


        /*选择分类
        * ####先定位到页面内部iframe，才能对该iframe内元素进行操作####
        * */
        WebElement frame = driver.findElement(By.cssSelector("iframe"));
        driver.switchTo().frame(frame);

        driver.findElement(By.cssSelector("span[onclick*='手机数码']")).click();
        driver.findElement(By.cssSelector("span[onclick*='手机通讯']")).click();
        driver.findElement(By.cssSelector("span[onclick*='对讲机']")).click();
        /*提交分类选择*/
        driver.findElement(By.cssSelector("input[value='提交']")).click();


        /*录入商品信息
        * */
        String goodname = new String();
        goodname = "atgood_" + dateStr;
        driver.findElement(By.id("Name")).sendKeys(goodname);
        System.out.println("testinfo");
        //新增关联分类；通过正则表达式匹配字符串查找元素  To do
        //driver.findElement(By.partialLinkText("新增分类")).click();


        /*选择邮费模板*/
        Select selectpostage = new Select(driver.findElement(By.id("PostageCode")));
        selectpostage.selectByVisibleText("QQ公共邮费");

        /*选择标签*/
        driver.findElements(By.id("tagitem")).get(0).click();


        /*选择 规格参数值*/
        driver.findElements(By.id("parameteritem")).get(0).click();
        List<WebElement> params = driver.findElements(By.cssSelector("input[name='paraText']"));
        for (int i = 0; i < params.size(); i++) {
            params.get(i).clear();
            params.get(i).sendKeys("测试参数值" + i);
        }




        /*选择规格： 无规格*/
        driver.findElement(By.id("noformat")).click();

        //设置库存
        List<WebElement> stocks = driver.findElements(By.className("stock"));

        for (int i = 0; i < stocks.size(); i++) {
            stocks.get(i).clear();
            stocks.get(i).sendKeys("10");
        }




        /*上传图片
        * 使用js设置页面元素，直接拿去服务器图片
        * */

        JavascriptExecutor js = (JavascriptExecutor) driver;
        //Logo图片
        js.executeScript("document.getElementById('Logo_Path').setAttribute('src','/uploadfiles/brandfccc388f143f4ba0add75ec65541ad01/images/fc874c11-0aa4-402f-aa85-c19d3f5f7917.jpg');");
        js.executeScript("document.getElementById('LogoPath').setAttribute('value','/uploadfiles/brandfccc388f143f4ba0add75ec65541ad01/images/fc874c11-0aa4-402f-aa85-c19d3f5f7917.jpg');");

        //展示图片
        js.executeScript("document.getElementById('Img1_Path').setAttribute('src','/uploadfiles/brandfccc388f143f4ba0add75ec65541ad01/images/fc874c11-0aa4-402f-aa85-c19d3f5f7917.jpg');");
        js.executeScript("document.getElementById('Img1').setAttribute('value','/uploadfiles/brandfccc388f143f4ba0add75ec65541ad01/images/fc874c11-0aa4-402f-aa85-c19d3f5f7917.jpg');");

        //添加简介
        driver.findElement(By.id("Briefdescribe")).sendKeys(goodname);

        //切换iframe 设置商品描述
        /*选择分类
        * ####先定位到页面内部iframe，才能对该iframe内元素进行操作####
        * */
        WebElement gframe = driver.findElement(By.cssSelector("iframe"));
        driver.switchTo().frame(gframe);
        driver.findElement(By.className("cke_show_borders")).sendKeys(goodname);

        //从iframe跳回
        driver.switchTo().defaultContent();
        driver.switchTo().frame(frame);
        //保存
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        //处理弹出框
        driver.switchTo().alert().accept();

        //System.out.println("debug test");

        //记录商品名称
        String file = "./good.txt";
        fileopt.rewriteFile(file, goodname);

        //注销
        driver.quit();


    }


}
