package com.valuationWebAutoTest.tests;

import com.valuationWebAutoTest.utils.AutoTestUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTest {
    public static ChromeDriver chromeDriver = AutoTestUtils.createDriver();
    @BeforeAll
    public static void baseControl() {
        chromeDriver.get("http://43.139.61.124:81/#/login");
    }
    @AfterAll
    public static void quitChromeDriver() {
        chromeDriver.quit();
    }

    /**
     * 登录页面加载
     */
    @Order(1)
    @Test
    public void LoginPageLoad() {
        // 检测手机号码输入框
        chromeDriver.findElement(By.cssSelector("#app > div > div.content > div > div:nth-child(1) > div > div > input"));
        // 检测发送验证码按钮
        chromeDriver.findElement(By.cssSelector("#app > div > div.content > div > div:nth-child(1) > button"));
        // 检测验证码输入框
        chromeDriver.findElement(By.cssSelector("#app > div > div.content > div > div.control.is-clearfix > input"));
        // 检测登录按钮
        chromeDriver.findElement(By.cssSelector("#app > div > div.content > div > button"));
    }

    /**
     * 检测正常登录
     */
    @Order(2)
    @ParameterizedTest
    @CsvSource({"17138830681","15089517646"})
    public void loginSuccess(String phoneNumber) throws InterruptedException {
        // 输入之前要清空输入框的值
        chromeDriver.findElement(By.cssSelector("#app > div > div.content > div > div:nth-child(1) > div > div > input")).clear();
        chromeDriver.findElement(By.cssSelector("#app > div > div.content > div > div.control.is-clearfix > input")).clear();
        // 输入手机号码并点击发送验证码按钮
        chromeDriver.findElement(By.cssSelector("#app > div > div.content > div > div:nth-child(1) > div > div > input")).sendKeys(phoneNumber);
        chromeDriver.findElement(By.cssSelector("#app > div > div.content > div > div:nth-child(1) > button")).click();
        Thread.sleep(2000);
        Alert alert = chromeDriver.switchTo().alert();
        String text = alert.getText();
        alert.accept();
        // 获取到验证码
        String code = text.split(":")[1];
        // 输入验证码并点击确认登录
        chromeDriver.findElement(By.cssSelector("#app > div > div.content > div > div.control.is-clearfix > input")).sendKeys(code);
        chromeDriver.findElement(By.cssSelector("#app > div > div.content > div > button")).click();
        Thread.sleep(2000);
        chromeDriver.navigate().back();
        Thread.sleep(2000);
    }
}
