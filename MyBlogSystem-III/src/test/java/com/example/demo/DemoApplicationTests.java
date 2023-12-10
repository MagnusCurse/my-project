package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void MD5test() {
		String p1 = DigestUtils.md5DigestAsHex("123".getBytes());
		String p2 = DigestUtils.md5DigestAsHex("123".getBytes());
		System.out.println(p1);
		System.out.println(p2);
	}
}
