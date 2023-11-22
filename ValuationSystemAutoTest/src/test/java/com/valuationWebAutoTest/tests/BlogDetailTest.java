package com.valuationWebAutoTest.tests;

import com.valuationWebAutoTest.utils.AutoTestUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BlogDetailTest {
    public static ChromeDriver chromeDriver = AutoTestUtils.createDriver();
    @BeforeAll
    public static void baseControl() {
       /* 这里是一篇用来测试的博客，手机号是非 17138830681 的用户. 可以用来测试订阅功能 */
       chromeDriver.get("http://43.139.61.124:81/#/blog-detail?id=28");
    }

    /**
     * 页面能否正常加载
     */
    @Order(1)
    @Test
    public void blogDetailPageLoad() {
        // 添加隐式等待，时间为 3 秒
        chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        // 个人信息模块：头像，昵称，日期
        chromeDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.basic"));
        // 博客内容
        chromeDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.blog-text"));
        // 点赞排行榜
        chromeDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.zan-box > div"));
        // 博客评价的餐厅
        chromeDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.shop-basic"));
        // 点赞图标
        chromeDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.zan-box > div"));
    }

    /**
     * 点赞功能测试
     */
    @Order(2)
    @Test
    public void likeBlog() throws IOException, InterruptedException {
        /* 这里要加载很长时间才能加载出来，奇怪的是使用显示等待没有作用 ?? */
        Thread.sleep(10000);

        AutoTestUtils.getScreenCapture("likeBlog");
        // 添加显示等待，直到点赞数出来了才获取文本
        new WebDriverWait(chromeDriver,Duration.ofSeconds(30))
                .until(chromeDriver -> chromeDriver
                        .findElement(By.cssSelector("#blog-like-count")));

        // 显示等待 5 秒，直到找到元素
//        new WebDriverWait(chromeDriver, Duration.ofSeconds(30))
//                .until(chromeDriver -> {
//                    JavascriptExecutor jsExecutor = (JavascriptExecutor) chromeDriver;
//                    return jsExecutor.executeScript("document.querySelector('#blog-like-count').innerText;");
//                });

        chromeDriver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);

        AutoTestUtils.getScreenCapture("likeBlog");
        // 获取点赞前的点赞数量
        int expected =
                /* NOTE 这里要注意，获取文本如果没有该元素的话，不会报错，而是直接获取到空文本 */
                Integer.parseInt(chromeDriver.findElement(By.cssSelector("#blog-like-count")).getText());
        AutoTestUtils.getScreenCapture("likeBlog");

        // 点击点赞图标
        chromeDriver.findElement(By.cssSelector("#app > div > div.foot > div:nth-child(1) > div > svg")).click();
        /* NOTE 这里最好等待久一点，否则容易导致点赞数量还没有变化就获取了，导致后面断言判断错误 */
        Thread.sleep(3000);
        // 获取点赞后的点赞数量
        int actual =
                Integer.parseInt(chromeDriver.findElement(By.cssSelector("#blog-like-count")).getText());

        System.out.println("expected" + expected);
        System.out.println("actual" + actual);

        // 当点赞数量 + 1 或者 - 1 的时候，都表示成功
        Assertions.assertTrue(actual == expected + 1 || actual == expected - 1);
    }

    /**
     * 关注/取关功能测试
     */
    @Order(3)
    @Test
    public void followUser() throws IOException, InterruptedException {
        AutoTestUtils.getScreenCapture("followUser");
        // 获取关注用户之前的文本
        String before =
                chromeDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.basic > div:nth-child(3) > div")).getText();
        System.out.println("before:" + before);
        // 点击用户关注图标
        chromeDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.basic > div:nth-child(3)")).click();
        /* NOTE 这里最好等待久一点，否则容易导致还没有变化就获取了，导致后面断言判断错误 */
        Thread.sleep(3000);
        // 获取点击后的文本
        String after =
                chromeDriver.findElement(By.cssSelector("#app > div > div:nth-child(2) > div.basic > div:nth-child(3) > div")).getText();
        System.out.println("after:" + after);
        AutoTestUtils.getScreenCapture("followUser");
        // 如果文本有变化说明订阅功能成功
        Assertions.assertNotEquals(before,after);
    }
}
