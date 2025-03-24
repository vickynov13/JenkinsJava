package org.home;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;

public class Main {
    private static WebDriver driver;
    public static void main(String[] args) throws MalformedURLException, InterruptedException {
//        runWeb();
        runMobile();
    }

    private static void runMobile() throws MalformedURLException {
        AppiumDriver driver;
//        String appPackage = "com.transsion.calculator";
//        String deviceUDID = "H626X650C0123456";
        String deviceUDID = "P8B4C18105000234";
        String appPackage = "com.android.calculator2";
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName","Android");
        caps.setCapability("appium:platformVersion","9");
        caps.setCapability("appium:udid",deviceUDID);
        caps.setCapability("appium:automationName","UiAutomator2");
        caps.setCapability("appium:noReset",false);
        caps.setCapability("appium:suppressKillServer",true);
        caps.setCapability("appium:appPackage",appPackage);
        driver = new AppiumDriver(new URL("http://192.168.1.3:4444/wd/hub"), caps);
        driver.findElement(By.id(appPackage+":id/digit_6")).click();
        takeMobileScreenShot("click6",driver);
        driver.findElement(By.id(appPackage+":id/op_add")).click();
        takeMobileScreenShot("clickadd", driver);
        driver.findElement(By.id(appPackage+":id/digit_2")).click();
        takeMobileScreenShot("click2", driver);
        driver.findElement(By.id(appPackage+":id/eq")).click();
        takeMobileScreenShot("clickeq", driver);
        System.out.println(driver.findElement(By.id(appPackage+":id/result")).getText());
        takeMobileScreenShot("result", driver);
        driver.quit();


    }

    private static void takeMobileScreenShot(String name, AppiumDriver driver) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(System.getProperty("user.dir")+"/screenshots/"+name+".png");
        try{
            FileUtils.copyFile(src,dest);
            System.out.println("Screenshot Saved: "+dest.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to get Screenshot"+e.getMessage());
        }
    }
    private static void takeDesktopScreenShot(String name, WebDriver driver) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(System.getProperty("user.dir")+"/screenshots/"+name+".png");
        try{
            FileUtils.copyFile(src,dest);
            System.out.println("Screenshot Saved: "+dest.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to get Screenshot"+e.getMessage());
        }
    }

    private static void runWeb() throws InterruptedException, MalformedURLException {
        ChromeOptions opt = new ChromeOptions();
        opt.setCapability("se:name", "Test on grid");

        driver = new RemoteWebDriver(new URL("http://192.168.1.3:4444/wd/hub"),opt);
//        Thread.sleep(50000);
        takeDesktopScreenShot("start",driver);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().setPosition(new Point(220,10));
        driver.manage().window().setSize(new Dimension(1000,650));
        driver.get("https://www.google.com");
//        Thread.sleep(50000);
        takeDesktopScreenShot("openBrowser",driver);
        System.out.println(driver.getTitle());
        driver.quit();
    }
}