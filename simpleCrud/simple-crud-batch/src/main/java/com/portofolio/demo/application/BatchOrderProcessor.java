package com.portofolio.demo.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchOrderProcessor {

    private final BatchOrderService service;

    private final Logger log = LoggerFactory.getLogger(BatchOrderProcessor.class);

    @Autowired
    public BatchOrderProcessor(BatchOrderService service) {
        this.service = service;
    }


    @Scheduled(cron = "*/30 * * * * *")
    public void processOrders() {

        this.log.info("Started batch...");

        this.service.processOrders();

        this.log.info("Batch job ended.");
    }
}
