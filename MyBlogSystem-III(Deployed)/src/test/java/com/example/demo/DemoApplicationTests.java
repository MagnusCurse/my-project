package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;

@SpringBootTest
class DemoApplicationTests {
	@Autowired
	RedisTemplate redisTemplate;

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

	@Test
	void bitMapRedisOps(String blogId, Long userId) {
		// like the blog, set the position true
		redisTemplate.opsForValue().setBit("blog:liked:" + blogId, userId, true);
		// unlike the blog, set the position false
		redisTemplate.opsForValue().setBit("blog:liked:" + blogId, userId, false);
	}
}
