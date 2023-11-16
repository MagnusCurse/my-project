package com.valuationWebAutoTest.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AutoTestUtils {
    public static ChromeDriver chromeDriver;
    // 返回谷歌驱动
    public static ChromeDriver createDriver() {
        // 如果驱动为空才重新建立一个驱动
        if(chromeDriver == null) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--remote-allow-origins=*");
            chromeDriver = new ChromeDriver(chromeOptions);
        }
        return chromeDriver;
    }

    //
    public static List<String> getTime() {
        // 文件名不能按照天的维度进行保存
        // 文件格式 eg: 20230212-123030 millis
        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd-HHmmssSS");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd");
        String fileName = format1.format(System.currentTimeMillis());
        String direName = format2.format(System.currentTimeMillis());
        List<String> ret = new ArrayList<>();
        ret.add(direName);
        ret.add(fileName);
        return ret;
    }

    /**
     * 获取屏幕截图
     */
    public static void getScreenCapture(String testType) throws IOException {
        List<String> list = getTime();
        // ./ 指的是当前文件路径
        // ./src/test/java/com/blogWebAutoTest/Tests/direName/fileName
        String fileName = "./src/test/java/com/blogWebAutoTest/Tests/" +
                list.get(0) + "/" + testType + "-" +
                list.get(1) + ".png";
        File screenCapture = chromeDriver.getScreenshotAs(OutputType.FILE);
        // 把屏幕截图生成的文件存放到指定的路径
        FileUtils.copyFile(screenCapture,new File(fileName));
    }
}
