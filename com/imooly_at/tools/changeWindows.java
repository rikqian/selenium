package com.imooly_at.tools;

import org.openqa.selenium.WebDriver;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by qianqiang on 14-12-29.
 * Basic function to switch the webdriver windows.
 */
public class changeWindows {


    public static void change(WebDriver driver)

    {
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


    }
}
