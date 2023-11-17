package com.blogWebAutoTest.Tests;

import com.blogWebAutoTest.utils.AutoTestUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BlogLoginTest {
    public static ChromeDriver chromeDriver = AutoTestUtils.createDriver();

    // 一下所有的用例都共用这一个步骤
    @BeforeAll
    public static void baseControl() {
        chromeDriver.get("http://43.139.61.124/#/login");
    }

    /**
     * 检查登录页面是否打开正确
     * 检查登录，注册，忘记密码选项是否存在
     */
    @Order(1)
    @Test
    public void loginPageLoad() throws IOException {
        // 检查登录标签
        chromeDriver.findElement(By.cssSelector("body > div > div > form > div:nth-child(2) > a"));
        // 检查注册标签
        chromeDriver.findElement(By.cssSelector("body > div > div > form > div.right-link > a:nth-child(1)"));
        // 检查忘记密码标签
        chromeDriver.findElement(By.cssSelector("body > div > div > form > div.right-link > a:nth-child(2)"));
        // 进行测试屏幕截图
        AutoTestUtils.getScreenCapture("loginPageLoad");
    }

    /**
     * 检查能否正常登录
     */
    @Order(2)
    @ParameterizedTest
    @CsvSource({"UserC1,1234","UserC2,5678"})
    public void loginSuccess(String username, String password) throws InterruptedException, IOException {
        /* TODO 执行登录执行步骤 */
        // 插入数据前要清空，否则自动化会报错
        chromeDriver.findElement(By.cssSelector("body > div > div > form > div:nth-child(1) > input[type=text]")).clear();
        chromeDriver.findElement(By.cssSelector("body > div > div > form > div:nth-child(2) > input[type=password]")).clear();
        chromeDriver.findElement(By.cssSelector("body > div > div > form > div:nth-child(1) > input[type=text]")).sendKeys(username);
        chromeDriver.findElement(By.cssSelector("body > div > div > form > div:nth-child(2) > input[type=password]")).sendKeys(password);
        chromeDriver.findElement(By.cssSelector("body > div > div > form > div:nth-child(2) > a")).click();
        // 这里要强制等待一下，否则会找不到 Alert 元素
        Thread.sleep(2000);
        // 登录完成后会跳出弹窗，点击确认
        Alert alert = chromeDriver.switchTo().alert();
        alert.accept();
        Thread.sleep(1000);
        // 对登录结果进行检测，看是否到达了主页
        chromeDriver.findElement(By.cssSelector("#home > div > div.main-container > div.header > div.logo"));
        // 进行测试屏幕截图
        AutoTestUtils.getScreenCapture("loginSuccess");
        // 跳转结束后回到登录页面进行下一组测试
        chromeDriver.navigate().back();
        Thread.sleep(2000);
    }

    /**
     * 检测异常登录
     */
    @Order(3)
    @ParameterizedTest
    @CsvSource({"UserWrong1,1234","UserWrong2,1234"})
    public void loginFail(String username, String password) throws InterruptedException, IOException {
        // 插入数据前要清空，否则自动化会报错
        chromeDriver.findElement(By.cssSelector("body > div > div > form > div:nth-child(1) > input[type=text]")).clear();
        chromeDriver.findElement(By.cssSelector("body > div > div > form > div:nth-child(2) > input[type=password]")).clear();
        chromeDriver.findElement(By.cssSelector("body > div > div > form > div:nth-child(1) > input[type=text]")).sendKeys(username);
        chromeDriver.findElement(By.cssSelector("body > div > div > form > div:nth-child(2) > input[type=password]")).sendKeys(password);
        chromeDriver.findElement(By.cssSelector("body > div > div > form > div:nth-child(2) > a")).click();
        // 登录失败后，弹窗会报错，获取弹窗文本元素
        String expected = "用户名或者密码错误,请重试!!";
        Thread.sleep(2000);
        Alert alert = chromeDriver.switchTo().alert();
        String actual = alert.getText();
        alert.accept();
        // 进行测试屏幕截图
        AutoTestUtils.getScreenCapture("loginFail");
        // 断言弹窗文本是否和预期文本相同
        Assertions.assertEquals(expected,actual);
    }
}
