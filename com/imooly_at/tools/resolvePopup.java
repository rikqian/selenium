package com.imooly_at.tools;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

/**
 * Created by qianqiang on 14-12-24.
 * Resolve two types popup windows during testing.
 * Confirm the windows by default.
 */
public class resolvePopup {
    private static Boolean b, b1, b2;


    public static boolean isElementPresent(WebDriver driver, By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        } catch (WebDriverException ue) {
            return false;
        }

    }

    public static boolean isAlertPresent(WebDriver driver) {
        try {
            driver.switchTo().alert();
            return true;
        } catch (WebDriverException Ex) {
            return false;
        }
    }

    public static void accept(WebDriver driver) {
        b1 = isElementPresent(driver, By.id("popup_ok"));
        b2 = isAlertPresent(driver);


        while (b1 | b2) {
            if (b1) {
                driver.findElement(By.id("popup_ok")).click();
                b1 = false;

            } else

            {
                driver.switchTo().alert().accept();
                b2 = false;

            }

        }

    }
}
