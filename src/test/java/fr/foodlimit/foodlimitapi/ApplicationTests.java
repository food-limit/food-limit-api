package fr.foodlimit.foodlimitapi;

import fr.foodlimit.api.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
public class ApplicationTests {

	@Test
	public void contextLoads() {
		Assert.assertEquals(1, 1);
	}

}
