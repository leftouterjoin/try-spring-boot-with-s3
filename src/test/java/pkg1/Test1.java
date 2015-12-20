package pkg1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import akihyro.tryspringbootwiths3.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class Test1 {

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    public void test1() {
    	Resource r = resourceLoader.getResource("file://pom.xml");
    	System.out.println(r);
    }

}
