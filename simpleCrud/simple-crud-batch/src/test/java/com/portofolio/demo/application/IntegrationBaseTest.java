package com.portofolio.demo.application;

import com.portofolio.demo.application.config.JpaContextConfig;
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

    public abstract void clearDataBase();
}
