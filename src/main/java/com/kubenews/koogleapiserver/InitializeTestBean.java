package com.kubenews.koogleapiserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitializeTestBean {
    private static final Logger log = LoggerFactory.getLogger(InitializeTestBean.class);
    @PostConstruct
    public void init() {
        log.info("koogle api server test bean initialize");
    }
}
