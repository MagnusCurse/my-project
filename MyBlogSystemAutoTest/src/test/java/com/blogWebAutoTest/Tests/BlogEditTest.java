package com.blogWebAutoTest.Tests;

import com.blogWebAutoTest.utils.AutoTestUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BlogEditTest {
    public static ChromeDriver chromeDriver = AutoTestUtils.createDriver();

    // 一下所有的用例都共用这一个步骤
    @BeforeAll
    public static void baseControl() {
        chromeDriver.get("http://43.139.61.124/#/home/create");
    }

    /* NOTE 驱动退出，放在这里是因为我们需要先登录再检查，所以登录测试的不能先退出，保留登录记录，一般直接加入到最后一个测试类 */
    @AfterAll
    public static void chromeDriverQuit() {
        chromeDriver.quit();
    }

    /**
     * 检测编辑页面是否正确加载
     */
    @Test
    @Order(1)
    public void editPageLoad() {
        // 检测输入框和发布按钮是否存在
        chromeDriver.findElement(By.cssSelector("#home > div > div.main-container > div.user-box.first-box > div.activity.card > div.input-field > div > input"));
        chromeDriver.findElement(By.cssSelector("#home > div > div.main-container > div.user-box.first-box > div.activity.card > div.input-field > button"));
    }

    /**
     * 发布博客功能测试
     */
    @Test
    @Order(2)
    public void submitBlog() throws InterruptedException {
        String expected = "发布文章成功!!";
        // 输入博客标题
        chromeDriver.findElement(By.cssSelector("#home > div > div.main-container > div.user-box.first-box > div.activity.card > div.input-field > div > input")).sendKeys("BlogTestTitle");
        // 因为采用的是外部编辑器，无法直接给编辑器输入文本，所以这里不输入文本，用原先自带的
        chromeDriver.findElement(By.cssSelector("#home > div > div.main-container > div.user-box.first-box > div.activity.card > div.input-field > button")).click();
        Thread.sleep(2000);
        // 检查弹出窗口的文本，是否符合预期，符合则发布博客成功
        Alert alert = chromeDriver.switchTo().alert();
        String actual = alert.getText();
        alert.accept();
        Assertions.assertEquals(expected,actual);
    }
}
