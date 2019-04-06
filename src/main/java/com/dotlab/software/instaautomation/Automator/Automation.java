/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dotlab.software.instaautomation.Automator;

import com.dotlab.software.instaautomation.Scrapper.Parser.AccountParser;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import com.dotlab.software.instaautomation.Settings.ApplicationSettings;
import com.dotlab.software.instaautomation.UI.AutoStatic;

import com.github.daytron.simpledialogfx.dialog.Dialog;
import com.github.daytron.simpledialogfx.dialog.DialogType;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.JavascriptExecutor;



/**
 *
 * @author omandotkom
 */
public class Automation {

    private static WebDriver driver;
    public final String LOGIN_STATUS_FUCKED = "FUCKED_UP";
    public final String LOGIN_STATUS_SUCCESS = "LOGIN_SUCCESS";
    //private final String LOGIN_STATUS_FAILED = "LOGIN_FAILED";

    private void setDriver(WebDriver driver) {
        //System.setProperty("webdriver.chrome.driver", new ApplicationSettings().getChromeDriverPath());
        //this.driver = driver;
    }

    public WebDriver setToMobile() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-agent=Mozilla/5.0 (Linux; Android 6.0; HTC One M9 Build/MRA58K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.98 Mobile Safari/537.36");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        return driver;
    }

    public Automation() {
        try {
            System.setProperty("webdriver.chrome.driver", new ApplicationSettings().getChromeDriverPath());
            ChromeOptions options = new ChromeOptions();
            //headless support
            if (new ApplicationSettings().getHeadless()) {
                //jika headless
                options.addArguments("headless");
            }

            options.addArguments("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
            driver = new ChromeDriver(options);
        } catch (IOException ex) {

            Logger.getLogger(Automation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Automation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void register(String name, String username, String email) throws Exception {
        driver.get("https://www.instagram.com/accounts/emailsignup/");
        WebElement usernameElement = driver.findElement(By.name("username"));
        WebElement passwordElement = driver.findElement(By.name("password"));
        WebElement emailElement = driver.findElement(By.name("emailOrPhone"));
        WebElement fullNameElement = driver.findElement(By.name("fullName"));

        // Enter something to search for
        emailElement.sendKeys(email);
        fullNameElement.sendKeys(name);

        usernameElement.sendKeys(username);
        passwordElement.sendKeys("system3298");
        passwordElement.submit();
        WebDriverWait wait = new WebDriverWait(driver, 300);
        wait.until(ExpectedConditions.titleIs("Instagram"));
        driver.manage().deleteAllCookies();

    }

    public void sleep(long duration) {
        driver.manage().timeouts().implicitlyWait(duration, TimeUnit.SECONDS);
    }
    public String fuckedStateCheck(){
    if (driver.manage().getCookieNamed("ds_user_id") == null) {
            //this is fucked up condition when instagram blocks the login form,
            //shit this happens again.

            System.out.println("Login failed, verification step needed");
            return this.LOGIN_STATUS_FUCKED;
        } else {
            //success
            return this.LOGIN_STATUS_SUCCESS;
        }
    }
    public String auth(String user, String pass) throws WebDriverException, Exception, InterruptedException {
        //Boolean returnVar = true;
        System.out.println("Authentication started...");

        driver.manage().window().maximize();
        driver.get("https://m.instagram.com/accounts/login/?source=auth_switcher");

        // Find the text input element by its name
        //driver.get("https://www.instagram.com/omandotkom.jpg");
        WebElement username = driver.findElement(By.name("username"));
        WebElement password = driver.findElement(By.name("password"));

        // Enter something to search for
        username.sendKeys(user);
        password.sendKeys(pass);

        // Now submit the form. WebDriver will find the form for us from the element
        Thread.sleep(2000);
        password.submit();

        WebDriverWait wait = new WebDriverWait(driver, 300);
        wait.until(ExpectedConditions.titleIs("Instagram"));
        if (driver.manage().getCookieNamed("ds_user_id") == null) {
            //this is fucked up condition when instagram blocks the login form,
            //shit this happens again.

            System.out.println("Login failed, verification step needed");
            return this.LOGIN_STATUS_FUCKED;
        } else {
            //success
            return this.LOGIN_STATUS_SUCCESS;
        }
        //Cookie cok = driver.manage().getCookieNamed("sessionid");

    }

    public Boolean like(String url) {
        Boolean result = false;
        if (!driver.getCurrentUrl().equals(url)) {

            driver.get(url);
        }
        try {
            this.scrollDown();
            WebElement button = driver.findElement(By.className(AutoStatic.SETTINGS.getLikeButton()));
            //WebElement button  = driver.findElement(By.cssSelector(".FY9nT > button:nth-child(1) > span:nth-child(1)"));
            //WebElement button = driver.findElement(By.className("dCJp8"));
            
            button.click();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String time = dtf.format(now);
            System.out.println("(" + time + ") Successfully like " + url);
            result = true;

        } catch (WebDriverException e) {
            System.err.println("Error " + url);
            result = false;
            throw (e);
        } catch (Exception e) {
            result = false;
            throw (e);
        }
        return result;
    }

    public void comment(String url) {
        driver.get(url);

        try {
            /*      WebElement button = driver.findElement(By.className("oF4XW"));
            button.click();
            Thread.sleep(2000);
             */
            WebElement komentar = driver.findElement(By.className("X7cDz"));
            //WebElement komentar = driver.findElement(By.linkText("Add a comment..."));

            Actions actions = new Actions(driver);
            actions.moveToElement(komentar);
            actions.click();
            actions.sendKeys("love it!!");
            actions.build().perform();
            komentar.submit();
            System.out.println("Successfully comment " + url);
        } catch (org.openqa.selenium.NoSuchElementException nse) {
            System.err.println("Error " + url);
            System.err.println(nse.getMessage());
        }

    }

    public String getSessionID() {
        return driver.manage().getCookieNamed("sessionid").getValue();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setWebDiver(WebDriver driver) throws IOException, URISyntaxException {
        this.driver = driver;
        System.setProperty("webdriver.chrome.driver", AutoStatic.SETTINGS.getChromeDriverPath());

    }

    public String getResponse(String url) {
        driver.get(url);
        return driver.getPageSource();
    }

    public Set<Cookie> getCookies() {
        return driver.manage().getCookies();
    }

    /*BELOW CODE IS EXPERIMENTAL*/
    public void newTab() {
        System.out.println("Opening new tab...");
        driver.findElement(By.tagName("body")).sendKeys(Keys.CONTROL + "t");
    }

    public void scrollDown() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");
    }

    public void scrollUp() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,0)");
    }

    public void a() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        for (String l : tabs) {
            System.out.println(l);
        }
//driver.switchTo().window(tabs.get(0));
    }

    public Boolean followByUsername(String url) {
        boolean result = false;

        try {
            driver.get(url);
            Document doc = Jsoup.parse(driver.getPageSource());
            /*Elements e = doc.getElementsByClass("_5f5mN");
            String status = e.get(0).text();
            if (status.toLowerCase().equals("following")) {
                return false;
            }*/
            WebElement element = driver.findElement(By.xpath("//button[contains(text(),'Follow')]"));
            element.click();
            result = true;

        } catch (WebDriverException e) {
            System.err.println("Error " + url);
            System.err.println(e.getMessage());
            result = false;
            throw (e);
        } catch (Exception e) {
            result = false;
            System.err.println("Error " + url);
            System.err.println(e.getMessage());
            throw (e);
        }
        return result;
    }

    public Boolean follow(String url) {
        //algoritma follow dari hashtag
        this.scrollUp();
        boolean result = false;
        if (!driver.getCurrentUrl().equals(url)) {
            driver.get(url);
        }

        try {
            WebElement element = driver.findElement(By.className("oW_lN"));
            element.click();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String time = dtf.format(now);
            result = true;
            System.out.print("Follow success.");
        } catch (WebDriverException exx) {
            System.err.println("Error " + url);
            result = false;
            throw (exx);

        } catch (Exception e) {
            result = false;
            throw (e);
        }
        return result;
    }

    public void open(String url) {
        this.driver.get(url);
    }

    public boolean unfollow(String url) {

        boolean result = false;
        driver.get(url);
        try {
            WebElement element = driver.findElement(By.className("vBF20"));
            element.click();
            WebDriverWait wait = new WebDriverWait(driver, 300);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("aOOlW")));

            element = driver.findElement(By.className("aOOlW"));
            element.click();
            result = true;
            System.out.print("Unfollow success.");
        } catch (org.openqa.selenium.NoSuchElementException nse) {
            System.err.println("Error " + url);
            result = false;
        } catch (Exception e) {
            System.err.println("Error " + url);
            result = false;
        }
        return result;

    }

    public boolean unfollow2(String url) {

        return true;
    }
}
