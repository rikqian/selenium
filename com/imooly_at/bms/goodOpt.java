package com.imooly_at.bms;

import com.imooly_at.tools.fileopt;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.imooly_at.tools.mongodb.getdb;

/**
 * Created by QianQiang on 14-10-20.
 * Finish the all operations for saling for a good.
 * Input: get the goodname from Root/good.txt
 */
public class goodOpt {

    @Before
    public void setUp() throws Exception {
        loginBMS.loginBMS();
    }

    @Test
    public void goodOpt() throws Exception {
        WebDriver driver = loginBMS.driver;
        //进入商品页面
        driver.findElement(By.className("icon-th")).click();

        driver.findElement(By.linkText("商品管理")).click();


        //读取商家名称 获得商品code,id
        String gname;
        String gcode, gid = null;
        String file = "./good.txt";
        gname = fileopt.readFile(file);

        DBCollection db = getdb("goods");
        DBObject dbobj = db.findOne(new BasicDBObject("name", gname), new BasicDBObject("code", true));
        gcode = (String) dbobj.get("code");
        gid = dbobj.get("_id").toString();


        System.out.println("审核前：" + driver.getCurrentUrl());// debug info
        /*点击审核按钮*/
        driver.findElement(By.cssSelector("a[data-original-title='审核'][href*='" + gcode + "']")).click();

        /*切换窗口*/
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



        /*审核成功*/
        while (driver.findElement(By.linkText("审核成功")).isEnabled() == false) {
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        }
        driver.findElement(By.linkText("审核成功")).click();

        //处理弹出框
        driver.switchTo().alert().accept();


        /*回到原来页面*/
        driver.switchTo().window(currentWindow);

        driver.findElement(By.id("successbtn")).click();
        System.out.println("审核后：" + driver.getCurrentUrl());// debug info



        /*点击价审按钮*/
        driver.findElement(By.cssSelector("a[data-original-title='编辑价格'][href*='" + gcode + "']")).click();

        /*价审页面：录入价格，进入待上架状态*/
        Set<String> handles2 = driver.getWindowHandles();//获取所有窗口句柄
        Iterator<String> it2 = handles2.iterator();
        while (it2.hasNext()) {
            if (currentWindow == it2.next()) {
                continue;
            }
            WebDriver window = driver.switchTo().window(it2.next());//切换到新窗口
            //window.close();//关闭新窗口
        }

        driver.findElement(By.id("onlyVipRmb")).clear();
        driver.findElement(By.id("onlyVipRmb")).sendKeys("0.02");
        driver.findElement(By.cssSelector("input[value='一键填入会员价']")).click();

        driver.findElement(By.id("onlyImooly")).clear();
        driver.findElement(By.id("onlyImooly")).sendKeys("0.03");
        driver.findElement(By.cssSelector("input[value='一键填入魔力价']")).click();

        driver.findElement(By.id("onlyClear")).clear();
        driver.findElement(By.id("onlyClear")).sendKeys("0.01");
        driver.findElement(By.cssSelector("input[value='一键填入结算价']")).click();

        driver.findElement(By.id("onlyMarket")).clear();
        driver.findElement(By.id("onlyMarket")).sendKeys("1");
        driver.findElement(By.cssSelector("input[value='一键填入市场价']")).click();

        driver.findElement(By.className("btn-success")).click();

        //处理弹出框
        driver.switchTo().alert().accept();


        /*回到原来页面*/
        driver.switchTo().window(currentWindow);


        driver.findElement(By.id("successbtn")).click();
        System.out.println("价审后：" + driver.getCurrentUrl());// debug info
        /*上架操作*/


        driver.findElement(By.cssSelector("a[data-original-title='上架'][data-itemid*='" + gid + "']")).click();
        //处理弹出框
        driver.switchTo().alert().accept();


        /*回到原来页面,及时更新driver信息，获取当前页面*/
        currentWindow = driver.getWindowHandle();
        driver.switchTo().window(currentWindow);


        //确认上架成功提示信息
/*        while (driver.findElement(By.cssSelector("input[id='popup_ok'][type='button'][value='确定']")).isEnabled()==false)
        {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        }*/
        //driver.manage().timeouts().pageLoadTimeout(10L,TimeUnit.SECONDS); //有效页面等待
        //driver.findElement(By.id("popup_ok")).click();
        /*debug info*/



        /*等待方法*/
        WebDriverWait wait = new WebDriverWait(driver, 3L);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[id='popup_ok'][type='button'][value='确定']")));
        System.out.println("上架后：" + driver.getCurrentUrl()); //debug info
        Thread.sleep(3000);
        /*
        if (driver.findElement(By.cssSelector("input[id='popup_ok'][type='button'][value='确定']")).isEnabled()) {
            currentWindow = driver.getWindowHandle();
            driver.switchTo().window(currentWindow);
            driver.findElement(By.cssSelector("input[id='popup_ok'][type='button'][value='确定']")).click();
        }
*/
        /*验证商品状态*/
        System.out.println(new Date());
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //等待状态变换
        dbobj = db.findOne(new BasicDBObject("name", gname), new BasicDBObject("position", true));
        Long gstatus = (Long) dbobj.get("position");
        System.out.println(new Date());
        System.out.print(gstatus);
        Long tstatus = Long.valueOf(4);
        Assert.assertEquals(tstatus, gstatus);


        //注销网页
        driver.quit();
    }


}
