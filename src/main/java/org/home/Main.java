package org.home;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Main {
    private static WebDriver driver;
    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        ChromeOptions opt = new ChromeOptions();
        opt.setCapability("se:name", "Test on grid");

        driver = new RemoteWebDriver(new URL("http:localhost:4444/wd/hub"),opt);
        Thread.sleep(50L);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().setPosition(new Point(220,10));
        driver.manage().window().setSize(new Dimension(1000,650));
        driver.get("https://www.google.com");
        Thread.sleep(50L);
        System.out.println(driver.getTitle());
        driver.quit();
    }
}