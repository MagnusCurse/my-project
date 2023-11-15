package com.blogWebAutoTest.Tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({BlogLoginTest.class,BlogCenterTest.class, BlogEditTest.class})
public class runSuite {

}
