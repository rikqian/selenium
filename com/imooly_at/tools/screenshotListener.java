package com.imooly_at.tools;

/**
 * Created by qianqiang on 14-12-25.
 * Try to get the screenshot once the test encounter an error.
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.Base64Encoder;
import org.openqa.selenium.remote.ScreenshotException;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 　　* This is an customized webdriver event listener.
 * <p/>
 * 　　* Now it implements onException method: webdriver will take a screenshot
 * <p/>
 * 　　* when it meets an exception. It's good but not so usable. And when we use
 * <p/>
 * 　　* WebDriverWait to wait for an element appearing, the webdriver will throw
 * <p/>
 * 　　* exception always and the onException will be excuted again and again, which
 * <p/>
 * 　　* generates a lot of screenshots.
 * <p/>
 * 　　* Put here for study
 * <p/>
 * 　　* Usage:
 * <p/>
 * 　　* WebDriver driver = new FirefoxDriver();
 * <p/>
 * 　　* WebDriverEventListener listener = new CustomWebDriverEventListener();
 * <p/>
 * 　　* return new EventFiringWebDriver(driver).register(listener);
 * <p/>
 * 　　*
 * <p/>
 * 　　* @author qa
 * <p/>
 * 　　*
 * <p/>
 */


public class screenshotListener extends AbstractWebDriverEventListener {

    @Override
    public void onException(Throwable throwable, WebDriver driver) {

        Throwable cause = throwable.getCause();

        if (cause instanceof ScreenshotException) {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");

            final String fname = "e:/Selenium/errorpic/" + formatter.format(new Date()) + "-exception.png";

            File of = new File(fname);

            FileOutputStream out = null;

            try {

                out = new FileOutputStream(of);

                out.write(new Base64Encoder().decode(((ScreenshotException) cause).getBase64EncodedScreenshot()));


                System.out.println("Error screenshot info: " + fname);


            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}