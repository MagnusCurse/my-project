package com.valuationWebAutoTest.tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({LoginTest.class, HomePageTest.class, CenterTest.class})
public class runSuite {

}
