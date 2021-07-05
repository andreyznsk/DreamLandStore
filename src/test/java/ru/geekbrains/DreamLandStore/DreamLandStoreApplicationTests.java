package ru.geekbrains.DreamLandStore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class DreamLandStoreApplicationTests {

	@Autowired
	private ApplicationContext appContext;

	@Test
	void contextLoads() {
		Assertions.assertNotNull(appContext.getEnvironment());
	}

}
