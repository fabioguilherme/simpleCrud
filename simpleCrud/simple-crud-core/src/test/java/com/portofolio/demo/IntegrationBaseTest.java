package com.portofolio.demo;

import com.portofolio.demo.config.JpaContextConfig;
import org.junit.After;
import org.junit.Before;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = {JpaContextConfig.class})
public abstract class IntegrationBaseTest {

    @After
    public abstract void afterTesting();

    @Before
    public abstract void beforeTesting();

}
