package com.kalaha;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KalahaApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void applicationContextTest() {
		KalahaApplication.main(new String[] {});
	}

}