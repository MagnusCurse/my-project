package com.blogWebAutoTest.Tests;

import com.blogWebAutoTest.utils.AutoTestUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class BlogCenterTest {
    public static ChromeDriver chromeDriver = AutoTestUtils.createDriver();

    // 一下所有的用例都共用这一个步骤
    @BeforeAll
    public static void baseControl() {
        chromeDriver.get("http://43.139.61.124/#/center");
    }

    /**
     * 检测个人中心页面是否可以正常显示
     */
    @Test
    public void centerPageLoad() throws InterruptedException {
        chromeDriver.findElement(By.cssSelector("#home > div > div.main-container > div.user-box.first-box > div > div.account.card > div.account-info > div:nth-child(1) > div.nickname"));
        chromeDriver.findElement(By.cssSelector("#home > div > div.main-container > div.user-box.first-box > div > div.account.card > div.account-info > div:nth-child(1) > div.email"));
        Thread.sleep(3000);
    }
}
