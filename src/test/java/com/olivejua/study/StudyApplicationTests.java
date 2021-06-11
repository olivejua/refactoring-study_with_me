package com.olivejua.study;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class StudyApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void test() {
		List<String> list = new ArrayList<>();
		if (list.isEmpty()) {
			System.out.println("this list is empty.");
		} else {
			System.out.println("this list is not empty.");
		}
	}

}
