package com.valuationWebAutoTest.tests;

import com.valuationWebAutoTest.utils.AutoTestUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HomePageTest {
    public static ChromeDriver chromeDriver = AutoTestUtils.createDriver();
    @BeforeAll
    public static void baseControl() {
        chromeDriver.get("http://43.139.61.124:81/#/home");
    }
    @AfterAll
    public static void quitChromeDriver() {
        chromeDriver.quit();
    }

    /**
     * 主页页面加载
     */
    @Order(1)
    @Test
    public void homePageLoad() {
        // 附近商铺图片
        chromeDriver.findElement(By.cssSelector("#app > div > div.type-list"));
        // 下方导航栏
        chromeDriver.findElement(By.cssSelector("#app > div > div.foot > div:nth-child(1) > div.foot-view"));
        chromeDriver.findElement(By.cssSelector("#app > div > div.foot > div:nth-child(2) > div"));
        chromeDriver.findElement(By.cssSelector("#app > div > div.foot > div:nth-child(3) > div.foot-view"));
    }

    /**
     * 检测点击后是否可以进入对应页面
     */
    @Order(2)
    @Test
    public void homePageNavigate() {
        // 添加隐式等待，时间为 3 秒
        chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        // 附近商铺图片
        chromeDriver.findElement(By.cssSelector("#app > div > div.type-list")).click();
        // 检测是否到达了附件商铺列表页面
        chromeDriver.findElement(By.cssSelector("#app > div > div.shop-list > div:nth-child(1)"));
        chromeDriver.navigate().back();

        // 发布博客图标
        chromeDriver.findElement(By.cssSelector("#app > div > div.foot > div:nth-child(2) > div")).click();
        chromeDriver.findElement(By.cssSelector("#app > div > div.header > div.header-commit > button"));
        chromeDriver.navigate().back();

        // 个人中心图标
        chromeDriver.findElement(By.cssSelector("#app > div > div.foot > div:nth-child(3) > div.foot-view")).click();
        chromeDriver.findElement(By.cssSelector("#app > div > div.basic > div.basic-info > div.edit-btn"));
        chromeDriver.navigate().back();
    }
}
