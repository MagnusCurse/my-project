package com.valuationWebAutoTest.tests;

import com.valuationWebAutoTest.utils.AutoTestUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShopDetailTest {
    public static ChromeDriver chromeDriver = AutoTestUtils.createDriver();
    @BeforeAll
    public static void baseControl() {
        // 这里主要测试这一家商铺
        chromeDriver.get("http://43.139.61.124:81/#/detail?id=2");
    }

    /**
     * 商铺详情页面加载
     */
    @Order(1)
    @Test
    public void shopDetailPageLoad() {
        // 检测餐厅名称
        chromeDriver.findElement(By.cssSelector("#app > div > div.shop-info-box > div.shop-title"));
        // 检测商铺图片
        chromeDriver.findElement(By.cssSelector("#app > div > div.shop-info-box > div.shop-images"));
        // 检测优惠券模块
        chromeDriver.findElement(By.cssSelector("#app > div > div.shop-voucher > div:nth-child(1)"));
        // 检测评论模块
        chromeDriver.findElement(By.cssSelector("#app > div > div.collapse.card > div.collapse-trigger > div"));
    }

    /**
     * 抢购优惠券功能测试
     */
    @Order(2)
    // @Test
    public void seckillVoucher() {
       // 点击秒杀优惠券按钮
       chromeDriver.findElement(By.cssSelector("#app > div > div.shop-voucher > div.voucher-box > div.voucher-right > div > div.voucher-btn")).click();
       
    }
}
