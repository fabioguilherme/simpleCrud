package com.portofolio.demo;

import com.portofolio.demo.config.JpaContextConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = {JpaContextConfig.class})
public abstract class IntegrationBaseTest {

    @AfterEach
    public void afterTesting() {
        clearDataBase();
    }

    @BeforeEach
    public void beforeTesting() {
        clearDataBase();
    }

    protected abstract void clearDataBase();
}
