package com.imooly_at.bms;

import com.imooly_at.tools.getScreenshot;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by qianqiang on 14-11-4.
 * Test the create/edit/delete for goodparameters
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testGoodparam {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    String dateStr = sdf.format(new Date());
    public static WebDriver driver = loginBMS.driver;
    public String classn = this.getClass().getName() + "_";

    String gpvalue = "atGP1|atGP2|atGP3|atGP4|atGP5|atGP6|atGP7|atGP8";

    @BeforeClass
    public static void setUp() throws Exception {

        loginBMS.loginBMS();
        //WebDriver driver = loginBMS.driver;

    }

    @AfterClass
    public static void tearDown() throws Exception {
        WebDriver driver = loginBMS.driver;
        driver.close();
    }

    @Test
    public void createGP() throws Exception {
        WebDriver driver = loginBMS.driver;
        driver.findElement(By.className("icon-th")).click();
        driver.findElement(By.linkText("规格参数模板")).click();

        driver.findElement(By.xpath("//a[span='添加规格参数模板']/span[1]")).click();

        WebDriverWait wait = new WebDriverWait(driver, 3L);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("classify")));

        /*选择一级分类*/
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('classify').removeAttribute('style');");
        Select s = new Select(driver.findElement(By.id("classify")));
        s.selectByVisibleText("电脑办公");


        /*通过截图获取debug信息*/
/*

        File scrFile =
                ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("e:/Selenium/"+dateStr+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

        getScreenshot.getScreenshot(driver, classn + Thread.currentThread().getStackTrace()[1].getMethodName());



        /*选择二级分类*/
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='classid']/div[2]")))); //通过添加等待确保新的下拉菜单出现
        //js.executeScript("document.getElementById('s2id_autogen1').nextSibling.removeAttribute('style');");
        js.executeScript("document.getElementById('classid').lastChild.removeAttribute('style');");
        s = new Select(driver.findElement(By.xpath("//div[@id='classid']/select[2]")));
        s.selectByVisibleText("电脑配件");

        /*选择三级分类*/
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='classid']/div[3]"))));
        //js.executeScript("document.getElementById('s2id_autogen3').nextSibling.removeAttribute('style');");
        js.executeScript("document.getElementById('classid').lastChild.removeAttribute('style');");
        s = new Select(driver.findElement(By.xpath("//div[@id='classid']/select[3]")));
        s.selectByVisibleText("内存");



        /*添加参数组*/
        driver.findElement(By.cssSelector("input[value='添加参数组']")).click();
        driver.findElement(By.cssSelector("input[value='添加参数组']")).click();

        //设置规格参数
        List<WebElement> gparams = driver.findElements(By.xpath("//tbody[@id='type']/tr"));

        for (int i = 0; i < gparams.size(); i++) {
            gparams.get(i).findElement(By.name("topname[]")).clear();
            gparams.get(i).findElement(By.name("topname[]")).sendKeys("atGP" + sdf.format(new Date()));

            gparams.get(i).findElement(By.name("names[]")).clear();
            gparams.get(i).findElement(By.name("names[]")).sendKeys(gpvalue);
        }

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        driver.findElement(By.id("popup_ok")).click();

    }


    @Test
    public void editGP() throws Exception {
        WebDriver driver = loginBMS.driver;

        driver.findElement(By.xpath("//tr[td='内存']/td[last()]/a[1]")).click();

        //修改规格参数
        List<WebElement> gparams = driver.findElements(By.xpath("//tbody[@id='type']/tr"));


        String tname = "";
        String tvalue = "";

        for (int i = 0; i < gparams.size(); i++) {

            gparams.get(i).findElement(By.name("topname[]")).sendKeys("e");


            gparams.get(i).findElement(By.name("names[]")).sendKeys("e");


            tname = tname + (i + 1) + ":" + gparams.get(i).findElement(By.name("topname[]")).getAttribute("value");
            if (i < gparams.size() - 1) {
                tname = tname + "\n";
            }

            tvalue = tvalue + (i + 1) + ":" + gparams.get(i).findElement(By.name("names[]")).getAttribute("value");
            if (i < gparams.size() - 1) {
                tvalue = tvalue + "\n";
            }
        }

        driver.findElement(By.cssSelector("button.btn.btn-success")).click();
        driver.findElement(By.id("popup_ok")).click();

        //验证结果，需查询mongodb数组信息。1.可以讲输入信息转化为数组，在与数据库数组比较 2.直接读取页面信息，与输入信息对比


        //刷新前检测
        String sname = driver.findElement(By.xpath("//tr[td='内存']/td[2]")).getText();
        String svalue = driver.findElement(By.xpath("//tr[td='内存']/td[3]")).getText();

        org.junit.Assert.assertEquals(tname, sname);
        org.junit.Assert.assertEquals(tvalue, svalue);

        //页面刷新后检测
        driver.navigate().refresh(); // 刷新
        sname = driver.findElement(By.xpath("//tr[td='内存']/td[2]")).getText();
        svalue = driver.findElement(By.xpath("//tr[td='内存']/td[3]")).getText();

        org.junit.Assert.assertEquals(tname, sname);
        org.junit.Assert.assertEquals(tvalue, svalue);

        getScreenshot.getScreenshot(driver, classn + Thread.currentThread().getStackTrace()[1].getMethodName());


    }

    @Test
    public void removeGP() throws Exception {
        WebDriver driver = loginBMS.driver;
        driver.findElement(By.xpath("//tr[td='内存']/td[last()]/a[2]")).click();
        driver.switchTo().alert().accept();

        getScreenshot.getScreenshot(driver, classn + Thread.currentThread().getStackTrace()[1].getMethodName());

        //无法找到元素
/*        WebDriverWait wait = new WebDriverWait(driver,3L);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("popup_ok")));
        driver.findElement(By.id("popup_ok")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("popup_ok")));
        driver.findElement(By.id("popup_ok")).click();*/


    }


}
