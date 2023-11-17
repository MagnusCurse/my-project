package com.valuationWebAutoTest.tests;

import com.valuationWebAutoTest.utils.AutoTestUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CenterTest {
    public static ChromeDriver chromeDriver = AutoTestUtils.createDriver();
    @BeforeAll
    public static void baseControl() {
        chromeDriver.get("http://43.139.61.124:81/#/center");
    }
    @AfterAll
    public static void quitChromeDriver() {
        chromeDriver.quit();
    }

    /**
     * 个人中心页面加载
     */
    @Order(1)
    @Test
    public void centerPageLoad() {
        // 个人信息模块
        chromeDriver.findElement(By.cssSelector("#app > div > div.basic > div.basic-icon"));
        chromeDriver.findElement(By.cssSelector("#app > div > div.basic > div.basic-info > div.name"));
        chromeDriver.findElement(By.cssSelector("#app > div > div.basic > div.basic-info > div.edit-btn"));
        chromeDriver.findElement(By.cssSelector("#app > div > div.basic > div.logout-btn"));
        // 个人博客
        chromeDriver.findElement(By.cssSelector("#tab-1"));
        // 个人订阅
        chromeDriver.findElement(By.cssSelector("#tab-4"));
        // 博客/订阅列表页面
        chromeDriver.findElement(By.cssSelector("#app > div > div.content > div > div.el-tabs__content"));
    }

    /**
     * 编辑信息功能测试：目前只有编辑昵称
     */
    @Order(2)
    @ParameterizedTest
    @CsvSource({"testName1","testName2"})
    public void editNickname(String nickname) throws InterruptedException {
       chromeDriver.findElement(By.cssSelector("#app > div > div.basic > div.basic-info > div.edit-btn")).click();
       chromeDriver.findElement(By.cssSelector("#app > div > div.edit-container > div:nth-child(1) > div:nth-child(3) > div.info-btn")).click();
       Thread.sleep(2000);
       // 点击确认修改昵称
       Alert alert = chromeDriver.switchTo().alert();
       alert.accept();
       Thread.sleep(1000);
       alert = chromeDriver.switchTo().alert();
       // 输入昵称到输入框
       alert.sendKeys(nickname);
       alert.accept();
       Thread.sleep(1000);
       // 修改昵称后还有一个弹窗，提示要重新登录
       alert = chromeDriver.switchTo().alert();
       alert.accept();
       chromeDriver.navigate().back();
       Thread.sleep(2000);
    }

    /**
     * 注销账户功能
     */
    @Order(3)
    @Test
    public void logout() throws InterruptedException {
        // 点击注销按钮，弹窗后同意即可注销
        chromeDriver.findElement(By.cssSelector("#app > div > div.basic > div.logout-btn")).click();
        Thread.sleep(1000);
        Alert alert = chromeDriver.switchTo().alert();
        alert.accept();
        Thread.sleep(1000);
        // 注销后跳转到登录页面，查看有无登录按钮即可
        chromeDriver.findElement(By.cssSelector("#app > div > div.content > div > button"));
    }
}
