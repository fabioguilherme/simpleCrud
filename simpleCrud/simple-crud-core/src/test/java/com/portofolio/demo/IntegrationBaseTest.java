package com.portofolio.demo;

import com.portofolio.demo.config.JpaContextConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {JpaContextConfig.class})
public abstract class IntegrationBaseTest {

    @After
    public abstract void afterTesting();

    @Before
    public abstract void beforeTesting();

}
