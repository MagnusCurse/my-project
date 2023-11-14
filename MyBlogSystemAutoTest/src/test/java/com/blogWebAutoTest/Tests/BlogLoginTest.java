package com.blogWebAutoTest.Tests;

import com.blogWebAutoTest.utils.AutoTestUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class BlogLoginTest {
    public static ChromeDriver chromeDriver = AutoTestUtils.createDriver();

    // 一下所有的用例都共用这一个步骤
    @BeforeAll
    public static void baseControl() {
        chromeDriver.get("http://43.139.61.124/#/login");
    }
    // 驱动退出
    @AfterAll
    public static void chromeDriverQuit() {
        chromeDriver.quit();
    }
    /**
     * 检查登录页面是否打开正确
     * 检查登录，注册，忘记密码选项是否存在
     */
    @Test
    public void loginPageLoad() {
        // 检查登录标签
        chromeDriver.findElement(By.cssSelector("body > div > div > form > div:nth-child(2) > a"));
        // 检查注册标签
        chromeDriver.findElement(By.cssSelector("body > div > div > form > div.right-link > a:nth-child(1)"));
        // 检查忘记密码标签
        chromeDriver.findElement(By.cssSelector("body > div > div > form > div.right-link > a:nth-child(2)"));
    }

    /**
     * 检查能否正常登录
     */
    @ParameterizedTest
    @CsvSource({"UserC1,1234","UserC2,5678"})
    public void loginSuccess(String username, String password) {
        // 执行登录执行步骤
        chromeDriver.findElement(By.cssSelector("body > div > div > form > div:nth-child(1) > input[type=text]")).sendKeys(username);
        chromeDriver.findElement(By.cssSelector("body > div > div > form > div:nth-child(2) > input[type=password]")).sendKeys(password);
        chromeDriver.findElement(By.cssSelector("body > div > div > form > div:nth-child(2) > a")).click();
        // 对登录结果进行检测，看是否到达了登录页面
        chromeDriver.findElement(By.cssSelector("#home > div > div.main-container > div.header > div.logo"));
        // 跳转结束后回到登录页面进行下一组测试
        chromeDriver.navigate().back();
    }
}
